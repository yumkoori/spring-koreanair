package org.doit.reservation.service;

import java.util.List;

import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.domain.CancelReservationVO;
import org.doit.reservation.persistence.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 예약 서비스 구현체
 */
@Service
public class ReservationServiceImpl implements ReservationService {
    
    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    private ReservationMapper reservationMapper;
    
    @Override
    public ReservationVO findReservationByIdAndName(String bookingId, String lastName, String userId) {
        ReservationVO reservation = reservationMapper.findByBookingIdAndName(bookingId, lastName);
        if (reservation != null) {
            validateReservation(reservation);
        }
        return reservation;
    }
    
    @Override
    public ReservationVO findReservationByIdAndDateAndFullName(String bookingId, String departureDate, String lastName, String firstName) {
        ReservationVO reservation = reservationMapper.findByBookingIdAndDateAndFullName(bookingId, departureDate, lastName, firstName);
        if (reservation != null) {
            validateReservation(reservation);
        }
        return reservation;
    }
    
    @Override
    public ReservationVO findReservationByIdAndUserId(String bookingId, String userId) {
        ReservationVO reservation = reservationMapper.findByBookingIdAndUserId(bookingId, userId);
        if (reservation != null) {
            validateReservation(reservation);
        }
        return reservation;
    }
    
    @Override
    public ReservationVO findReservationById(String bookingId) {
        ReservationVO reservation = reservationMapper.findByBookingId(bookingId);
        if (reservation != null) {
            validateReservation(reservation);
        }
        return reservation;
    }
    
    @Override
    public List<ReservationVO> getUserReservations(String userId) {
        log.info("ReservationServiceImpl.getUserReservations 호출 - userId: " + userId);
        List<ReservationVO> reservations = reservationMapper.findByUserId(userId);
        log.info("DB 조회 결과 - 예약 수: " + (reservations != null ? reservations.size() : 0));
        if (reservations != null && !reservations.isEmpty()) {
            log.info("첫 번째 예약의 user_id: " + reservations.get(0).getUserId());
        }
        if (reservations != null) {
            for (ReservationVO reservation : reservations) {
                try {
                    validateReservation(reservation);
                } catch (IllegalArgumentException e) {
                    log.warn("예약 검증 실패 - bookingId: " + reservation.getBookingId() + ", 에러: " + e.getMessage());
                    // 검증 실패해도 예약 목록에는 포함시킴
                }
            }
        }
        return reservations;
    }
    
    @Override
    public boolean createReservation(ReservationVO reservation) {
        validateReservationForCreation(reservation);
        return reservationMapper.insertReservation(reservation) > 0;
    }
    
    @Override
    public boolean updateReservation(ReservationVO reservation) {
        validateReservationForUpdate(reservation);
        return reservationMapper.updateReservation(reservation) > 0;
    }
    
    @Override
    public boolean cancelReservation(String bookingId, String reason) {
        if (reason == null || reason.trim().isEmpty()) {
            throw new IllegalArgumentException("취소 사유는 필수입니다.");
        }
        return reservationMapper.cancelReservation(bookingId, reason) > 0;
    }
    
    @Override
    public CancelReservationVO getCancelReservationInfo(String bookingId, String userId) {
        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("예약번호는 필수입니다.");
        }
        return reservationMapper.findCancelReservationInfo(bookingId, userId);
    }
    
    /**
     * 예약 정보 유효성 검증
     */
    private void validateReservation(ReservationVO reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("예약 정보가 없습니다.");
        }
        
        if (reservation.getBookingId() == null || reservation.getBookingId().trim().isEmpty()) {
            throw new IllegalArgumentException("예약번호는 필수입니다.");
        }
        
        if (reservation.getPassengerName() == null || reservation.getPassengerName().trim().isEmpty()) {
            throw new IllegalArgumentException("승객명은 필수입니다.");
        }
    }
    
    /**
     * 예약 생성 시 유효성 검증
     */
    private void validateReservationForCreation(ReservationVO reservation) {
        validateReservation(reservation);
        
        if (reservation.getFlightId() == null || reservation.getFlightId().trim().isEmpty()) {
            throw new IllegalArgumentException("항공편 정보는 필수입니다.");
        }
        
        if (reservation.getDepartureTime() == null) {
            throw new IllegalArgumentException("출발 시간은 필수입니다.");
        }
    }
    
    /**
     * 예약 수정 시 유효성 검증
     */
    private void validateReservationForUpdate(ReservationVO reservation) {
        validateReservation(reservation);
        
        if (reservation.getBookingId() == null || reservation.getBookingId().trim().isEmpty()) {
            throw new IllegalArgumentException("예약번호는 필수입니다.");
        }
    }
} 