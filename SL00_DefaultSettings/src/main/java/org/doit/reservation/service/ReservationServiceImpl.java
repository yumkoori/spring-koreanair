package org.doit.reservation.service;

import java.util.List;

import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.domain.CancelReservationVO;
import org.doit.reservation.persistence.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 예약 서비스 구현체
 */
@Service
public class ReservationServiceImpl implements ReservationService {
    
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
        List<ReservationVO> reservations = reservationMapper.findByUserId(userId);
        if (reservations != null) {
            for (ReservationVO reservation : reservations) {
                validateReservation(reservation);
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