package org.doit.admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.service.CheckDuplicationSchedulesDbService;
import org.doit.admin.service.SaveSchedulesDBService;
import org.doit.admin.service.SeatPriceDuplicationService;
import org.doit.admin.service.SeatPriceSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 비행 스케줄 데이터 저장 전용 컨트롤러
 * JSON 응답만 처리하며, 페이지 로딩과 완전히 분리됨
 */
@RestController
@RequestMapping("/flights/eachpricesave")
public class SeatEachPriceSaveController {

	@Autowired
	SeatPriceSaveService seatPriceSaveService;

	@Autowired
	SeatPriceDuplicationService seatPriceDuplicationService;

	/**
	 * 비행 스케줄 데이터를 데이터베이스에 저장
	 * @param request HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws IOException 입출력 예외
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void seatPriceSave(@RequestParam(value = "flight_id", required = false) String flightid, 
			HttpServletRequest request, 
			HttpServletResponse response) throws IOException {

		Map<String, Object> responseMap = new HashMap<>();

		// flight_id 파라미터가 없거나 비어있는 경우 처리
		if(flightid == null || flightid.trim().isEmpty()) {
			responseMap.put("status", "Null");
			responseMap.put("message", "flight_id 파라미터가 필요합니다.");

			// 응답 전송
			Gson gson = new Gson();
			String jsonResponse = gson.toJson(responseMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonResponse);
			return;
		}

		System.out.println("<><><><><><><><><><><><>" + flightid);
		System.out.println("> SeatPriceSaveController 시작!");

		try {
			// 요청 인코딩을 UTF-8로 설정 (한글 깨짐 방지)
			request.setCharacterEncoding("UTF-8");
			// 프론트엔드에서 넘어오는 JSON 데이터 읽기
			StringBuilder jsonData = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;

			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}

			String requestBody = jsonData.toString();

			// 2. JSON 배열을 직접 DTO 리스트로 변환
			Gson gson = new Gson();
			SeatPriceSaveDTO eachprice = gson.fromJson(requestBody, SeatPriceSaveDTO.class);
			System.out.println(eachprice);

			int duplication = seatPriceDuplicationService.duplicationSeatEachPrice(eachprice, flightid);

			if(duplication >=1 ) {
				responseMap.put("status", "duplication");
				String jsonResponse = gson.toJson(responseMap);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse);
				return;
			}


			int check = seatPriceSaveService.seatEachPriceSave(eachprice, flightid);
			if(check>= 1) { 
				responseMap.put("status", "success");
				responseMap.put("message", "데이터 처리 완료"); 
				} else { responseMap.put("status","fail"); 
				responseMap.put("message", "DB에 좌석 가격저장에 실패했습니다"); 
				}
			
		} catch (Exception e) {
			System.out.println("SaveSchedulesDBHandler 오류: " + e.getMessage());
			e.printStackTrace();

			responseMap.put("status", "error");
			responseMap.put("message", "비행 스케줄 저장 중 오류가 발생했습니다: " + e.getMessage());
		}

		// 공통 응답 처리
		Gson gson = new Gson();
		String jsonResponse = gson.toJson(responseMap);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse);

		System.out.println("응답: " + jsonResponse);
	}
} 