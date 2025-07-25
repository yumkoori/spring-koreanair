package org.doit.admin.controller;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.service.CheckDuplicationSchedulesDbService;
import org.doit.admin.service.RefreshSchedulesDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/api/flights/refresh")
public class FlightScheduleSynchronizationController {
	
	@Autowired
	CheckDuplicationSchedulesDbService checkDuplicationSchedulesDbService;
	
	@Autowired
	RefreshSchedulesDbService refreshSchedulesDbService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 요청 인코딩을 UTF-8로 설정 (한글 깨짐 방지)
		request.setCharacterEncoding("UTF-8");
		
		Map<String, Object> responseMap = new HashMap<>();
		System.out.println("> FlightScheduleSynchronizationController 불러짐 ");
		
		try {
			
			// 요청 메소드 확인
			String method = request.getMethod();
			System.out.println("요청 메소드: " + method);
			
			// Content-Type 확인
			String contentType = request.getContentType();
			System.out.println("Content-Type: " + contentType);
			
			// POST 요청인 경우 JSON 데이터 읽기
			if ("POST".equalsIgnoreCase(method)) {
				StringBuilder jsonData = new StringBuilder();
				String line;
				
				try (BufferedReader reader = request.getReader()) {
					while ((line = reader.readLine()) != null) {
						jsonData.append(line);
					}
				}
				
				String receivedJson = jsonData.toString();
				System.out.println("받은 JSON 데이터 길이: " + receivedJson.length());
				// System.out.println("받은 JSON 데이터: " + receivedJson);
				
		        // JSON 구조를 올바르게 파싱 (SaveSchedulesDBHandler와 동일한 방식)
		        JsonObject root = JsonParser.parseString(receivedJson).getAsJsonObject();
		        JsonArray currentSchedulesJsonArray = root.getAsJsonArray("currentSchedules");

		        Gson gson = new Gson();
		        List<SaveSchedulesDBDTO> refresList = gson.fromJson(
		            currentSchedulesJsonArray,
		            new com.google.gson.reflect.TypeToken<List<SaveSchedulesDBDTO>>(){}.getType()
		        );
		        
		        // 더미 데이터 3개 추가 (실제 API 데이터와 같은 형식으로 수정)
	            SaveSchedulesDBDTO dummy1 = new SaveSchedulesDBDTO();
	            dummy1.setId("2025-01-30-all-FLKE001_999");
	            dummy1.setFlightNo("KE00166");
	            dummy1.setOrigin("ICN");
	            dummy1.setDestination("LAX");
	            dummy1.setDepartureTime("14:30");
	            dummy1.setArrivalTime("18:45");
	            dummy1.setStatus("제공안함쓰");
	            dummy1.setAirline("대한항공");
	            
	            SaveSchedulesDBDTO dummy2 = new SaveSchedulesDBDTO();
	            dummy2.setId("2025-01-30-all-FLOZ205_998");
	            dummy2.setFlightNo("OZ20566");
	            dummy2.setOrigin("ICN");
	            dummy2.setDestination("NRT");
	            dummy2.setDepartureTime("16:20");
	            dummy2.setArrivalTime("19:30");
	            dummy2.setStatus("제공안함쓰");
	            dummy2.setAirline("아시아나항공");
	            
	            SaveSchedulesDBDTO dummy3 = new SaveSchedulesDBDTO();
	            dummy3.setId("2025-01-30-all-FLKE123_997");
	            dummy3.setFlightNo("KE12366");
	            dummy3.setOrigin("GMP");
	            dummy3.setDestination("KIX");
	            dummy3.setDepartureTime("09:15");
	            dummy3.setArrivalTime("11:40");
	            dummy3.setStatus("제공안함쓰");
	            dummy3.setAirline("대한항공");
	            
	            // 원본 리스트에 더미 데이터 추가
	            refresList.add(dummy1);
	            refresList.add(dummy2);
	            refresList.add(dummy3);
	            
	            System.out.println("더미 데이터 3개가 추가되었습니다.");
	            // System.out.println(refresList);
	            
		        	           
		            
		            int addedList = refreshSchedulesDbService.refreshSchedules(refresList);
		            System.out.println("addedList >>>>> " + addedList);
		            
		            if (addedList >= 1 ) {
			            responseMap.put("status", "success");
			            responseMap.put("message", "스케줄이 성공적으로 추가되었습니다.");
			            responseMap.put("newSchedules", addedList);
					} else {
			        	responseMap.put("status", "fail");
			        	responseMap.put("message", "새로 추가된 내용이 없습니다.");
			        	responseMap.put("newSchedules", null);
					}


		        
		       
				
	            String jsonResponse = gson.toJson(responseMap);
	            response.setContentType("application/json");
	            response.setCharacterEncoding("UTF-8");
	            response.getWriter().write(jsonResponse);
	            
	            return null;  // 바로 응답 종료

			}
			// POST가 아니면 null 반환(필요시 다른 처리 가능)
	        return null;

			
		} catch (Exception e) {
			System.err.println("RefreshSchedulesHandler 오류: " + e.getMessage());
			e.printStackTrace();
			
			// 오류 응답
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			StringBuilder errorJson = new StringBuilder();
			errorJson.append("{");
			errorJson.append("\"status\":\"error\",");
			errorJson.append("\"message\":\"스케줄 새로고침 중 오류가 발생했습니다: ");
			errorJson.append(e.getMessage().replace("\"", "\\\""));
			errorJson.append("\"");
			errorJson.append("}");
			
			response.getWriter().write(errorJson.toString());
			return null;
		}
	}
	

}
