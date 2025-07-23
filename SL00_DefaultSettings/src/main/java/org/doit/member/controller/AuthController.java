package org.doit.member.controller;

import org.doit.member.model.User;
import org.doit.member.service.AuthService;
import org.doit.member.service.UserService;
import org.doit.member.util.KakaoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model, HttpServletRequest request,
                           @RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout) {
        
        if (error != null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        }
        
        if (logout != null) {
            model.addAttribute("message", "로그아웃되었습니다.");
        }
        
        String savedUserId = authService.getSavedUserId(request);
        if (savedUserId != null) {
            model.addAttribute("savedUserId", savedUserId);
        }
        
        return "login/login";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerForm() {
        return "login/register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String userId,
                         @RequestParam String password,
                         @RequestParam String confirmPassword,
                         @RequestParam String koreanName,
                         @RequestParam String englishName,
                         @RequestParam String birthDate,
                         @RequestParam String gender,
                         @RequestParam String email,
                         @RequestParam String phone,
                         @RequestParam(required = false) String address,
                         RedirectAttributes redirectAttributes) {
        
        try {
            User user = authService.registerUser(userId, password, confirmPassword, 
                                               koreanName, englishName, birthDate, 
                                               gender, email, phone, address);
            
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/login";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }
    
    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String dashboard(Model model, HttpSession session, HttpServletRequest request) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("user", user);
        
        // 카카오 사용자인 경우 카카오 브라우저 로그아웃 URL 제공
        if (user.getLoginType() != null && 
            ("kakao".equals(user.getLoginType()) || "both".equals(user.getLoginType()))) {
            String kakaoLogoutUrl = KakaoConfig.getKakaoBrowserLogoutUrl(request);
            model.addAttribute("kakaoLogoutUrl", kakaoLogoutUrl);
        }
        
        return "login/dashboard";
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        
        // 카카오에서 리다이렉트로 온 경우 (로그아웃 후 돌아온 경우) 세션만 정리하고 메인 페이지로 이동
        String referer = request.getHeader("Referer");
        if (referer != null && referer.contains("kauth.kakao.com")) {
            // 세션 무효화 및 Spring Security 컨텍스트 정리
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
            
            if (session != null) {
                session.invalidate();
            }
            
            return "redirect:/?logout=kakao";
        }
        
        // 일반 로그아웃 또는 카카오 사용자의 직접 로그아웃 처리
        try {
            boolean isKakaoLogout = authService.logoutUser(session, response, request);
            if (isKakaoLogout) {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 세션 무효화 및 Spring Security 컨텍스트 정리
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        
        return "redirect:/login?logout=true";
    }
    
    @RequestMapping(value = "/checkUserId", method = RequestMethod.POST)
    @ResponseBody
    public String checkUserId(@RequestParam String userId) {
        try {
            boolean exists = userService.isUserIdExists(userId);
            return "{\"exists\":" + exists + "}";
        } catch (Exception e) {
            return "{\"error\":\"서버 오류가 발생했습니다.\"}";
        }
    }
    
    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmail(@RequestParam String email) {
        try {
            boolean exists = userService.isEmailExists(email);
            User existingUser = userService.findByEmail(email);
            
            // 카카오 연동 가능 조건: 기존에 kakao 타입으로만 가입된 이메일
            boolean isKakaoLinkable = exists && existingUser != null && 
                                    "kakao".equals(existingUser.getLoginType());
            
            return "{\"exists\":" + exists + ",\"isKakaoLinkable\":" + isKakaoLinkable + "}";
        } catch (Exception e) {
            return "{\"error\":\"서버 오류가 발생했습니다.\"}";
        }
    }
}