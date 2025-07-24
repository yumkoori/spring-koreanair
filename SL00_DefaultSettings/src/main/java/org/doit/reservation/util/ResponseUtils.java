package org.doit.reservation.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import lombok.extern.log4j.Log4j;

/**
 * HTTP 응답 생성 공통 유틸리티 클래스
 */
@Log4j
public class ResponseUtils {
    
    /**
     * 성공 응답 생성
     */
    public static ResponseEntity<Map<String, Object>> createSuccessResponse(String redirectUrl) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("redirectUrl", redirectUrl);
        
        log.info("성공 응답 생성 - redirectUrl: " + redirectUrl);
        
        return ResponseEntity.ok()
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(response);
    }
    
    /**
     * 에러 응답 생성
     */
    public static ResponseEntity<Map<String, Object>> createErrorResponse(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", errorMessage);
        
        log.warn("에러 응답 생성 - errorMessage: " + errorMessage);
        
        return ResponseEntity.ok()
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(response);
    }
    
    /**
     * 시스템 에러 응답 생성 (500 에러)
     */
    public static ResponseEntity<Map<String, Object>> createSystemErrorResponse(String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("error", errorMessage);
        
        log.error("시스템 에러 응답 생성 - errorMessage: " + errorMessage);
        
        return ResponseEntity.status(500)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(response);
    }
} 