package org.doit.member.security;

import java.util.List;

import org.doit.member.model.User;
import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.service.ReservationService;
import org.doit.reservation.util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    @Autowired
    private ReservationService reservationService;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        HttpSession session = request.getSession();
        User user = (User) authentication.getPrincipal();
        
        System.out.println("=== CustomAuthenticationSuccessHandler 시작 ===");
        System.out.println("로그인 사용자: " + user.getKoreanName());
        
        session.setAttribute("user", user);
        session.setAttribute("isAuthenticated", true);
        
        // 로그인 성공 시 사용자의 예약 목록을 세션에 저장
        List<ReservationVO> userBookings = reservationService.getUserReservations(String.valueOf(user.getUserNo()));
        session.setAttribute("userBookings", userBookings);
        
        // 취소 대기 중인 예약이 있는지 확인
        String pendingBookingId = (String) session.getAttribute("pendingCancelBookingId");
        ReservationVO pendingReservation = (ReservationVO) session.getAttribute("pendingCancelReservation");
        
        if (pendingBookingId != null && pendingReservation != null) {
            System.out.println("DEBUG: 취소 대기 중인 예약 발견 - bookingId: " + pendingBookingId);
            
            // 이름 일치 여부 확인
            boolean nameMatches = isUserNameMatch(user, pendingReservation);
            
            // 세션 정리
            session.removeAttribute("pendingCancelBookingId");
            session.removeAttribute("pendingCancelReservation");
            
            if (nameMatches) {
                // 일치: 취소 페이지로
                System.out.println("DEBUG: 이름 일치 - 취소 페이지로 리다이렉트");
                response.sendRedirect(request.getContextPath() + "/reservation/cancel.htm?bookingId=" + pendingBookingId);
                return;
            } else {
                // 불일치: 대시보드로
                System.out.println("DEBUG: 이름 불일치 - 대시보드로 리다이렉트");
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        }
        
        // 좌석선택 대기 중인 예약이 있는지 확인
        String pendingSeatBookingId = (String) session.getAttribute("pendingSeatBookingId");
        ReservationVO pendingSeatReservation = (ReservationVO) session.getAttribute("pendingSeatReservation");
        
        if (pendingSeatBookingId != null && pendingSeatReservation != null) {
            System.out.println("DEBUG: 좌석선택 대기 중인 예약 발견 - bookingId: " + pendingSeatBookingId);
            
            // 이름 일치 여부 확인
            boolean nameMatches = isUserNameMatch(user, pendingSeatReservation);
            
            // 세션 정리
            session.removeAttribute("pendingSeatBookingId");
            session.removeAttribute("pendingSeatReservation");
            
            if (nameMatches) {
                // 일치: 좌석선택 페이지로
                System.out.println("DEBUG: 이름 일치 - 좌석선택 페이지로 리다이렉트");
                response.sendRedirect(request.getContextPath() + "/checkin/seat.htm?bookingId=" + pendingSeatBookingId);
                return;
            } else {
                // 불일치: 대시보드로
                System.out.println("DEBUG: 이름 불일치 - 대시보드로 리다이렉트");
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        }
        
        // 예약변경 대기 중인 예약이 있는지 확인
        String pendingChangeBookingId = (String) session.getAttribute("pendingChangeBookingId");
        ReservationVO pendingChangeReservation = (ReservationVO) session.getAttribute("pendingChangeReservation");
        
        if (pendingChangeBookingId != null && pendingChangeReservation != null) {
            System.out.println("DEBUG: 예약변경 대기 중인 예약 발견 - bookingId: " + pendingChangeBookingId);
            
            // 이름 일치 여부 확인
            boolean nameMatches = isUserNameMatch(user, pendingChangeReservation);
            
            // 세션 정리
            session.removeAttribute("pendingChangeBookingId");
            session.removeAttribute("pendingChangeReservation");
            
            if (nameMatches) {
                // 일치: 예약변경 선택 페이지로
                System.out.println("DEBUG: 이름 일치 - 예약변경 선택 페이지로 리다이렉트");
                response.sendRedirect(request.getContextPath() + "/reservation/changeReservationSelect.htm?bookingId=" + pendingChangeBookingId);
                return;
            } else {
                // 불일치: 대시보드로
                System.out.println("DEBUG: 이름 불일치 - 대시보드로 리다이렉트");
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        }
        
        // 저장된 targetUrl이 있는지 확인 (기존 좌석선택 로직)
        String targetUrl = (String) session.getAttribute("targetUrl");
        System.out.println("targetUrl 세션 확인: " + targetUrl);
        if (targetUrl != null && targetUrl.contains("/checkin/seat.htm")) {
            // 좌석선택 페이지로의 요청인 경우
            session.removeAttribute("targetUrl"); // 세션에서 제거
            
            // URL에서 bookingId 추출
            String bookingId = extractBookingIdFromUrl(targetUrl);
            if (bookingId != null) {
                System.out.println("DEBUG: 좌석선택 처리 - bookingId: " + bookingId);
                // 세션에서 비회원 조회 결과 가져오기 (lookupResult로 저장됨)
                ReservationVO reservation = (ReservationVO) session.getAttribute("lookupResult");
                if (reservation != null && bookingId.equals(reservation.getBookingId())) {
                    // 로그인한 사용자의 이름과 예약의 승객명 비교
                    String loginUserName = user.getKoreanName(); // 로그인한 사용자의 한국어 이름
                    String reservationPassengerName = reservation.getFullPassengerName(); // 예약의 승객명 (firstName + lastName)
                    
                    System.out.println("DEBUG: 이름 비교 - 로그인 사용자: " + loginUserName + ", 예약 승객명: " + reservationPassengerName);
                    
                    // 이름 비교 (한국어 이름 또는 영어 이름으로 비교)
                    boolean nameMatches = false;
                    if (loginUserName != null && reservationPassengerName != null) {
                        // 한국어 이름 비교
                        if (loginUserName.equals(reservationPassengerName)) {
                            nameMatches = true;
                            System.out.println("DEBUG: 한국어 이름 일치");
                        }
                        // 영어 이름 비교
                        else if (user.getEnglishName() != null && user.getEnglishName().equals(reservationPassengerName)) {
                            nameMatches = true;
                            System.out.println("DEBUG: 영어 이름 일치");
                        }
                        // firstName + lastName 조합 비교 (이미 getFullPassengerName에서 처리됨)
                        else if (reservation.getFirstName() != null && reservation.getLastName() != null) {
                            String fullName = reservation.getFirstName() + " " + reservation.getLastName();
                            if (loginUserName.equals(fullName) || 
                                (user.getEnglishName() != null && user.getEnglishName().equals(fullName))) {
                                nameMatches = true;
                                System.out.println("DEBUG: 풀네임 조합 일치");
                            }
                        }
                    }
                    
                    System.out.println("DEBUG: 이름 매칭 결과: " + nameMatches);
                    
                    if (nameMatches) {
                        // 비회원조회값과 로그인값이 같으면 좌석선택 페이지로
                        System.out.println("DEBUG: 좌석선택 페이지로 리다이렉트: " + targetUrl);
                        response.sendRedirect(request.getContextPath() + targetUrl);
                        return;
                    } else {
                        // 비회원조회값과 로그인값이 다르면 대시보드로
                        System.out.println("DEBUG: 이름 불일치로 대시보드로 리다이렉트");
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                        return;
                    }
                } else {
                    System.out.println("DEBUG: 세션에서 예약 정보를 찾을 수 없음 - bookingId: " + bookingId);
                }
            } else {
                System.out.println("DEBUG: bookingId를 추출할 수 없음 - targetUrl: " + targetUrl);
            }
        }
        
        // 기본 리다이렉트
        String defaultTargetUrl = "/dashboard";
        if ("admin".equals(user.getUserId())) {
            defaultTargetUrl = "/admin";
        }
        
        response.sendRedirect(request.getContextPath() + defaultTargetUrl);
    }
    
    private String extractBookingIdFromUrl(String url) {
        if (url != null && url.contains("bookingId=")) {
            int startIndex = url.indexOf("bookingId=") + 10;
            int endIndex = url.indexOf("&", startIndex);
            if (endIndex == -1) {
                endIndex = url.length();
            }
            return url.substring(startIndex, endIndex);
        }
        return null;
    }
    
    /**
     * 로그인 사용자와 예약 정보의 이름 매칭을 검증하는 유틸리티 메서드
     */
    private boolean isUserNameMatch(User loginUser, ReservationVO reservation) {
        if (loginUser == null || reservation == null) {
            return false;
        }
        
        String loginKoreanName = loginUser.getKoreanName();
        String loginEnglishName = loginUser.getEnglishName();
        String reservationPassengerName = reservation.getFullPassengerName();
        
        System.out.println("DEBUG: 이름 매칭 검증 - 로그인 한국어명: " + loginKoreanName + 
                ", 로그인 영어명: " + loginEnglishName + 
                ", 예약 승객명: " + reservationPassengerName);
        
        // 1. 한국어 이름과 전체 승객명 비교
        if (loginKoreanName != null && reservationPassengerName != null && 
            loginKoreanName.equals(reservationPassengerName)) {
            System.out.println("DEBUG: 매칭 성공: 한국어 이름 = 전체 승객명");
            return true;
        }
        
        // 2. 영어 이름과 전체 승객명 비교
        if (loginEnglishName != null && reservationPassengerName != null && 
            loginEnglishName.equals(reservationPassengerName)) {
            System.out.println("DEBUG: 매칭 성공: 영어 이름 = 전체 승객명");
            return true;
        }
        
        // 3. 한국어 이름과 lastName 비교
        if (loginKoreanName != null && reservation.getLastName() != null && 
            loginKoreanName.equals(reservation.getLastName())) {
            System.out.println("DEBUG: 매칭 성공: 한국어 이름 = lastName");
            return true;
        }
        
        // 4. 영어 이름과 firstName 비교
        if (loginEnglishName != null && reservation.getFirstName() != null && 
            loginEnglishName.equals(reservation.getFirstName())) {
            System.out.println("DEBUG: 매칭 성공: 영어 이름 = firstName");
            return true;
        }
        
        // 5. firstName + lastName 조합과 비교
        if (reservation.getFirstName() != null && reservation.getLastName() != null) {
            String fullName = reservation.getFirstName() + " " + reservation.getLastName();
            
            // 한국어 이름과 firstName + lastName 조합 비교
            if (loginKoreanName != null && loginKoreanName.equals(fullName)) {
                System.out.println("DEBUG: 매칭 성공: 한국어 이름 = firstName + lastName");
                return true;
            }
            
            // 영어 이름과 firstName + lastName 조합 비교
            if (loginEnglishName != null && loginEnglishName.equals(fullName)) {
                System.out.println("DEBUG: 매칭 성공: 영어 이름 = firstName + lastName");
                return true;
            }
        }
        
        // 6. 공백 제거 후 비교 (공백으로 인한 매칭 실패 방지)
        if (loginKoreanName != null && reservationPassengerName != null) {
            String trimmedKorean = loginKoreanName.trim();
            String trimmedPassenger = reservationPassengerName.trim();
            if (trimmedKorean.equals(trimmedPassenger)) {
                System.out.println("DEBUG: 매칭 성공: 공백 제거 후 한국어 이름 = 전체 승객명");
                return true;
            }
        }
        
        if (loginEnglishName != null && reservationPassengerName != null) {
            String trimmedEnglish = loginEnglishName.trim();
            String trimmedPassenger = reservationPassengerName.trim();
            if (trimmedEnglish.equals(trimmedPassenger)) {
                System.out.println("DEBUG: 매칭 성공: 공백 제거 후 영어 이름 = 전체 승객명");
                return true;
            }
        }
        
        System.out.println("DEBUG: 이름 매칭 실패 - 모든 조합이 일치하지 않음");
        return false;
    }
}