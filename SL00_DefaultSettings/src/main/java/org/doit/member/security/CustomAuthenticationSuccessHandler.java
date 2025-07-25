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
        
        // 저장된 targetUrl이 있는지 확인
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
}