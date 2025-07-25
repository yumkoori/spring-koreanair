package org.doit.payment.util;

import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 토큰 관련 유틸리티 클래스
 */
public class TokenUtil {
    
    private static final String PAYMENT_TOKEN_ATTR = "payment_token";
    private static final String CSRF_TOKEN_ATTR = "csrf_token";
    private static final SecureRandom secureRandom = new SecureRandom();
    
    /**
     * 결제 토큰 생성 및 세션에 저장
     * @param session HttpSession
     * @return 생성된 토큰
     */
    public static String generatePaymentToken(HttpSession session) {
        String token = generateSecureToken();
        session.setAttribute(PAYMENT_TOKEN_ATTR, token);
        return token;
    }
    
    /**
     * 결제 토큰 검증 및 소비 (한 번 사용 후 삭제)
     * @param session HttpSession
     * @param token 검증할 토큰
     * @return 검증 결과
     */
    public static boolean validateAndConsumePaymentToken(HttpSession session, String token) {
        if (session == null || token == null) {
            return false;
        }
        
        String sessionToken = (String) session.getAttribute(PAYMENT_TOKEN_ATTR);
        if (sessionToken != null && sessionToken.equals(token)) {
            // 토큰 사용 후 삭제 (한 번만 사용 가능)
            session.removeAttribute(PAYMENT_TOKEN_ATTR);
            return true;
        }
        return false;
    }
    
    /**
     * CSRF 토큰 생성 및 세션에 저장
     * @param session HttpSession
     * @return 생성된 토큰
     */
    public static String generateCSRFToken(HttpSession session) {
        String token = generateSecureToken();
        session.setAttribute(CSRF_TOKEN_ATTR, token);
        return token;
    }
    
    /**
     * CSRF 토큰 검증
     * @param session HttpSession
     * @param token 검증할 토큰
     * @return 검증 결과
     */
    public static boolean validateCSRFToken(HttpSession session, String token) {
        if (session == null || token == null) {
            return false;
        }
        
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        return sessionToken != null && sessionToken.equals(token);
    }
    
    /**
     * 보안 토큰 생성
     * @return 32바이트 길이의 Base64 인코딩된 랜덤 토큰
     */
    private static String generateSecureToken() {
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getEncoder().encodeToString(tokenBytes);
    }
} 