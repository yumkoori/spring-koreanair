package org.doit.payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.doit.member.model.User;
import org.doit.payment.domain.PaymentVerifyDTO;
import org.doit.payment.service.EmailService;
import org.doit.payment.service.PaymentVerifyService;
import org.doit.payment.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class PaymentVerifyController {

    @Autowired
    private PaymentVerifyService paymentVerifyService;
    
    @Autowired
    private EmailService emailService;

    /**
     * 결제 검증 처리
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String paymentVerify(
            @RequestParam("imp_uid") String impUid,
            @RequestParam(value = "csrfToken", required = false) String csrfToken,
            HttpServletRequest request,
            Model model) throws Exception {
        
        // 1. CSRF 토큰 검증
        HttpSession session = request.getSession(false);
        
        System.out.println("CSRF 토큰 검증 시작 - 요청받은 토큰: " + csrfToken);
        
        if (session == null) {
            throw new SecurityException("세션이 유효하지 않습니다. 다시 로그인해 주세요.");
        }
        
        if (!TokenUtil.validateCSRFToken(session, csrfToken)) {
            throw new SecurityException("보안 토큰이 유효하지 않습니다. 결제 페이지에서 다시 시도해 주세요.");
        }
        
        System.out.println("CSRF 토큰 검증 성공");
        
        // 2. impUid 유효성 검사
        System.out.println("결제 검증 요청 - imp_uid: " + impUid);

        if (impUid == null || impUid.trim().isEmpty()) {
            throw new IllegalArgumentException("imp_uid가 누락되었습니다.");
        }

        // 3. PaymentVerifyService를 통한 결제 검증 (아임포트 API vs DB 데이터 비교)
        boolean isValid = paymentVerifyService.verifyPaymentByImpUid(impUid, null);

        if (isValid) {
            System.out.println("결제 검증 성공 - imp_uid: " + impUid);
            
            // 검증 성공 시 - DB에서 결제 상태 업데이트 (READY -> PAID, paid_at 설정)
            PaymentVerifyDTO paymentInfo = paymentVerifyService.getPaymentInfo(impUid);
            String merchantUid = paymentInfo.getMerchantUid();
            
            // DB 상태 업데이트
            boolean updateSuccess = paymentVerifyService.updatePaymentStatusAfterVerification(merchantUid);
            
            if (updateSuccess) {
                model.addAttribute("paymentInfo", paymentInfo);
                model.addAttribute("verificationStatus", "success");
                
                System.out.println("결제 검증 및 DB 업데이트 성공 - imp_uid: " + impUid + ", merchant_uid: " + merchantUid);
                
                // 결제 완료 이메일 전송
                sendPaymentConfirmationEmail(session, paymentInfo);
                
                return "/payment/success";
            } else {
                // DB 업데이트 실패 시
                model.addAttribute("error", "결제 검증은 성공했지만 DB 업데이트에 실패했습니다. 관리자에게 문의해 주세요.");
                model.addAttribute("verificationStatus", "db_update_failed");
                
                System.out.println("결제 검증은 성공했지만 DB 업데이트 실패 - imp_uid: " + impUid + ", merchant_uid: " + merchantUid);
                
                return "/views/error";
            }
        } else {
            // 검증 실패 시
            System.out.println("결제 검증 실패 - imp_uid: " + impUid);
            model.addAttribute("error", "결제 검증에 실패했습니다. 결제 정보를 확인해 주세요.");
            model.addAttribute("verificationStatus", "failed");
            
            return "/views/payment/failure";
        }
    }
    
    @GetMapping("success")
    public String paymentSuccess() throws Exception {
		return "/";
	}
    
    /**
     * 결제 완료 이메일 전송 메서드
     * @param session HTTP 세션
     * @param paymentInfo 결제 정보
     */
    private void sendPaymentConfirmationEmail(HttpSession session, PaymentVerifyDTO paymentInfo) {
        try {
            // 세션에서 사용자 정보 가져오기
            User user = (User) session.getAttribute("user");
            
            if (user != null && user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
                String userEmail = user.getEmail();
                
                // 이메일 전송
                emailService.sendPaymentConfirmationEmail(userEmail, paymentInfo);
                
                System.out.println("결제 완료 이메일 전송 성공 - 수신자: " + userEmail + 
                                 ", 주문번호: " + paymentInfo.getMerchantUid());
                
            } else {
                System.out.println("이메일 전송 건너뛰기 - 사용자 정보 없음 또는 이메일 주소 없음");
            }
            
        } catch (Exception e) {
            // 이메일 전송 실패해도 결제 프로세스는 계속 진행
            System.err.println("결제 완료 이메일 전송 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 