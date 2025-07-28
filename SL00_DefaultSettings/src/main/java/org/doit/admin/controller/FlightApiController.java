package org.doit.admin.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.doit.admin.dto.FlightScheduleDTO;
import org.doit.admin.service.FlightScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 비행 스케줄 데이터 API 전용 컨트롤러
 * JSON 응답만 처리하며, 페이지 로딩과 완전히 분리됨
 */
@RestController
@RequestMapping("/api/flights")
public class FlightApiController {

    @Autowired
    private FlightScheduleService flightService;

    /**
     * 비행 스케줄 데이터를 JSON으로 반환
     * @param date 조회할 날짜 (기본값: 오늘)
     * @param flightType 비행 유형 (기본값: all)
     * @return 비행 스케줄 리스트 (JSON 자동 변환)
     */
    @GetMapping
    public List<FlightScheduleDTO> getFlights(
            @RequestParam(value = "date", defaultValue = "") String date,
            @RequestParam(value = "flightType", defaultValue = "all") String flightType) {
        
        System.out.println("=== FlightApiController.getFlights 시작 ===");
        System.out.println("요청 파라미터 - date: " + date + ", flightType: " + flightType);
        
        // 기본값 설정
        if (date == null || date.trim().isEmpty()) {
            date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        }
        if (flightType == null || flightType.trim().isEmpty()) {
            flightType = "all";
        }
        
        try {
            // 스케줄 데이터 조회
            List<FlightScheduleDTO> flightList = flightService.getFlightData(date, flightType);
           
            System.out.println("API 응답 데이터 크기: " + flightList.size());
            
            return flightList; // @RestController가 자동으로 JSON 변환
            
        } catch (Exception e) {
            System.err.println("FlightApiController 오류: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("비행 스케줄 데이터 조회 실패: " + e.getMessage());
        }
    }
} 