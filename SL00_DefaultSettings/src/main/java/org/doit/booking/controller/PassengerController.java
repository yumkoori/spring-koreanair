package org.doit.booking.controller;

import org.doit.booking.dto.PassengerDTO;
import org.doit.booking.service.PassengerService;
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
    public String savePassenger(@ModelAttribute PassengerDTO passengerDTO) {
    	//재 저장을 고려하여 저장전, 삭제 기능 고려 
        passengerService.savePassengerInfo(passengerDTO);
        return "OK";
    }
}