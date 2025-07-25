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
import org.doit.admin.service.SeatSaveDuplicationService;
import org.doit.admin.service.SeatSaveService;
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
@RequestMapping("/flights/seatsave")
public class SeatSaveController {
	
	@Autowired
	SeatSaveService seatSaveService;
	
	@Autowired
	SeatSaveDuplicationService seatSaveDuplicationService;
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
		System.out.println("> SeatSaveController 시작!");

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
			
			System.out.println("requestBody  :   " + requestBody);
			
			// 2. JSON 객체에서 seats 배열을 추출하여 DTO 리스트로 변환
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = parser.parse(requestBody).getAsJsonObject();
			
			// seats 배열 추출
			JsonArray seatsArray = jsonObject.getAsJsonArray("seats");
			List<SeatPriceSaveDTO> seatList = gson.fromJson(
					seatsArray,
					new com.google.gson.reflect.TypeToken<List<SeatPriceSaveDTO>>(){}.getType()
					);
			
			// 추가 정보도 사용 가능
			int totalCount = jsonObject.has("totalCount") ? jsonObject.get("totalCount").getAsInt() : 0;
			
			// System.out.println("좌석들의 JSON 데이터 값>> " + seatList);
			// System.out.println("전송된 좌석 수: " + totalCount);
						
			
			
			 int duplication = seatSaveDuplicationService.seatSaveDuplication(seatList, flightid);
			 System.out.println("중복 개수 검사" + duplication);
					 
			if(duplication >=1 ) {
				responseMap.put("status", "duplication");
				String jsonResponse = gson.toJson(responseMap);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse);
				return;
			}
			System.out.println("seatList (()))())())" + seatList);
			
			
			int priceExistenceCheck = seatSaveDuplicationService.seatSavePirceExistence(seatList, flightid);
			System.out.println("priceExistenceCheck" + priceExistenceCheck);
			if(priceExistenceCheck == 0 ) {
				responseMap.put("status", "existence");
				String jsonResponse = gson.toJson(responseMap);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse);
				return;
			}
						
			int check = seatSaveService.SeatSave(seatList, flightid, totalCount );
			
			if(check >= 1) {
			// 성공 응답 설정
			responseMap.put("status", "success");
			responseMap.put("message", "데이터 처리 완료");
			} else {
				responseMap.put("status", "fail");
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