package org.doit.reservation.service;

import java.util.List;

import org.doit.reservation.domain.CheckinVO;
import org.doit.reservation.domain.ReservationVO;

/**
 * 체크인 서비스 인터페이스
 */
public interface CheckinService {
    
    /**
     * 체크인 가능 여부 확인 (시간 검증)
     */
    boolean isCheckinAvailable(ReservationVO reservation);
    
    /**
     * 체크인 정보 조회
     */
    CheckinVO getCheckinInfo(String bookingId);
    
    /**
     * 좌석 맵 정보 조회
     */
    List<Object> getSeatMap(String flightId);
    
    /**
     * 좌석 사용 가능 여부 확인
     */
    boolean isSeatAvailable(String flightId, String seatNumber);
    
    /**
     * 체크인 완료 처리
     */
    boolean completeCheckin(CheckinVO checkinVO);
    
    /**
     * 좌석 변경 가능 여부 확인
     */
    boolean canChangeSeat(String bookingId);
    
    /**
     * 좌석 변경 처리
     */
    boolean updateSeat(String bookingId, String newSeatNumber);
    
    /**
     * 연락처 정보 업데이트
     */
    boolean updateContactInfo(String bookingId, String phoneNumber, String email);
    
    /**
     * 좌석 선택 저장 처리
     */
    boolean saveSeatSelection(String bookingId, String seatId);
    
    // ===== 중복 좌석 선택 방지 관련 메서드 =====
    
    /**
     * 현재 선택된 좌석 목록 조회
     */
    List<String> getOccupiedSeats(String flightId);
    
    /**
     * 특정 좌석 선택 가능 여부 확인 (중복 방지용)
     */
    boolean isSeatAvailableForSelection(String flightId, String seatNumber);
    
    /**
     * 좌석 가격 조회
     */
    int getSeatPrice(String flightId, String seatNumber);
} 