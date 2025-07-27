package org.doit.payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.doit.payment.domain.PaymentPrepareDTO;
import org.doit.payment.service.PaymentPrepareService;
import org.doit.payment.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentPrepareService paymentPrepareService;
    
    @RequestMapping(value = "/page", method = {RequestMethod.GET, RequestMethod.POST})
    public String paymentPreparePage(
    		@RequestParam(value = "bookingId", defaultValue="8f361971-012a-4e9d-9185-c19cb9159c8a") String bookingId,
    		@RequestParam(value = "totalAmount", defaultValue="300000") String totalAmount
    		, Model model)
    		throws Exception {
    	
    	model.addAttribute("bookingId", bookingId);
    	model.addAttribute("totalAmount", totalAmount);
		return "/payment/Payment_Page";
	}
    
    /**
     * 결제 준비 처리
     */
    @RequestMapping(value = "/prepare", method = RequestMethod.POST)
    @ResponseBody
    public String paymentPrepare(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain; charset=UTF-8");
        
        // 1. 세션 및 이중결제 방지 토큰 검증
        HttpSession session = request.getSession(false);
        String paymentToken = request.getParameter("paymentToken");
        
        System.out.println("토큰 검증 시작 - 요청받은 토큰: " + paymentToken);
        
        if (session == null) {
            throw new SecurityException("세션이 유효하지 않습니다.");
        }
        
        if (!TokenUtil.validateAndConsumePaymentToken(session, paymentToken)) {
            throw new SecurityException("유효하지 않은 결제 토큰입니다. 페이지를 새로고침 후 다시 시도해주세요.");
        }
        
        System.out.println("토큰 검증 성공");
        
        // 2. 요청 파라미터 추출
        String merchantUid = request.getParameter("merchantUid");
        String bookingId = request.getParameter("bookingId");      
        String paymentMethod = request.getParameter("payment_method"); 
        String amount = request.getParameter("amount");
        String createdAt = request.getParameter("created_at");
        
        System.out.println("결제 요청 정보 - merchantUid: " + merchantUid + ", bookingId: " + bookingId + ", amount: " + amount);
        
        // 3. PaymentPrepareDTO 생성
        PaymentPrepareDTO dto = new PaymentPrepareDTO(bookingId, merchantUid, paymentMethod, amount, createdAt);
        
        // 4. 서비스 계층에 처리 위임
        boolean success = paymentPrepareService.processPaymentPrepare(dto);

        if (success) {
            System.out.println("결제 사전 검증 성공 - merchantUid: " + merchantUid);
            return "success";
        } else {
            System.out.println("결제 사전 검증 실패 - merchantUid: " + merchantUid);
            return "failed";
        }
    }
} 