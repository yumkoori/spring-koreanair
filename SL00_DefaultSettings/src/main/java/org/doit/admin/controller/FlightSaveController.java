package org.doit.admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.service.CheckDuplicationSchedulesDbService;
import org.doit.admin.service.SaveSchedulesDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/api/flights/save")
public class FlightSaveController {

	@Autowired
	private SaveSchedulesDBService saveSchedulesDBService;
	
	@Autowired
	private CheckDuplicationSchedulesDbService checkDuplicationSchedulesDbService;
	/**
	 * 비행 스케줄 데이터를 데이터베이스에 저장
	 * @param request HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws IOException 입출력 예외
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public void saveFlights(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 요청 인코딩을 UTF-8로 설정 (한글 깨짐 방지)
		request.setCharacterEncoding("UTF-8");
		
		Map<String, Object> responseMap = new HashMap<>();
		System.out.println("> SaveSchedulesDBHandler for Initial Page Load with Data called...");

		try {
			// 1. request의 body에서 JSON 데이터 읽기
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String jsonData = buffer.toString();

			// System.out.println("핸들러가 받은 JSON 데이터: " + jsonData);

			// 2. JSON → DTO 배열 → 리스트로 변환

			JsonObject root = JsonParser.parseString(jsonData).getAsJsonObject();
			JsonArray schedulesJsonArray = root.getAsJsonArray("schedules");

			Gson gson = new Gson();
			List<SaveSchedulesDBDTO> scheduleList = gson.fromJson(
					schedulesJsonArray,
					new com.google.gson.reflect.TypeToken<List<SaveSchedulesDBDTO>>(){}.getType()
					);
			/*
			 * Gson gson = new Gson(); SaveSchedulesDBDTO[] schedulesArray =
			 * gson.fromJson(jsonData, SaveSchedulesDBDTO[].class); List<SaveSchedulesDBDTO>
			 * scheduleList = Arrays.asList(schedulesArray);
			 */


			int checkduplication = checkDuplicationSchedulesDbService.DuplicationSchedulesDB(scheduleList);
			if( checkduplication >= 1 ) {
				responseMap.put("status", "Duplicationfail");
			    responseMap.put("message", "중복된 스케줄이 존재합니다.");
			   
				String jsonResponse = gson.toJson(responseMap);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(jsonResponse);
				System.out.println(checkduplication);
			    return;
			} 
			  int checkdb = saveSchedulesDBService.saveSchedulesDB(scheduleList);
			  
			  if (checkdb >= 1) {
			  responseMap.put("status", "success"); 
			  } else {
				  responseMap.put("status", "fail");
			  }
			  System.out.println(checkduplication);
			

			// int checkdb = dao.saveSchdulesDB(scheduleList);


		} catch (Exception e) {
			System.out.println("SaveSchedulesDBHandler 오류: " + e.getMessage());
			e.printStackTrace();

			responseMap.put("status", "error");
			responseMap.put("message", "비행 스케줄 저장 중 오류가 발생했습니다: " + e.getMessage());
		}

		// 공통 응답 처리
		try {
			Gson gson = new Gson();
			String jsonResponse = gson.toJson(responseMap);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonResponse);

			System.out.println("응답: ");
		} catch (IOException e) {
			System.err.println("응답 처리 중 오류: " + e.getMessage());
			throw e;
		}
	}
} 