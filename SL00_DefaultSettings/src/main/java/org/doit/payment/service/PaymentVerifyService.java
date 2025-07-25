package org.doit.payment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.doit.payment.domain.PaymentCompareDTO;
import org.doit.payment.domain.PaymentVerifyDTO;
import org.doit.payment.mapper.PaymentVerifyMapper;

@Service
public class PaymentVerifyService {
    
    private static final String IAMPORT_API_URL = "https://api.iamport.kr";
    private static final String IMP_KEY = "6276503637885650"; // 실제 아임포트 REST API 키로 변경 필요
    private static final String IMP_SECRET = "7jajf8d7pzMXanL5PhCbbJfGBfnBO2NZRPqrwFExfSMMYJvJJYcitNUwk0BIrbQKWVh6sOzF1RaxDHTC"; // 실제 아임포트 REST API Secret으로 변경 필요
    
    @Autowired
    private PaymentVerifyMapper paymentVerifyMapper;
    
    /**
     * 아임포트 액세스 토큰을 발급받습니다.
     * @return 액세스 토큰
     * @throws Exception 토큰 발급 중 오류 발생 시
     */
    private String getAccessToken() throws Exception {
        try {
            URL url = new URL(IAMPORT_API_URL + "/users/getToken");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            
            // 요청 본문 생성
            String jsonInputString = String.format(
                "{\"imp_key\":\"%s\",\"imp_secret\":\"%s\"}", 
                IMP_KEY, IMP_SECRET
            );
            
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("아임포트 토큰 발급 실패. 응답 코드: " + responseCode);
            }
            
            // 응답 읽기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            
            // JSON 파싱하여 액세스 토큰 추출
            String responseStr = response.toString();
            String accessToken = extractJsonValue(responseStr, "access_token");
            
            if (accessToken != null && !accessToken.isEmpty()) {
                System.out.println("[ACCESS TOKEN] 토큰 발급 성공");
                return accessToken;
            } else {
                throw new Exception("액세스 토큰을 찾을 수 없습니다.");
            }
            
        } catch (IOException e) {
            System.err.println("[TOKEN ERROR] 아임포트 API 통신 중 오류: " + e.getMessage());
            throw new Exception("아임포트 API 통신 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
    
    /**
     * imp_uid로 아임포트에서 결제 정보를 조회합니다.
     * @param impUid 아임포트 결제 고유번호
     * @return PaymentDTO 결제 정보
     * @throws Exception 조회 중 오류 발생 시
     */
    public PaymentVerifyDTO getPaymentInfo(String impUid) throws Exception {
        if (impUid == null || impUid.trim().isEmpty()) {
            throw new IllegalArgumentException("imp_uid는 필수값입니다.");
        }
        
        try {
            String accessToken = getAccessToken();
            
            URL url = new URL(IAMPORT_API_URL + "/payments/" + impUid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            conn.setRequestProperty("Content-Type", "application/json");
            
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("결제 정보 조회 실패. 응답 코드: " + responseCode);
            }
            
            // 응답 읽기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
            
            // JSON 파싱하여 결제 정보 추출
            String responseStr = response.toString();
            System.out.println("[API RESPONSE] 결제 정보 조회 성공: " + impUid);
            
            // PaymentDTO 객체 생성 및 설정
            PaymentVerifyDTO paymentDTO = new PaymentVerifyDTO();
            paymentDTO.setImpUid(extractJsonValue(responseStr, "imp_uid"));
            paymentDTO.setMerchantUid(extractJsonValue(responseStr, "merchant_uid"));
            paymentDTO.setStatus(extractJsonValue(responseStr, "status"));
            
            String amountStr = extractJsonValue(responseStr, "amount");
            paymentDTO.setAmount(amountStr != null && !amountStr.isEmpty() ? Integer.parseInt(amountStr) : 0);
            
            paymentDTO.setPayMethod(extractJsonValue(responseStr, "pay_method"));
            paymentDTO.setPaidAt(extractJsonValue(responseStr, "paid_at"));
            paymentDTO.setFailReason(extractJsonValue(responseStr, "fail_reason"));
            paymentDTO.setBuyerName(extractJsonValue(responseStr, "buyer_name"));
            paymentDTO.setBuyerEmail(extractJsonValue(responseStr, "buyer_email"));
            paymentDTO.setBuyerTel(extractJsonValue(responseStr, "buyer_tel"));
            
            return paymentDTO;
            
        } catch (IOException e) {
            System.err.println("[PAYMENT INFO ERROR] 결제 정보 조회 중 네트워크 오류: " + e.getMessage());
            throw new Exception("아임포트 API 통신 중 오류가 발생했습니다: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            System.err.println("[PAYMENT INFO ERROR] 결제 금액 파싱 오류: " + e.getMessage());
            throw new Exception("결제 금액 정보가 올바르지 않습니다.", e);
        }
    }
    
    /**
     * imp_uid로 아임포트 API에서 받은 데이터와 DB 데이터를 비교하여 검증
     * @param impUid JSP에서 전달받은 imp_uid
     * @return 검증 결과 (true: 성공, false: 실패)
     * @throws Exception 검증 중 오류 발생 시
     */
    public boolean verifyPaymentByImpUid(String impUid, String merchantUid) throws Exception {
        if (impUid == null || impUid.trim().isEmpty()) {
            throw new IllegalArgumentException("imp_uid는 필수값입니다.");
        }
        
        try {
            System.out.println("[VERIFY START] 결제 검증 시작 - imp_uid: " + impUid);
            
            // 1. 아임포트 API에서 결제 정보 조회 (amount, merchant_uid만 필요)
            PaymentVerifyDTO iamportData = getPaymentInfo(impUid);
            String iamportMerchantUid = iamportData.getMerchantUid();
            int iamportAmount = iamportData.getAmount();
            
            System.out.println("[IAMPORT DATA] merchant_uid: " + iamportMerchantUid + ", amount: " + iamportAmount);
            
            // 2. 결제 상태가 'paid'인지 확인
            if (!"paid".equals(iamportData.getStatus())) {
                System.err.println("[VERIFY FAIL] 결제 상태가 완료되지 않음 - 상태: " + iamportData.getStatus());
                return false;
            }
            
            // 3. DB에서 merchant_uid로 결제 정보 조회
            PaymentCompareDTO dbData = paymentVerifyMapper.getPaymentCompareInfoByMerchantUid(iamportMerchantUid);
            String dbMerchantUid = dbData.getMerchantUid();
            int dbAmount = dbData.getAmount();
            
            System.out.println("[DB DATA] merchant_uid: " + dbMerchantUid + ", amount: " + dbAmount);
            
            // 4. merchant_uid 비교
            if (!iamportMerchantUid.equals(dbMerchantUid)) {
                System.err.println("[VERIFY FAIL] merchant_uid 불일치 - 아임포트: " + iamportMerchantUid + ", DB: " + dbMerchantUid);
                return false;
            }
            
            // 5. amount 비교
            if (iamportAmount != dbAmount) {
                System.err.println("[VERIFY FAIL] 결제 금액 불일치 - 아임포트: " + iamportAmount + "원, DB: " + dbAmount + "원");
                return false;
            }
            
            System.out.println("[VERIFY SUCCESS] 모든 검증 통과 - merchant_uid: " + dbMerchantUid + ", amount: " + dbAmount + "원");
            return true;
            
        } catch (Exception e) {
            System.err.println("[VERIFY ERROR] 결제 검증 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * 결제 검증 후 DB 상태를 PAID로 업데이트
     * @param merchantUid 가맹점 주문번호
     * @return 업데이트 성공 여부
     * @throws Exception 업데이트 중 오류 발생 시
     */
    @Transactional
    public boolean updatePaymentStatusAfterVerification(String merchantUid) throws Exception {
        if (merchantUid == null || merchantUid.trim().isEmpty()) {
            throw new IllegalArgumentException("merchant_uid는 필수값입니다.");
        }
        
        try {
            int updateResult = paymentVerifyMapper.updatePaymentStatusToPaid(merchantUid);
            boolean success = updateResult > 0;
            
            if (success) {
                System.out.println("[STATUS UPDATE SUCCESS] 결제 상태 업데이트 완료 - merchant_uid: " + merchantUid);
            } else {
                System.err.println("[STATUS UPDATE FAIL] 결제 상태 업데이트 실패 - merchant_uid: " + merchantUid);
            }
            
            return success;
            
        } catch (Exception e) {
            System.err.println("[STATUS UPDATE ERROR] 결제 상태 업데이트 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * JSON 문자열에서 특정 필드의 값을 추출합니다.
     * @param jsonString JSON 문자열
     * @param fieldName 필드명
     * @return 필드 값 (찾지 못한 경우 null)
     */
    private String extractJsonValue(String jsonString, String fieldName) {
        try {
            // "fieldName":"value" 또는 "fieldName":value 패턴을 찾습니다
            String pattern = "\"" + fieldName + "\"\\s*:\\s*\"?([^,}\"]+)\"?";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(jsonString);
            
            if (matcher.find()) {
                String value = matcher.group(1);
                // 따옴표 제거
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }
                return value;
            }
            return null;
        } catch (Exception e) {
            System.err.println("[JSON PARSE ERROR] JSON 파싱 오류 - 필드: " + fieldName + ", 오류: " + e.getMessage());
            return null;
        }
    }
} 