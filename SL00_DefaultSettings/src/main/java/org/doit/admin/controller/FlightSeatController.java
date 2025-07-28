package org.doit.admin.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.admin.dto.FlightScheduleDTO;
import org.doit.admin.service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;



@Controller
@RequestMapping("/views")
public class FlightSeatController {

	@Autowired
    private FlightScheduleService flightService = new FlightScheduleService();

    @GetMapping("/index4")
    public String process(HttpServletRequest request, Model model) throws Exception {
        System.out.println("=== FlightSeatController 시작 ===");
        System.out.println("요청 URI: " + request.getRequestURI());
        System.out.println("요청 메소드: " + request.getMethod());
        
        return "adminpage/index4"; // JSP로 포워딩
    }
    
}