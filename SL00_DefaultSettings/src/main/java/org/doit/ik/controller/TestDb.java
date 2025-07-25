package org.doit.ik.controller;

import org.doit.ik.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ik")
public class TestDb {
	
	@Autowired
	TestMapper testMapper;
	
	@GetMapping("/test")
	public String testDb(Model model) {
	    try {
	        String testValue = testMapper.getTestValue();
	        String currentTime = testMapper.getCurrentTime();
	        String dbVersion = testMapper.getDatabaseVersion();
	        
	        model.addAttribute("testResult", "성공");
	        model.addAttribute("testValue", testValue);
	        model.addAttribute("currentTime", currentTime);
	        model.addAttribute("dbVersion", dbVersion);
	        model.addAttribute("message", "MariaDB 연결이 정상적으로 작동합니다!");
	        
	        System.out.println("=== MariaDB 연결 테스트 결과 ===");
	        System.out.println("테스트 값: " + testValue);
	        System.out.println("현재 시간: " + currentTime);
	        System.out.println("DB 버전: " + dbVersion);
	        System.out.println("==============================");
	        
	    } catch (Exception e) {
	        model.addAttribute("testResult", "실패");
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("message", "MariaDB 연결에 실패했습니다!");
	        
	        System.err.println("MariaDB 연결 테스트 실패: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return "ik/dbTest";
	}
	
	@GetMapping("/test/json")
	@ResponseBody
	public String testDbJson() {
	    try {
	        String testValue = testMapper.getTestValue();
	        String currentTime = testMapper.getCurrentTime();
	        String dbVersion = testMapper.getDatabaseVersion();
	        
	        return "{\"status\":\"success\", \"testValue\":\"" + testValue + 
	               "\", \"currentTime\":\"" + currentTime + 
	               "\", \"dbVersion\":\"" + dbVersion + "\"}";
	        
	    } catch (Exception e) {
	        return "{\"status\":\"error\", \"message\":\"" + e.getMessage() + "\"}";
	    }
	}
}
