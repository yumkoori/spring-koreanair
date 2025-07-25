package org.doit.reservation.util;

import javax.servlet.http.HttpSession;
import org.doit.reservation.domain.ReservationVO;
import lombok.extern.log4j.Log4j;

/**
 * 세션 관련 공통 유틸리티 클래스
 */
@Log4j
public class SessionUtils {
    
    /**
     * 세션에서 예약 정보를 안전하게 가져오는 메서드
     */
    public static ReservationVO getReservationFromSession(HttpSession session, String bookingId) {
        // checkinReservation 세션 확인
        ReservationVO checkinReservation = (ReservationVO) session.getAttribute("checkinReservation");
        if (checkinReservation != null && bookingId.equals(checkinReservation.getBookingId())) {
            log.info("세션에서 체크인 예약 정보 조회 성공 - bookingId: " + bookingId);
            return checkinReservation;
        }
        
        // lookupResult 세션 확인
        ReservationVO lookupResult = (ReservationVO) session.getAttribute("lookupResult");
        if (lookupResult != null && bookingId.equals(lookupResult.getBookingId())) {
            log.info("세션에서 조회 예약 정보 조회 성공 - bookingId: " + bookingId);
            return lookupResult;
        }
        
        // reservation 세션 확인 (detail 페이지에서 사용)
        ReservationVO reservation = (ReservationVO) session.getAttribute("reservation");
        if (reservation != null && bookingId.equals(reservation.getBookingId())) {
            log.info("세션에서 예약 정보 조회 성공 - bookingId: " + bookingId);
            return reservation;
        }
        
        log.warn("세션에 예약 정보 없음 - bookingId: " + bookingId);
        return null;
    }
    
    /**
     * 세션에 예약 정보를 저장하는 메서드
     */
    public static void setReservationToSession(HttpSession session, ReservationVO reservation, String sessionKey) {
        session.setAttribute(sessionKey, reservation);
        log.info("세션에 예약 정보 저장 - bookingId: " + reservation.getBookingId() + ", sessionKey: " + sessionKey);
    }
    
    /**
     * 세션에서 예약 정보를 제거하는 메서드
     */
    public static void removeReservationFromSession(HttpSession session, String sessionKey) {
        session.removeAttribute(sessionKey);
        log.info("세션에서 예약 정보 제거 - sessionKey: " + sessionKey);
    }
} 