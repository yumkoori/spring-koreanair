package org.doit.reservation.service;

import java.util.List;

import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.domain.CancelReservationVO;

/**
 * 예약 서비스 인터페이스
 */
public interface ReservationService {
    
    /**
     * 예약번호와 승객명으로 예약 조회 (비회원용)
     */
    ReservationVO findReservationByIdAndName(String bookingId, String lastName, String userId);
    
    /**
     * 예약번호, 출발일, 성, 이름으로 예약 조회 (비회원용 - 4개 값 검증)
     */
    ReservationVO findReservationByIdAndDateAndFullName(String bookingId, String departureDate, String lastName, String firstName);
    
    /**
     * 예약번호와 사용자ID로 예약 조회 (회원용)
     */
    ReservationVO findReservationByIdAndUserId(String bookingId, String userId);
    
    /**
     * 예약번호로 예약 조회
     */
    ReservationVO findReservationById(String bookingId);
    
    /**
     * 사용자의 모든 예약 목록 조회
     */
    List<ReservationVO> getUserReservations(String userId);
    
    /**
     * 예약 생성
     */
    boolean createReservation(ReservationVO reservation);
    
    /**
     * 예약 수정
     */
    boolean updateReservation(ReservationVO reservation);
    
    /**
     * 예약 취소
     */
    boolean cancelReservation(String bookingId, String reason);
    
    /**
     * 예약취소 정보 조회 (위약금, 환불 정보 포함)
     */
    CancelReservationVO getCancelReservationInfo(String bookingId, String userId);
} 