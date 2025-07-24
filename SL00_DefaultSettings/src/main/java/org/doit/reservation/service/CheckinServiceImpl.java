package org.doit.reservation.service;

import java.util.List;
import java.util.UUID;
import java.util.Date;

import org.doit.reservation.domain.CheckinVO;
import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.persistence.CheckinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 체크인 서비스 구현체
 */
@Service
public class CheckinServiceImpl implements CheckinService {
    
    @Autowired
    private CheckinMapper checkinMapper;
    
    @Autowired
    private ReservationService reservationService;
    
    /**
     * UUID 생성 메서드
     * @param prefix 접두사 (예: "BS", "CHK")
     * @return 생성된 UUID
     */
    private String generateUUID(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8);
    }
    
    @Override
    public boolean isCheckinAvailable(ReservationVO reservation) {
        // 체크인 가능 시간 검증 (무제한으로 설정)
        if (reservation == null || reservation.getDepartureTime() == null) {
            return false;
        }
        
        // 체크인 시간을 무제한으로 설정 (항상 true 반환)
        return true;
    }
    
    @Override
    public CheckinVO getCheckinInfo(String bookingId) {
        return checkinMapper.findByBookingId(bookingId);
    }
    
    @Override
    public List<Object> getSeatMap(String flightId) {
        return (List<Object>) (List<?>) checkinMapper.getSeatMap(flightId);
    }
    
    @Override
    public boolean isSeatAvailable(String flightId, String seatNumber) {
        return checkinMapper.countSeatUsage(flightId, seatNumber) == 0;
    }
    
    @Override
    public boolean completeCheckin(CheckinVO checkinVO) {
        return checkinMapper.insertCheckin(checkinVO) > 0;
    }
    
    @Override
    public boolean canChangeSeat(String bookingId) {
        // 출발 시간이 1시간 이상 남았는지 확인
        Date departureTime = checkinMapper.getDepartureTime(bookingId);
        if (departureTime == null) {
            return false;
        }
        
        Date currentTime = new Date();
        long timeDifference = departureTime.getTime() - currentTime.getTime();
        
        // 1시간 이상 남았으면 좌석 변경 가능
        return timeDifference > (60 * 60 * 1000);
    }
    
    @Override
    public boolean updateSeat(String bookingId, String newSeatNumber) {
        return checkinMapper.updateSeat(bookingId, newSeatNumber, "1") > 0;
    }
    
    @Override
    public boolean updateContactInfo(String bookingId, String phoneNumber, String email) {
        return checkinMapper.updateContactInfo(bookingId, phoneNumber, email) > 0;
    }
    
    @Override
    public boolean saveSeatSelection(String bookingId, String seatId) {
        try {
            // 1. 예약 정보에서 flightId 조회
            ReservationVO reservation = reservationService.findReservationById(bookingId);
            if (reservation == null) {
                return false;
            }
            String flightId = reservation.getFlightId();
            
            // 2. 중복 좌석 선택 검증 추가
            if (!isSeatAvailableForSelection(flightId, seatId)) {
                return false;
            }
            
            // 3. 기존 좌석 정보 확인
            String existingSeat = checkinMapper.getExistingSeat(bookingId);
            
            if (existingSeat != null) {
                // 4. 좌석 변경: 기존 정보 삭제
                checkinMapper.deleteCheckIn(bookingId);
                checkinMapper.deleteBookingSeat(bookingId);
            }
            
            // 5. 새 좌석 정보 삽입
            String bookingSeatId = generateUUID("BS");
            String checkInId = generateUUID("CHK");
            
            int bookingSeatResult = checkinMapper.insertBookingSeat(bookingSeatId, bookingId, flightId, seatId);
            int checkInResult = checkinMapper.insertCheckIn(checkInId, bookingSeatId);
            
            return bookingSeatResult > 0 && checkInResult > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // ===== 중복 좌석 선택 방지 관련 메서드 =====
    
    @Override
    public List<String> getOccupiedSeats(String flightId) {
        return checkinMapper.getOccupiedSeats(flightId);
    }
    
    @Override
    public boolean isSeatAvailableForSelection(String flightId, String seatNumber) {
        return !checkinMapper.isSeatOccupied(flightId, seatNumber);
    }
    
    @Override
    public int getSeatPrice(String flightId, String seatNumber) {
        return checkinMapper.getSeatPrice(flightId, seatNumber);
    }
} 