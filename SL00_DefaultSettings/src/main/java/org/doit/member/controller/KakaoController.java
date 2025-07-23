package org.doit.member.controller;

import org.doit.member.service.KakaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/kakao")
public class KakaoController {
    
    @Autowired
    private KakaoService kakaoService;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String kakaoLogin(HttpServletRequest request, HttpServletResponse response) {
        String kakaoLoginUrl = kakaoService.getKakaoLoginUrl(request);
        return "redirect:" + kakaoLoginUrl;
    }
    
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String kakaoCallback(@RequestParam(required = false) String code,
                              @RequestParam(required = false) String error,
                              HttpServletRequest request,
                              HttpServletResponse response,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        try {
            String result = kakaoService.processKakaoCallback(code, error, request, response, session);
            
            if (result.startsWith("redirect:")) {
                return result;
            } else {
                return result;
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String kakaoSignupForm() {
        return "login/kakao_signup";
    }
    
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String completeKakaoSignup(@RequestParam String koreanName,
                                    @RequestParam String englishName,
                                    @RequestParam String birthDate,
                                    @RequestParam String gender,
                                    @RequestParam String phone,
                                    @RequestParam(required = false) String address,
                                    HttpSession session,
                                    RedirectAttributes redirectAttributes) {
        
        try {
            boolean success = kakaoService.completeKakaoSignup(session, koreanName, englishName, 
                                                             birthDate, gender, phone, address);
            
            if (success) {
                redirectAttributes.addFlashAttribute("message", "카카오 회원가입이 완료되었습니다. 카카오 로그인을 이용해주세요.");
                return "redirect:/login";
            } else {
                redirectAttributes.addFlashAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
                return "redirect:/kakao/signup";
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/kakao/signup";
        }
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String kakaoLogout(HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            boolean success = kakaoService.processKakaoLogout(session);
            
            if (success) {
                redirectAttributes.addFlashAttribute("message", "카카오 로그아웃이 완료되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("error", "로그아웃 중 문제가 발생했습니다.");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "로그아웃 중 오류가 발생했습니다.");
        }
        
        return "redirect:/";
    }
}