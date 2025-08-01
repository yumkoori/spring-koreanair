package org.doit.admin.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.doit.admin.dto.ReservationSearchDTO;
import org.doit.admin.service.ReservationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller  
public class ReservationSearchController {
	
	@Autowired
	ReservationSearchService reservationSearchService;
	
	public ReservationSearchController() {
		System.out.println("=== ReservationSearchController 생성됨 ===");
	}
			
	/**
	 * 이름으로 예약 검색 API (단순화)
	 */
	@GetMapping("/flight/reservationsearch")
	@ResponseBody
	public Map<String, Object> searchReservations(
	    @RequestParam(required = false) String searchKeyword
	) {
		System.out.println("=== ReservationSearchController.searchReservations 호출됨 ===");
		System.out.println("Service 객체: " + (reservationSearchService != null ? "정상" : "NULL"));
		System.out.println("검색 키워드: " + searchKeyword);
		
		Map<String, Object> responseMap = new HashMap<>();
		
		try {
			// 서비스에서 이름으로 예약 목록 조회
			List<ReservationSearchDTO> allReservations = reservationSearchService.reservationSearch("userName", searchKeyword, null);
			
			System.out.println("DB 조회 결과" + allReservations );
			
			// 응답 데이터 구성
			List<Map<String, Object>> responseReservations = new ArrayList<>();
			if (allReservations != null) {
				for (ReservationSearchDTO dto : allReservations) {
					Map<String, Object> reservationMap = new HashMap<>();
					reservationMap.put("reservationId", dto.getBookingId());
					reservationMap.put("userName", dto.getUserName());
					reservationMap.put("userEmail", dto.getEmail());
					reservationMap.put("userPhone", dto.getPhone());
					reservationMap.put("userBirth", dto.getBirthDate());
					reservationMap.put("flightNumber", dto.getFlightNO());
					
					// expireTime에서 날짜와 시간 분리
					String expireTimeStr = null;
					String reservationDate = null;
					
					if (dto.getExpireTime() != null) {
						// 날짜 부분을 예약일로 사용
						reservationDate = dto.getExpireTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						// 시간 부분만 expireTime으로 사용
						expireTimeStr = dto.getExpireTime().toLocalTime().toString();
					} else {
						// expireTime이 null이면 현재 날짜와 시간 사용
						reservationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					}
					
					// bookingDate가 있으면 그것을 우선 사용, 없으면 expireTime의 날짜 사용
					if (dto.getBookingDate() != null) {
						reservationDate = dto.getBookingDate();
					}
					
					reservationMap.put("expireTime", expireTimeStr);
					reservationMap.put("reservationDate", reservationDate);
					
					reservationMap.put("departure", dto.getStart());
					reservationMap.put("arrival", dto.getEnd());
					reservationMap.put("departureTime", dto.getStartDate());
					reservationMap.put("arrivalTime", dto.getEndDate());
					
					reservationMap.put("status", "completed");  // 상태를 완료로 고정
					reservationMap.put("seatClass", dto.getSeatClass());
					reservationMap.put("passengerCount", dto.getPassenger());
					reservationMap.put("totalAmount", dto.getTotalPrice());
					responseReservations.add(reservationMap);
					
					
				}
			}
			
			
			
			responseMap.put("success", true);
			responseMap.put("message", "검색이 완료되었습니다.");
			responseMap.put("reservations", responseReservations);
			responseMap.put("totalCount", responseReservations.size());
			
			System.out.println("응답 데이터 크기: " + responseReservations.size() + "건");
			
		} catch (Exception e) {
			System.out.println("예약 검색 오류: " + e.getMessage());
			e.printStackTrace();

			responseMap.put("success", false);
			responseMap.put("message", "예약 검색 중 오류가 발생했습니다: " + e.getMessage());
			responseMap.put("reservations", new ArrayList<>());
			responseMap.put("totalCount", 0);
		}

		return responseMap;
	}
} 