package org.doit.member.controller;

import javax.servlet.http.HttpSession;
import java.util.List;

import org.doit.reservation.domain.ReservationVO;
import org.doit.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {
    
    @Autowired
    private ReservationService reservationService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(@RequestParam(value = "logout", required = false) String logout, 
                      HttpSession session, Model model) {
        
        // 로그인된 사용자의 예약 목록 조회
        Object user = session.getAttribute("user");
        if (user != null) {
            Integer userNo = null;
            if (user instanceof org.doit.member.model.User) {
                userNo = ((org.doit.member.model.User) user).getUserNo();
                log.info("사용자 정보 - userNo: " + userNo + ", userId: " + ((org.doit.member.model.User) user).getUserId());
                log.info("사용자 정보 - userNo 타입: " + (userNo != null ? userNo.getClass().getName() : "null"));
            }
            if (userNo != null) {
                log.info("사용자 번호로 예약 조회 시작: " + userNo);
                String userNoStr = String.valueOf(userNo);
                log.info("전달할 userNo 문자열: " + userNoStr + ", 길이: " + userNoStr.length());
                List<ReservationVO> userBookings = reservationService.getUserReservations(userNoStr);
                log.info("조회된 예약 수: " + (userBookings != null ? userBookings.size() : 0));
                session.setAttribute("userBookings", userBookings);
            } else {
                log.warn("사용자 번호가 null입니다.");
            }
        } else {
            log.info("세션에 사용자 정보가 없습니다.");
        }
        
        if ("kakao".equals(logout)) {
            model.addAttribute("message", "카카오 로그아웃이 완료되었습니다.");
        }
        return "index";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(HttpSession session) {
        // 로그인된 사용자의 예약 목록 조회
        Object user = session.getAttribute("user");
        if (user != null) {
            Integer userNo = null;
            if (user instanceof org.doit.member.model.User) {
                userNo = ((org.doit.member.model.User) user).getUserNo();
            }
            if (userNo != null) {
                log.info("사용자 번호로 예약 조회 시작: " + userNo);
                List<ReservationVO> userBookings = reservationService.getUserReservations(String.valueOf(userNo));
                log.info("조회된 예약 수: " + (userBookings != null ? userBookings.size() : 0));
                session.setAttribute("userBookings", userBookings);
            } else {
                log.warn("사용자 번호가 null입니다.");
            }
        } else {
            log.info("세션에 사용자 정보가 없습니다.");
        }
        return "index";
    }
}