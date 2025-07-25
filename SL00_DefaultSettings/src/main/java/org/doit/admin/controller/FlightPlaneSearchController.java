package org.doit.admin.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.doit.admin.service.SearchPlaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 비행 스케줄 데이터 저장 전용 컨트롤러
 * JSON 응답만 처리하며, 페이지 로딩과 완전히 분리됨
 */
@RestController
@RequestMapping("/flights/searchplane")
public class FlightPlaneSearchController {

	@Autowired
	private SearchPlaneService searchPlaneService;
	
	
	/**
	 * 비행 스케줄 데이터를 데이터베이스에 저장
	 * @param request HTTP 요청 객체
	 * @param response HTTP 응답 객체
	 * @throws IOException 입출력 예외
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public void planeSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("FlightSaveController 도착!");
		String flightid = request.getParameter("searchword");
		System.out.println("searchword = " + flightid);
		
		
		
		
		int check;
		try {
			check = searchPlaneService.searchplane(flightid);
			
	        // 1. 클라이언트(브라우저)에게 지금부터 보내는 데이터가 JSON 형식임을 알려줍니다.
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        // 2. 응답 데이터를 쓸 수 있는 PrintWriter 객체를 얻어옵니다.
	        PrintWriter out = response.getWriter();

	        // 3. 보낼 JSON 데이터를 문자열로 만듭니다. (예: {"check": 1} 또는 {"check": 0})
	        //    String.format을 사용하면 간단하게 만들 수 있습니다.
	        String jsonResponse = String.format("{\"check\": %d}", check);
	        
	        // 4. 생성한 JSON 문자열을 클라이언트로 보냅니다.
	        out.print(jsonResponse);
	        out.flush(); // 버퍼에 남아있는 데이터를 모두 전송
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
} 