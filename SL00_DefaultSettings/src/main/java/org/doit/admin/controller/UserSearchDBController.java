package org.doit.admin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.dto.SeatLoadDTO;
import org.doit.admin.dto.SeatPriceLoadDTO;
import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.dto.UserInfoDTO;
import org.doit.admin.service.CheckDuplicationSchedulesDbService;
import org.doit.admin.service.SaveSchedulesDBService;
import org.doit.admin.service.SeatLoadService;
import org.doit.admin.service.SeatPriceDuplicationService;
import org.doit.admin.service.SeatPriceSaveService;
import org.doit.admin.service.SeatSaveDuplicationService;
import org.doit.admin.service.SeatSaveService;
import org.doit.admin.service.UserInformationService;
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
@RequestMapping("users/search")
public class UserSearchDBController {
	
	@Autowired
	UserInformationService userInformationService;
	/**
	 * 비행 스케줄 데이터를 데이터베이스에 저장
	 * @param request HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws IOException 입출력 예외
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void seatPriceSave(@RequestParam(value = "name", required = false) String userName, 
            HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
				
		Map<String, Object> responseMap = new HashMap<>();
		

		try {
			request.setCharacterEncoding("UTF-8");
			System.out.println("넘어온 유저 이름" + userName);
			
			List<UserInfoDTO> userInformationList = userInformationService.userInfoList(userName);
			
			System.out.println(userInformationList);
			
			responseMap.put("status", "success");
			responseMap.put("results", userInformationList);
			responseMap.put("message", "저장되어있는 좌석들을 불러옵니다");
	        
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