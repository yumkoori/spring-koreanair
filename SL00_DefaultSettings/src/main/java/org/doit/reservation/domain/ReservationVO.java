package org.doit.reservation.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 예약 정보 Value Object (MyBatis 직접 사용 + Lombok 최적화)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVO {
    
    // === 기본 예약 정보 ===
    private String bookingId;           // 예약 ID (PK)
    private String userId;              // 사용자 ID (FK, nullable for 비회원)
    private String memberId;            // 회원번호
    private String flightId;            // 항공편 ID (FK)
    
    // === 승객 정보 ===
    private String firstName;           // 이름
    private String lastName;            // 성
    private String passengerName;       // 승객명 (전체명)
    private String passengerType;       // 승객 유형 (adult, child, infant)
    private String gender;              // 성별 (M, F, U)
    private Timestamp birthDate;        // 생년월일
    private String phone;               // 전화번호
    private String passengerPhone;      // 승객 전화번호
    private String email;               // 이메일
    
    // === 항공편 정보 ===
    private String flightNumber;        // 항공편 번호
    private String aircraftType;        // 항공기 모델
    private Timestamp departureTime;    // 출발 시간
    private Timestamp arrivalTime;      // 도착 시간
    private String departureAirport;    // 출발 공항
    private String arrivalAirport;      // 도착 공항
    private String departureAirportId;  // 출발 공항 코드 (ICN, GMP 등)
    private String arrivalAirportId;    // 도착 공항 코드
    private String departureAirportName; // 출발 공항 이름
    private String arrivalAirportName;  // 도착 공항 이름
    
    // === 날짜 정보 (문자열 형태) ===
    private String departureDate;       // 출발 날짜 (YYYY-MM-DD)
    private String arrivalDate;         // 도착 날짜 (YYYY-MM-DD)
    
    // === 좌석 및 가격 정보 ===
    private String assignedSeat;        // 좌석 번호 (12A, 15F 등)
    private String cabinClass;          // 좌석 등급 (Economy, Business, First)
    private Double totalPrice;          // 총 가격
    
    // === 상태 정보 ===
    private String bookingStatus;       // 예약 상태 (CONFIRMED, CANCELLED 등)
    private String status;              // 일반 상태
    private String checkinStatus;       // 체크인 상태 (NOT_CHECKED, COMPLETED 등)
    private Timestamp bookingDate;      // 예약 생성 날짜
    private String ticketNumber;        // 항공권 번호
    
    // === 비즈니스 로직 메서드 (Lombok이 생성하지 않는 커스텀 메서드) ===
    
    /**
     * 체크인 가능 여부 확인 (출발 24시간 전부터 1시간 전까지)
     */
    public boolean isCheckinAvailable() {
        if (departureTime == null) return false;
        
        long currentTime = System.currentTimeMillis();
        long depTime = departureTime.getTime();
        long timeDiff = depTime - currentTime;
        
        // 24시간 전부터 1시간 전까지
        long hours24 = 24 * 60 * 60 * 1000;
        long hours1 = 60 * 60 * 1000;
        
        return timeDiff <= hours24 && timeDiff >= hours1;
    }
    
    /**
     * 예약 취소 가능 여부 확인 (출발 24시간 전까지)
     */
    public boolean isCancellable() {
        if (departureTime == null) return false;
        
        long currentTime = System.currentTimeMillis();
        long depTime = departureTime.getTime();
        long timeDiff = depTime - currentTime;
        
        // 출발 24시간 전까지 취소 가능
        long hours24 = 24 * 60 * 60 * 1000;
        
        return timeDiff > hours24 && !"CANCELLED".equals(bookingStatus);
    }
    
    /**
     * 전체 승객명 반환 (firstName + lastName)
     */
    public String getFullPassengerName() {
        if (passengerName != null && !passengerName.trim().isEmpty()) {
            return passengerName;
        }
        
        StringBuilder fullName = new StringBuilder();
        if (firstName != null) fullName.append(firstName);
        if (lastName != null) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(lastName);
        }
        
        return fullName.toString();
    }
    
    /**
     * 체크인 완료 여부 확인
     */
    public boolean isCheckedIn() {
        return "COMPLETED".equals(checkinStatus);
    }
    
    /**
     * 좌석 클래스별 우선순위 반환
     */
    public int getSeatClassPriority() {
        if (cabinClass == null) return 3;
        
        switch (cabinClass.toUpperCase()) {
            case "FIRST": return 1;
            case "BUSINESS": return 2;
            case "ECONOMY": return 3;
            default: return 3;
        }
    }
}