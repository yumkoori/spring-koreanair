package org.doit.booking.controller;

import java.util.HashMap;
import java.util.Map;

import org.doit.booking.dto.PassengerDTO;
import org.doit.booking.service.BookingService;
import org.doit.booking.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class NonUserPwController {

	@Autowired
	private BookingService bookingService;
	   
    @PostMapping(value = "/save/nonUserPw", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, String>> updateNonUserPW(@RequestBody Map<String, String> payload) {
        String bookingId = payload.get("bookingId");
        String bookingPW = payload.get("bookingPW");

        bookingService.updateNonUserPW(bookingId, bookingPW);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "비회원 비밀번호가 저장되었습니다.");
        
        return ResponseEntity.ok()
                .header("Content-Type", "application/json;charset=UTF-8")
                .body(response);
    }
}
