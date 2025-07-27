package org.doit.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(1)  // AOP 실행 순서 지정
public class PaymentLoggingAspect {

    @Pointcut("execution(* org.doit.payment.controller.*.*(..))")
    public void paymentControllerMethods() {}
    
    @Pointcut("execution(* org.doit.payment.service.*.*(..))")
    public void paymentServiceMethods() {}

    /**
     * Controller 메서드 실행 전후 로깅
     */
    @Around("paymentControllerMethods()")
    public Object logControllerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        // 요청 정보 수집
        HttpServletRequest request = getCurrentRequest();
        String requestInfo = "";
        if (request != null) {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String sessionId = getSessionId(request);
            requestInfo = String.format("%s %s (SessionId: %s)", method, uri, sessionId);
        }
        
        System.out.println(String.format("=== [CONTROLLER START] %s.%s - %s ===", 
                                       className, methodName, requestInfo));
        
        long startTime = System.currentTimeMillis();
        Object result = null;
        
        try {
            result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            System.out.println(String.format("=== [CONTROLLER SUCCESS] %s.%s - 실행시간: %dms ===", 
                                           className, methodName, executionTime));
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            System.err.println(String.format("=== [CONTROLLER ERROR] %s.%s - 실행시간: %dms, 오류: %s ===", 
                                            className, methodName, executionTime, e.getMessage()));
            throw e;
        }
    }

    /**
     * Service 메서드 실행 전후 로깅
     */
    @Around("paymentServiceMethods()")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        System.out.println(String.format("[SERVICE START] %s.%s", className, methodName));
        
        long startTime = System.currentTimeMillis();
        Object result = null;
        
        try {
            result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            System.out.println(String.format("[SERVICE SUCCESS] %s.%s - 실행시간: %dms", 
                                           className, methodName, executionTime));
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            System.err.println(String.format("[SERVICE ERROR] %s.%s - 실행시간: %dms, 오류: %s", 
                                            className, methodName, executionTime, e.getMessage()));
            throw e;
        }
    }

    /**
     * 트랜잭션 메서드 로깅
     */
    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object logTransactionalExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        // 결제 관련 클래스만 트랜잭션 로깅
        if (className.contains("Payment") || className.contains("Refund")) {
            System.out.println(String.format("[TRANSACTION START] %s.%s", className, methodName));
            
            try {
                Object result = joinPoint.proceed();
                System.out.println(String.format("[TRANSACTION COMMIT] %s.%s", className, methodName));
                return result;
            } catch (Exception e) {
                System.err.println(String.format("[TRANSACTION ROLLBACK] %s.%s - 오류: %s", 
                                                className, methodName, e.getMessage()));
                throw e;
            }
        } else {
            return joinPoint.proceed();
        }
    }

    private HttpServletRequest getCurrentRequest() {
        try {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            return attributes != null ? attributes.getRequest() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String getSessionId(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            return session != null ? session.getId() : "null";
        } catch (Exception e) {
            return "unknown";
        }
    }
} 