package org.doit.reservation.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 체크인 정보 Value Object (MyBatis 직접 사용 + Lombok 최적화)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckinVO {
    
    // === 기본 체크인 정보 ===
    private String checkinId;           // 체크인 ID (PK)
    private String bookingId;           // 예약 ID (FK)
    private String flightId;            // 항공편 ID (FK)
    
    // === 승객 정보 ===
    private String firstName;           // 이름
    private String lastName;            // 성
    private String phoneNumber;         // 전화번호
    private String email;               // 이메일
    
    // === 항공편 정보 ===
    private String flightNumber;        // 항공편 번호
    private String aircraftModel;       // 항공기 모델
    private Timestamp departureTime;    // 출발 시간
    private Timestamp arrivalTime;      // 도착 시간
    private String departureAirport;    // 출발 공항
    private String arrivalAirport;      // 도착 공항
    private String departureCode;       // 출발 공항 코드
    private String arrivalCode;         // 도착 공항 코드
    private String departureAirportName; // 출발 공항 이름
    private String arrivalAirportName;   // 도착 공항 이름
    
    // === 좌석 및 탑승 정보 ===
    private String seatNumber;          // 좌석 번호 (12A, 15F 등)
    private String seatClass;           // 좌석 등급 (Economy, Business, First)
    private String boardingGroup;       // 탑승 그룹 (1, 2, 3)
    private String gateNumber;          // 게이트 번호 (A12, B5 등)
    
    // === 체크인 상태 정보 ===
    private String checkinStatus;       // 체크인 상태 (COMPLETED, CANCELLED 등)
    private String status;              // 일반 상태
    private Timestamp checkinTime;      // 체크인 완료 시간
    
    // === 비즈니스 로직 메서드 (Lombok이 생성하지 않는 커스텀 메서드) ===
    
    /**
     * 체크인 완료 여부 확인
     */
    public boolean isCheckinCompleted() {
        return "COMPLETED".equals(checkinStatus) && checkinTime != null;
    }
    
    /**
     * 좌석 변경 가능 여부 확인 (출발 1시간 전까지)
     */
    public boolean canChangeSeat() {
        if (departureTime == null || !isCheckinCompleted()) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        long depTime = departureTime.getTime();
        long timeDiff = depTime - currentTime;
        long oneHour = 60 * 60 * 1000; // 1시간
        
        return timeDiff > oneHour;
    }
    
    /**
     * 탑승 그룹 우선순위 반환 (1이 가장 높음)
     */
    public int getBoardingPriority() {
        if (boardingGroup == null) return 99;
        
        try {
            return Integer.parseInt(boardingGroup);
        } catch (NumberFormatException e) {
            return 99; // 기본값
        }
    }
    
    /**
     * 전체 승객명 반환 (firstName + lastName)
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
    
    /**
     * 체크인 완료 처리 (상태 일괄 업데이트)
     */
    public void completeCheckin(String seatNumber, String boardingGroup) {
        this.seatNumber = seatNumber;
        this.boardingGroup = boardingGroup;
        this.checkinStatus = "COMPLETED";
        this.checkinTime = new Timestamp(System.currentTimeMillis());
    }
}