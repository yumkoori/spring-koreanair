package org.doit.reservation.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 예약취소 정보 Value Object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelReservationVO {
    
    // === 기본 예약 정보 ===
    private String bookingId;           // 예약 ID
    private String flightId;            // 항공편 ID
    private String passengerName;       // 승객명
    private String lastName;            // 성
    private String firstName;           // 이름
    
    // === 항공편 정보 ===
    private Timestamp departureTime;    // 출발 시간
    private Timestamp arrivalTime;      // 도착 시간
    private String departureAirportId;  // 출발 공항 코드
    private String departureAirportName; // 출발 공항 이름
    private String arrivalAirportId;    // 도착 공항 코드
    private String arrivalAirportName;  // 도착 공항 이름
    
    // === 취소 관련 정보 ===
    private Double baseFare;            // 기본 운임
    private Double tax;                 // 세금
    private Integer penaltyFee;         // 위약금
    private Double totalRefundAmount;   // 총 환불 예정 금액
    private String cancelReason;        // 취소 사유
    private Timestamp cancelDate;       // 취소 날짜
    
    // === 신청자 정보 ===
    private String phone;               // 연락처
    private String email;               // 이메일
    
    // === 비즈니스 로직 메서드 ===
    
    /**
     * 총 환불 예정 금액 계산
     */
    public Double getTotalRefundAmount() {
        double base = (baseFare != null) ? baseFare : 0.0;
        double taxAmount = (tax != null) ? tax : 0.0;
        int penalty = (penaltyFee != null) ? penaltyFee : 0;
        
        return base + taxAmount - penalty;
    }
    
    /**
     * 취소 가능 여부 확인 (항상 가능)
     */
    public boolean isCancellable() {
        return true;
    }
    
    /**
     * 전체 승객명 반환
     */
    public String getFullPassengerName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) fullName.append(firstName);
        if (lastName != null) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(lastName);
        }
        return fullName.toString();
    }
} 