package org.doit.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Order(2)  // 로깅 AOP 다음에 실행
public class PaymentExceptionAspect {

    @Pointcut("execution(* org.doit.payment.controller.*.*(..))")
    public void paymentControllerMethods() {}
    
    @Pointcut("execution(* org.doit.payment.service.*.*(..))")
    public void paymentServiceMethods() {}

    /**
     * Controller에서 발생하는 모든 예외 처리
     */
    @AfterThrowing(pointcut = "paymentControllerMethods()", throwing = "exception")
    public void handleControllerException(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        // 요청 정보 수집
        HttpServletRequest request = getCurrentRequest();
        String requestInfo = "";
        if (request != null) {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            requestInfo = String.format("%s %s", method, uri);
        }
        
        // 예외 타입별 상세 로깅
        if (exception instanceof IllegalArgumentException) {
            System.err.println(String.format("[CONTROLLER VALIDATION ERROR] %s.%s - %s - 입력값 오류: %s", 
                                            className, methodName, requestInfo, exception.getMessage()));
        } else if (exception instanceof SecurityException) {
            System.err.println(String.format("[CONTROLLER SECURITY ERROR] %s.%s - %s - 보안 오류: %s", 
                                            className, methodName, requestInfo, exception.getMessage()));
        } else {
            System.err.println(String.format("[CONTROLLER SYSTEM ERROR] %s.%s - %s - 시스템 오류: %s", 
                                            className, methodName, requestInfo, exception.getMessage()));
            exception.printStackTrace();
        }
    }

    /**
     * Service에서 발생하는 모든 예외 처리
     */
    @AfterThrowing(pointcut = "paymentServiceMethods()", throwing = "exception")
    public void handleServiceException(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        // 예외 타입별 상세 로깅
        if (exception instanceof IllegalArgumentException) {
            System.err.println(String.format("[SERVICE VALIDATION ERROR] %s.%s - 비즈니스 룰 위반: %s", 
                                            className, methodName, exception.getMessage()));
        } else if (exception instanceof java.sql.SQLException) {
            System.err.println(String.format("[SERVICE DB ERROR] %s.%s - 데이터베이스 오류: %s", 
                                            className, methodName, exception.getMessage()));
        } else if (exception instanceof java.io.IOException) {
            System.err.println(String.format("[SERVICE API ERROR] %s.%s - 외부 API 통신 오류: %s", 
                                            className, methodName, exception.getMessage()));
        } else {
            System.err.println(String.format("[SERVICE SYSTEM ERROR] %s.%s - 시스템 오류: %s", 
                                            className, methodName, exception.getMessage()));
            exception.printStackTrace();
        }
    }

    /**
     * 트랜잭션 관련 예외 처리
     */
    @AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "exception")
    public void handleTransactionException(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        // 결제 관련 클래스만 트랜잭션 예외 로깅
        if (className.contains("Payment") || className.contains("Refund")) {
            System.err.println(String.format("[TRANSACTION ERROR] %s.%s - 트랜잭션 롤백됨: %s", 
                                            className, methodName, exception.getMessage()));
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
} 