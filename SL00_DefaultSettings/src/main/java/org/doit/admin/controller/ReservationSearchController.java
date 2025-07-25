package org.doit.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 예약 검색 전용 컨트롤러
 * JSON 응답만 처리
 */
@RestController
@RequestMapping("/flight")
public class ReservationSearchController {
	/**
	 * 예약 검색 API
	 * @param searchType 검색 타입 (reservationId, userName, userEmail, phone)
	 * @param searchKeyword 검색 키워드
	 * @param reservationStatus 예약 상태 필터
	 * @param page 페이지 번호
	 * @param size 페이지 크기
	 * @return 검색 결과
	 */
	@GetMapping("/reservationsearch")
	@ResponseBody
	public Map<String, Object> searchReservations(
	    @RequestParam String searchType,
	    @RequestParam(required = false) String searchKeyword,
	    @RequestParam(required = false) String reservationStatus,
	    @RequestParam(defaultValue = "1") int page,
	    @RequestParam(defaultValue = "10") int size
	) {
		Map<String, Object> responseMap = new HashMap<>();
		
		System.out.println("예약 검색 요청 - searchType: " + searchType + 
				", keyword: " + searchKeyword + 
				", status: " + reservationStatus + 
				", page: " + page + 
				", size: " + size);

		try {
			// 목 데이터 생성 (실제 프로젝트에서는 DB에서 조회)
			List<Map<String, Object>> mockReservations = createMockReservations();
			
			// 검색 조건 적용
			List<Map<String, Object>> filteredReservations = filterReservations(
				mockReservations, searchType, searchKeyword, reservationStatus);
			
			// 페이지네이션 적용
			int totalCount = filteredReservations.size();
			int startIndex = (page - 1) * size;
			int endIndex = Math.min(startIndex + size, totalCount);
			
			List<Map<String, Object>> pageReservations = new ArrayList<>();
			if (startIndex < totalCount) {
				pageReservations = filteredReservations.subList(startIndex, endIndex);
			}
			
			responseMap.put("success", true);
			responseMap.put("reservations", pageReservations);
			responseMap.put("totalCount", totalCount);
			responseMap.put("currentPage", page);
			responseMap.put("pageSize", size);
			responseMap.put("message", totalCount + "건의 검색 결과를 찾았습니다.");
	        
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
	
	/**
	 * 목 데이터 생성
	 */
	private List<Map<String, Object>> createMockReservations() {
		List<Map<String, Object>> reservations = new ArrayList<>();
		
		Map<String, Object> reservation1 = new HashMap<>();
		reservation1.put("reservationId", "KE240101001");
		reservation1.put("userName", "김철수");
		reservation1.put("userEmail", "kim@example.com");
		reservation1.put("userPhone", "010-1234-5678");
		reservation1.put("userBirth", "1985-03-15");
		reservation1.put("flightNumber", "KE001");
		reservation1.put("departure", "인천(ICN)");
		reservation1.put("arrival", "도쿄(NRT)");
		reservation1.put("departureTime", "2024-03-15 09:00");
		reservation1.put("arrivalTime", "2024-03-15 11:30");
		reservation1.put("reservationDate", "2024-02-01 14:30");
		reservation1.put("status", "confirmed");
		reservation1.put("seatClass", "이코노미");
		reservation1.put("passengerCount", 2);
		reservation1.put("totalAmount", 850000);
		reservations.add(reservation1);
		
		Map<String, Object> reservation2 = new HashMap<>();
		reservation2.put("reservationId", "KE240101002");
		reservation2.put("userName", "이영희");
		reservation2.put("userEmail", "lee@example.com");
		reservation2.put("userPhone", "010-2345-6789");
		reservation2.put("userBirth", "1990-07-22");
		reservation2.put("flightNumber", "KE123");
		reservation2.put("departure", "인천(ICN)");
		reservation2.put("arrival", "파리(CDG)");
		reservation2.put("departureTime", "2024-03-20 13:45");
		reservation2.put("arrivalTime", "2024-03-21 07:15");
		reservation2.put("reservationDate", "2024-02-02 10:15");
		reservation2.put("status", "pending");
		reservation2.put("seatClass", "비즈니스");
		reservation2.put("passengerCount", 1);
		reservation2.put("totalAmount", 2500000);
		reservations.add(reservation2);
		
		Map<String, Object> reservation3 = new HashMap<>();
		reservation3.put("reservationId", "KE240101003");
		reservation3.put("userName", "박민수");
		reservation3.put("userEmail", "park@example.com");
		reservation3.put("userPhone", "010-3456-7890");
		reservation3.put("userBirth", "1988-12-03");
		reservation3.put("flightNumber", "KE456");
		reservation3.put("departure", "부산(PUS)");
		reservation3.put("arrival", "방콕(BKK)");
		reservation3.put("departureTime", "2024-03-18 16:20");
		reservation3.put("arrivalTime", "2024-03-18 19:45");
		reservation3.put("reservationDate", "2024-02-03 16:45");
		reservation3.put("status", "cancelled");
		reservation3.put("seatClass", "이코노미");
		reservation3.put("passengerCount", 3);
		reservation3.put("totalAmount", 1200000);
		reservations.add(reservation3);
		
		Map<String, Object> reservation4 = new HashMap<>();
		reservation4.put("reservationId", "KE240101004");
		reservation4.put("userName", "정수연");
		reservation4.put("userEmail", "jung@example.com");
		reservation4.put("userPhone", "010-4567-8901");
		reservation4.put("userBirth", "1992-05-18");
		reservation4.put("flightNumber", "KE789");
		reservation4.put("departure", "인천(ICN)");
		reservation4.put("arrival", "뉴욕(JFK)");
		reservation4.put("departureTime", "2024-03-25 11:00");
		reservation4.put("arrivalTime", "2024-03-25 14:30");
		reservation4.put("reservationDate", "2024-02-04 09:20");
		reservation4.put("status", "completed");
		reservation4.put("seatClass", "퍼스트");
		reservation4.put("passengerCount", 2);
		reservation4.put("totalAmount", 4800000);
		reservations.add(reservation4);
		
		Map<String, Object> reservation5 = new HashMap<>();
		reservation5.put("reservationId", "KE240101005");
		reservation5.put("userName", "최동훈");
		reservation5.put("userEmail", "choi@example.com");
		reservation5.put("userPhone", "010-5678-9012");
		reservation5.put("userBirth", "1987-09-11");
		reservation5.put("flightNumber", "KE321");
		reservation5.put("departure", "제주(CJU)");
		reservation5.put("arrival", "오사카(KIX)");
		reservation5.put("departureTime", "2024-03-22 08:30");
		reservation5.put("arrivalTime", "2024-03-22 10:15");
		reservation5.put("reservationDate", "2024-02-05 13:10");
		reservation5.put("status", "confirmed");
		reservation5.put("seatClass", "이코노미");
		reservation5.put("passengerCount", 1);
		reservation5.put("totalAmount", 320000);
		reservations.add(reservation5);
		
		return reservations;
	}
	
	/**
	 * 예약 필터링
	 */
	private List<Map<String, Object>> filterReservations(List<Map<String, Object>> reservations, 
			String searchType, String searchKeyword, String reservationStatus) {
		
		List<Map<String, Object>> filtered = new ArrayList<>();
		
		for (Map<String, Object> reservation : reservations) {
			boolean textMatch = true;
			boolean statusMatch = true;
			
			// 텍스트 검색 조건
			if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
				String keyword = searchKeyword.toLowerCase().trim();
				switch (searchType) {
					case "reservationId":
						textMatch = ((String) reservation.get("reservationId")).toLowerCase().contains(keyword);
						break;
					case "userName":
						textMatch = ((String) reservation.get("userName")).toLowerCase().contains(keyword);
						break;
					case "userEmail":
						textMatch = ((String) reservation.get("userEmail")).toLowerCase().contains(keyword);
						break;
					case "phone":
						textMatch = ((String) reservation.get("userPhone")).contains(keyword);
						break;
				}
			}
			
			// 상태 필터 조건
			if (reservationStatus != null && !reservationStatus.trim().isEmpty()) {
				statusMatch = reservationStatus.equals(reservation.get("status"));
			}
			
			if (textMatch && statusMatch) {
				filtered.add(reservation);
			}
		}
		
		return filtered;
	}
} 