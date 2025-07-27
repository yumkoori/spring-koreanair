package org.doit.payment.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.doit.payment.domain.RefundProcessDTO;
import org.doit.member.model.User;
import org.doit.payment.service.RefundProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/refund")
public class RefundProcessController {

    @Autowired
    private RefundProcessService refundProcessService;

    /**
     * GET 요청 처리 - 환불 확인 페이지 표시
     */
    @RequestMapping(value = "/process", method = RequestMethod.GET)
    public String refundProcessPage(
            @RequestParam(value = "bookingId", required = true) String bookingId,
            @RequestParam(value = "passengerName", required = false) String passengerName,
            @RequestParam(value = "bookingPw", required = false) String bookingPw,
            Model model) {
        
        // 파라미터를 Model에 설정하여 JSP에서 사용할 수 있도록 함
        if (bookingId != null) {
            model.addAttribute("bookingId", bookingId);
        }
        if (passengerName != null) {
            model.addAttribute("passengerName", passengerName);
        }
        if (bookingPw != null) {
            model.addAttribute("bookingPw", bookingPw);
        }

        // 환불 확인 JSP 페이지로 포워딩
        return "/payment/refundProcess";
    }

    /**
     * POST 요청 처리 - 환불 처리 실행
     */
    @RequestMapping(value = "/process", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String refundProcess(
            @RequestParam("bookingId") String bookingId,
            @RequestParam(value = "refundReason", required = false) String refundReason,
            @RequestParam(value = "bookingPw", required = false) String bookingPw,
            HttpServletRequest request) {

        Map<String, Object> result = new HashMap<>();
        
        
        try {
            // 1. 세션에서 사용자 정보 가져오기
            HttpSession session = request.getSession(false);
            User user = null;
            Integer userNo = null;
            
            if (session != null) {
                user = (User) session.getAttribute("user");
                if (user != null) {
                    userNo = user.getUserNo();
                }
            }

            // 2. 입력값 검증
            if (bookingId == null || bookingId.trim().isEmpty()) {
                return "{\"success\": false, \"message\": \"예약번호가 누락되었습니다.\"}";
            }

            System.out.println("[DEBUG] 환불 요청 - BookingId: " + bookingId + ", UserNo: " + userNo + ", BookingPw: " + (bookingPw != null ? "제공됨" : "미제공"));

            // 3. 위변조 검사를 위한 DTO 생성 및 검증
            String merchantUid;
            if (userNo != null) {
                // 회원인 경우 기존 로직
                RefundProcessDTO validationDto = new RefundProcessDTO(bookingId, userNo);
                merchantUid = refundProcessService.validateRefundRequest(validationDto);
            } else {
                // 비회원인 경우 bookingPw로 검증
                if (bookingPw == null || bookingPw.trim().isEmpty()) {
                    return "{\"success\": false, \"message\": \"비회원 예약의 경우 예약 비밀번호가 필요합니다.\"}";
                }
                RefundProcessDTO validationDto = new RefundProcessDTO(bookingId, bookingPw);
                merchantUid = refundProcessService.validateNonMemberRefundRequest(validationDto);
            }

            // 4. 환불 처리를 위한 DTO 생성 (amount는 실제로는 DB에서 조회해야 함)
            String amount = getRefundAmount(merchantUid);
            RefundProcessDTO refundDto = new RefundProcessDTO(bookingId, merchantUid, 
                    userNo != null ? userNo : 0, amount,
                    refundReason != null ? refundReason : "사용자 요청");

            // 5. 환불 처리 실행
            boolean refundSuccess = refundProcessService.processRefund(refundDto);

            if (refundSuccess) {
                return "{\"success\": true, \"message\": \"환불이 성공적으로 처리되었습니다.\"}";
            } else {
                return "{\"success\": false, \"message\": \"환불 처리 중 오류가 발생했습니다.\"}";
            }

        } catch (IllegalArgumentException e) {
            // 입력값 검증 오류 처리
            System.err.println("[VALIDATION ERROR] " + e.getMessage());
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";

        } catch (Exception e) {
            // 시스템 오류 처리
            System.err.println("[SYSTEM ERROR] 환불 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace(); // 디버깅을 위해 스택 트레이스 출력
            return "{\"success\": false, \"message\": \"환불 처리 중 시스템 오류가 발생했습니다.\"}";
        }
    }
        

    /**
     * merchant_uid로 환불 금액 조회
     */
    private String getRefundAmount(String merchantUid) {
        try {
            // refundProcessService를 통해 실제 결제 금액 조회
            String paymentAmount = refundProcessService.getPaymentAmount(merchantUid);
            
            if (paymentAmount != null && !paymentAmount.trim().isEmpty()) {
                System.out.println("[SUCCESS] 환불 금액 조회 성공 - MerchantUid: " + merchantUid + ", Amount: " + paymentAmount);
                return paymentAmount;
            } else {
                System.err.println("[WARNING] 환불 금액 조회 실패 - MerchantUid: " + merchantUid);
                return "0"; // 조회 실패시 0 반환
            }
        } catch (Exception e) {
            System.err.println("[ERROR] 환불 금액 조회 중 오류 발생 - MerchantUid: " + merchantUid + ", Error: " + e.getMessage());
            return "0"; // 오류 발생시 0 반환
        }
    }
} 