package org.doit.payment.service;

import java.sql.SQLException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.doit.payment.domain.PaymentPrepareDTO;
import org.doit.payment.mapper.PaymentPrepareMapper;

@Service
public class PaymentPrepareService {

    @Autowired
    private PaymentPrepareMapper paymentPrepareMapper;

    /**
     * 결제 준비 처리 (유효성 검증 + 로직 판단 + DAO 호출)
     * @param dto 결제 준비 정보
     * @return 처리 성공 여부
     * @throws IllegalArgumentException 입력값이 유효하지 않은 경우
     * @throws Exception 처리 중 발생하는 예외
     */
    
    public boolean processPaymentPrepare(PaymentPrepareDTO dto) throws Exception {
        // 1. 파라미터 유효성 검증
        validateParameters(dto);
        
        // 2. 결제 금액 유효성 검증
        validateAmount(dto.getAmount());
        
        // 3. 비즈니스 로직 검증
        validateBusinessLogic(dto);
        
        // 4. 결제 정보와 로그를 트랜잭션으로 저장
        return savePaymentInfo(dto);
    }

    /**
     * 결제 정보를 저장합니다.
     * @param dto 결제 준비 정보
     * @return 저장 성공 여부
     * @throws Exception 저장 중 발생하는 예외
     */
    @Transactional
    public boolean savePaymentInfo(PaymentPrepareDTO dto) throws Exception {
        try {
            // 1. payment_id 생성
            String paymentId = "PAY_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
            
            // 2. payment 테이블에 저장
            int paymentResult = paymentPrepareMapper.insertPayment(dto, paymentId);
            
            if (paymentResult <= 0) {
                throw new Exception("payment 테이블 저장에 실패했습니다.");
            }
            
            // 3. request_log_id 생성을 위한 현재 행 개수 조회
            int nextRequestLogId = paymentPrepareMapper.getPaymentRequestLogCount() + 1;
            
            // 4. payment_request_log 테이블에 저장
            int logResult = paymentPrepareMapper.insertPaymentRequestLog(nextRequestLogId, paymentId, dto);
            
            if (logResult <= 0) {
                throw new Exception("payment_request_log 테이블 저장에 실패했습니다.");
            }
            
            System.out.println("[SUCCESS] 결제 정보 및 로그 저장 성공 - PaymentId: " + paymentId + ", MerchantUid: " + dto.getMerchantUid());
            return true;
            
        } catch (Exception e) {
            System.err.println("[ERROR] 결제 정보 저장 중 오류 발생 - MerchantUid: " + dto.getMerchantUid() + ", Error: " + e.getMessage());
            throw new Exception("결제 정보 저장 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 파라미터 유효성 검증
     * @param dto 검증할 DTO 객체
     * @throws IllegalArgumentException 유효하지 않은 입력값인 경우
     */
    private void validateParameters(PaymentPrepareDTO dto) throws IllegalArgumentException {
        if (dto == null) {
            throw new IllegalArgumentException("결제 정보가 누락되었습니다.");
        }
        
        // 주문번호 검증
        String merchantUid = dto.getMerchantUid();
        if (merchantUid == null || merchantUid.trim().isEmpty()) {
            throw new IllegalArgumentException("주문번호가 누락되었습니다.");
        }
        
        // 예약번호 검증
        String bookingId = dto.getBookingId();
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("예약번호가 누락되었습니다.");
        }
        
        // 결제방법 검증
        String paymentMethod = dto.getPaymentMethod();
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("결제방법이 누락되었습니다.");
        }
        
        // 결제금액 검증
        String amount = dto.getAmount();
        if (amount == null || amount.trim().isEmpty()) {
            throw new IllegalArgumentException("결제금액이 누락되었습니다.");
        }
        
        // 생성시간 검증
        String createdAt = dto.getCreatedAt();
        if (createdAt == null || createdAt.trim().isEmpty()) {
            throw new IllegalArgumentException("생성시간이 누락되었습니다.");
        }
        
        System.out.println("[VALIDATION] 파라미터 검증 완료 - MerchantUid: " + merchantUid);
    }
    
    /**
     * 결제 금액 유효성 검증
     * @param amount 결제 금액 문자열
     * @throws IllegalArgumentException 금액이 유효하지 않은 경우
     */
    private void validateAmount(String amount) throws IllegalArgumentException {
        try {
            double amountValue = Double.parseDouble(amount);
            if (amountValue <= 0) {
                throw new IllegalArgumentException("결제금액은 0보다 커야 합니다.");
            }
            if (amountValue > 10000000) { // 1천만원 한도
                throw new IllegalArgumentException("결제금액이 한도를 초과했습니다. (최대 10,000,000원)");
            }
            System.out.println("[VALIDATION] 결제금액 검증 완료 - Amount: " + amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("결제금액 형식이 올바르지 않습니다.");
        }
    }
    
    /**
     * 비즈니스 로직 검증
     * @param dto 검증할 DTO 객체
     * @throws IllegalArgumentException 비즈니스 규칙에 위배되는 경우
     */
    private void validateBusinessLogic(PaymentPrepareDTO dto) throws IllegalArgumentException {
        // 결제방법 유효성 검증
        String paymentMethod = dto.getPaymentMethod();
        if (!isValidPaymentMethod(paymentMethod)) {
            throw new IllegalArgumentException("지원하지 않는 결제방법입니다: " + paymentMethod);
        }
    }
    
    /**
     * 지원하는 결제방법인지 검증
     * @param paymentMethod 결제방법
     * @return 유효한 결제방법 여부
     */
    private boolean isValidPaymentMethod(String paymentMethod) {
        // 지원하는 결제방법 목록
        String[] validMethods = {"card", "kakaopay", "tosspay"};
        
        for (String method : validMethods) {
            if (method.equalsIgnoreCase(paymentMethod)) {
                return true;
            }
        }
        return false;
    }
} 