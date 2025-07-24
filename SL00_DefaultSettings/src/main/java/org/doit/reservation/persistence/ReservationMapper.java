package org.doit.reservation.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.domain.CancelReservationVO;

/**
 * 예약 관련 MyBatis Mapper 인터페이스 (MyBatis 직접 사용 최적화)
 * Controller에서 직접 호출하여 사용
 */
public interface ReservationMapper {
    
    // ===== 조회 메서드 (Read-Only) =====
    
    /**
     * 예약번호와 승객명으로 예약 조회 (비회원용)
     * @param bookingId 예약번호
     * @param lastName 승객 성(姓)
     * @return 예약 정보 또는 null
     */
    ReservationVO findByBookingIdAndName(@Param("bookingId") String bookingId, 
                                        @Param("lastName") String lastName);
    
    /**
     * 예약번호, 출발일, 승객명(성+이름)으로 예약 조회 (비회원용 - 보안 강화)
     * @param bookingId 예약번호
     * @param departureDate 출발일 (yyyy-MM-dd)
     * @param lastName 승객 성(姓)
     * @param firstName 승객 이름
     * @return 예약 정보 또는 null
     */
    ReservationVO findByBookingIdAndDateAndFullName(@Param("bookingId") String bookingId,
                                                   @Param("departureDate") String departureDate,
                                                   @Param("lastName") String lastName,
                                                   @Param("firstName") String firstName);
    
    /**
     * 예약번호와 사용자ID로 예약 조회 (회원용)
     * @param bookingId 예약번호
     * @param userId 사용자 ID
     * @return 예약 정보 또는 null
     */
    ReservationVO findByBookingIdAndUserId(@Param("bookingId") String bookingId, 
                                          @Param("userId") String userId);
    
    /**
     * 예약번호로 예약 조회 (단순 조회)
     * @param bookingId 예약번호
     * @return 예약 정보 또는 null
     */
    ReservationVO findByBookingId(@Param("bookingId") String bookingId);
    
    /**
     * 사용자ID로 예약 목록 조회 (회원 대시보드용)
     * @param userId 사용자 ID
     * @return 예약 목록 (빈 리스트 가능)
     */
    List<ReservationVO> findByUserId(@Param("userId") String userId);
    
    /**
     * 예약 상태별 예약 목록 조회
     * @param userId 사용자 ID
     * @param status 예약 상태 (CONFIRMED, CANCELLED 등)
     * @return 상태별 예약 목록
     */
    List<ReservationVO> findByUserIdAndStatus(@Param("userId") String userId, 
                                             @Param("status") String status);
    
    /**
     * 체크인 가능한 예약 목록 조회 (24시간 이내 출발)
     * @param userId 사용자 ID
     * @return 체크인 가능한 예약 목록
     */
    List<ReservationVO> findCheckinAvailableReservations(@Param("userId") String userId);
    
    // ===== 생성/수정/삭제 메서드 (Transactional) =====
    
    /**
     * 예약 생성
     * @param reservation 예약 정보
     * @return 영향받은 행 수 (1: 성공, 0: 실패)
     */
    int insertReservation(ReservationVO reservation);
    
    /**
     * 예약 정보 수정 (승객 정보, 연락처 등)
     * @param reservation 수정할 예약 정보
     * @return 영향받은 행 수
     */
    int updateReservation(ReservationVO reservation);
    
    /**
     * 예약 취소 (상태 변경 + 취소 사유)
     * @param bookingId 예약번호
     * @param reason 취소 사유
     * @return 영향받은 행 수
     */
    int cancelReservation(@Param("bookingId") String bookingId, 
                         @Param("reason") String reason);
    
    /**
     * 예약 상태 변경 (CONFIRMED, CANCELLED, COMPLETED 등)
     * @param bookingId 예약번호
     * @param status 새로운 상태
     * @return 영향받은 행 수
     */
    int updateReservationStatus(@Param("bookingId") String bookingId, 
                               @Param("status") String status);
    
    /**
     * 체크인 상태 업데이트
     * @param bookingId 예약번호
     * @param checkinStatus 체크인 상태 (NOT_CHECKED, COMPLETED 등)
     * @return 영향받은 행 수
     */
    int updateCheckinStatus(@Param("bookingId") String bookingId, 
                           @Param("checkinStatus") String checkinStatus);
    
    /**
     * 좌석 정보 업데이트 (좌석 선택/변경)
     * @param bookingId 예약번호
     * @param seatNumber 좌석번호
     * @param seatClass 좌석등급
     * @return 영향받은 행 수
     */
    int updateSeatInfo(@Param("bookingId") String bookingId, 
                      @Param("seatNumber") String seatNumber,
                      @Param("seatClass") String seatClass);
    
    // ===== 통계/집계 메서드 =====
    
    /**
     * 사용자의 총 예약 수 조회
     * @param userId 사용자 ID
     * @return 총 예약 수
     */
    int countReservationsByUserId(@Param("userId") String userId);
    
    /**
     * 특정 항공편의 예약 수 조회
     * @param flightId 항공편 ID
     * @return 해당 항공편 예약 수
     */
    int countReservationsByFlightId(@Param("flightId") String flightId);
    
    // ===== 예약취소 관련 메서드 =====
    
    /**
     * 예약취소 정보 조회 (위약금, 환불 정보 포함)
     * @param bookingId 예약번호
     * @param userId 사용자 ID
     * @return 취소 정보 또는 null
     */
    CancelReservationVO findCancelReservationInfo(@Param("bookingId") String bookingId, 
                                                 @Param("userId") String userId);
}