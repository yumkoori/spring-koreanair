package org.doit.reservation.persistence;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.doit.reservation.domain.CheckinVO;

/**
 * 체크인 관련 MyBatis Mapper 인터페이스 (MyBatis 직접 사용 최적화)
 * Controller에서 직접 호출하여 사용
 */
public interface CheckinMapper {
    
    // ===== 조회 메서드 (Read-Only) =====
    
    /**
     * 예약번호로 체크인 정보 조회
     * @param bookingId 예약번호
     * @return 체크인 정보 또는 null
     */
    CheckinVO findByBookingId(@Param("bookingId") String bookingId);
    
    /**
     * 체크인 ID로 체크인 정보 조회
     * @param checkinId 체크인 ID
     * @return 체크인 정보 또는 null
     */
    CheckinVO findByCheckinId(@Param("checkinId") String checkinId);
    
    /**
     * 항공편의 좌석 맵 정보 조회 (좌석 선택용)
     * @param flightId 항공편 ID
     * @return 좌석 맵 정보 리스트
     */
    List<Map<String, Object>> getSeatMap(@Param("flightId") String flightId);
    
    /**
     * 특정 좌석의 사용 횟수 조회 (중복 확인용)
     * @param flightId 항공편 ID
     * @param seatNumber 좌석번호
     * @return 사용 횟수 (0: 사용가능, 1+: 사용중)
     */
    int countSeatUsage(@Param("flightId") String flightId, 
                      @Param("seatNumber") String seatNumber);
    
    /**
     * 출발 시간 조회 (좌석 변경 가능 여부 확인용)
     * @param bookingId 예약번호
     * @return 출발 시간 또는 null
     */
    Date getDepartureTime(@Param("bookingId") String bookingId);
    
    /**
     * 항공편별 체크인 완료 승객 목록 조회
     * @param flightId 항공편 ID
     * @return 체크인 완료 승객 목록
     */
    List<CheckinVO> findCheckedInPassengersByFlight(@Param("flightId") String flightId);
    
    /**
     * 탑승 그룹별 승객 목록 조회
     * @param flightId 항공편 ID
     * @param boardingGroup 탑승 그룹 (1, 2, 3)
     * @return 탑승 그룹별 승객 목록
     */
    List<CheckinVO> findPassengersByBoardingGroup(@Param("flightId") String flightId, 
                                                 @Param("boardingGroup") String boardingGroup);
    
    // ===== 생성/수정 메서드 (Transactional) =====
    
    /**
     * 체크인 정보 저장 (체크인 완료 처리)
     * @param checkinVO 체크인 정보
     * @return 영향받은 행 수 (1: 성공, 0: 실패)
     */
    int insertCheckin(CheckinVO checkinVO);
    
    /**
     * 좌석 변경 (좌석번호 + 탑승그룹 업데이트)
     * @param bookingId 예약번호
     * @param seatNumber 새로운 좌석번호
     * @param boardingGroup 새로운 탑승그룹
     * @return 영향받은 행 수
     */
    int updateSeat(@Param("bookingId") String bookingId, 
                   @Param("seatNumber") String seatNumber,
                   @Param("boardingGroup") String boardingGroup);
    
    /**
     * 연락처 정보 업데이트 (체크인 후 연락처 변경)
     * @param bookingId 예약번호
     * @param phoneNumber 전화번호
     * @param email 이메일
     * @return 영향받은 행 수
     */
    int updateContactInfo(@Param("bookingId") String bookingId, 
                         @Param("phoneNumber") String phoneNumber,
                         @Param("email") String email);
    
    /**
     * 체크인 상태 변경 (COMPLETED, CANCELLED 등)
     * @param bookingId 예약번호
     * @param status 체크인 상태
     * @return 영향받은 행 수
     */
    int updateCheckinStatus(@Param("bookingId") String bookingId, 
                           @Param("status") String status);
    
    /**
     * 게이트 정보 업데이트 (항공편 게이트 변경 시)
     * @param flightId 항공편 ID
     * @param gateNumber 게이트 번호
     * @return 영향받은 행 수
     */
    int updateGateInfo(@Param("flightId") String flightId, 
                      @Param("gateNumber") String gateNumber);
    
    /**
     * 체크인 취소 (체크인 정보 삭제)
     * @param bookingId 예약번호
     * @return 영향받은 행 수
     */
    int deleteCheckin(@Param("bookingId") String bookingId);
    
    // ===== 통계/집계 메서드 =====
    
    /**
     * 항공편별 체크인 완료 승객 수 조회
     * @param flightId 항공편 ID
     * @return 체크인 완료 승객 수
     */
    int countCheckedInPassengers(@Param("flightId") String flightId);
    
    /**
     * 좌석 등급별 체크인 승객 수 조회
     * @param flightId 항공편 ID
     * @param seatClass 좌석 등급 (Economy, Business, First)
     * @return 해당 등급 체크인 승객 수
     */
    int countCheckedInPassengersBySeatClass(@Param("flightId") String flightId, 
                                           @Param("seatClass") String seatClass);
    
    /**
     * 사용 가능한 좌석 목록 조회
     * @param flightId 항공편 ID
     * @param seatClass 좌석 등급 (선택사항)
     * @return 사용 가능한 좌석 목록
     */
    List<String> getAvailableSeats(@Param("flightId") String flightId, 
                                  @Param("seatClass") String seatClass);
    
    // ===== 좌석 변경 관련 메서드 =====
    
    /**
     * 기존 좌석 정보 조회
     * @param bookingId 예약번호
     * @return 기존 좌석번호 또는 null
     */
    String getExistingSeat(@Param("bookingId") String bookingId);
    
    /**
     * booking_seat에서 기존 좌석 삭제
     * @param bookingId 예약번호
     * @return 영향받은 행 수
     */
    int deleteBookingSeat(@Param("bookingId") String bookingId);
    
    /**
     * check_in에서 기존 체크인 정보 삭제
     * @param bookingId 예약번호
     * @return 영향받은 행 수
     */
    int deleteCheckIn(@Param("bookingId") String bookingId);
    
    /**
     * booking_seat에 새 좌석 정보 삽입
     * @param bookingSeatId 좌석 예약 ID
     * @param bookingId 예약번호
     * @param seatNumber 좌석번호
     * @return 영향받은 행 수
     */
        int insertBookingSeat(@Param("bookingSeatId") String bookingSeatId, 
                         @Param("bookingId") String bookingId, 
                         @Param("flightId") String flightId,
                         @Param("seatNumber") String seatNumber);
    
    /**
     * check_in에 새 체크인 정보 삽입
     * @param checkInId 체크인 ID
     * @param bookingSeatId 좌석 예약 ID
     * @return 영향받은 행 수
     */
    int insertCheckIn(@Param("checkInId") String checkInId,
                     @Param("bookingSeatId") String bookingSeatId);
    
    // ===== 중복 좌석 선택 방지 관련 메서드 =====
    
    /**
     * 현재 선택된 좌석 목록 조회
     * @param flightId 항공편 ID
     * @return 선택된 좌석 번호 목록
     */
    List<String> getOccupiedSeats(@Param("flightId") String flightId);
    
    /**
     * 특정 좌석 선택 여부 확인
     * @param flightId 항공편 ID
     * @param seatNumber 좌석번호
     * @return 선택 여부 (true: 선택됨, false: 선택되지 않음)
     */
    boolean isSeatOccupied(@Param("flightId") String flightId, 
                          @Param("seatNumber") String seatNumber);
    
    /**
     * 좌석 가격 조회
     * @param flightId 항공편 ID
     * @param seatNumber 좌석번호
     * @return 좌석 가격
     */
    int getSeatPrice(@Param("flightId") String flightId, 
                     @Param("seatNumber") String seatNumber);
}