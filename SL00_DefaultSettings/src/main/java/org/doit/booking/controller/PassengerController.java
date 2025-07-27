package org.doit.booking.controller;

import javax.servlet.http.HttpSession;

import org.doit.booking.dto.PassengerDTO;
import org.doit.booking.service.PassengerService;
import org.doit.member.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    @PostMapping("/passenger")
    public String savePassenger(@ModelAttribute PassengerDTO passengerDTO, HttpSession session) {
    	
        User user = (User) session.getAttribute("user");
        
        if (user != null) {
            Integer userNo = user.getUserNo();
            passengerDTO.setUserNo(userNo);  // 🎯 여기서 userNo 설정!
            System.out.println("세션에서 가져온 userNo: " + userNo);
        } else {
            System.out.println("세션에 사용자 정보가 없습니다. (비회원)");
            // 비회원의 경우 userNo는 null로 유지
        }
    	passengerService.savePassengerInfo(passengerDTO);
        return "OK";
    }
}