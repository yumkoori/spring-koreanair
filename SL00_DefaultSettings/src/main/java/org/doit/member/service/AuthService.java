package org.doit.member.service;

import org.doit.member.dao.UserDAO;
import org.doit.member.model.User;
import org.doit.member.util.KakaoApiService;
import org.doit.member.util.KakaoConfig;
import org.doit.member.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private UserService userService;
    
    public User authenticateUser(String userId, String password) throws Exception {
        if (userId == null || password == null || userId.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return userService.authenticateUser(userId, password);
    }
    
    public void setupUserSession(HttpSession session, User user) throws Exception {
        session.setAttribute("user", user);
        session.setAttribute("isAuthenticated", true);
    }
    
    public void handleRememberMe(HttpServletResponse response, String userId, String remember) {
        if ("on".equals(remember)) {
            Cookie userIdCookie = new Cookie("savedUserId", userId.trim());
            userIdCookie.setMaxAge(30 * 24 * 60 * 60); // 30일
            userIdCookie.setPath("/");
            userIdCookie.setHttpOnly(true);
            response.addCookie(userIdCookie);
        } else {
            Cookie userIdCookie = new Cookie("savedUserId", "");
            userIdCookie.setMaxAge(0);
            userIdCookie.setPath("/");
            response.addCookie(userIdCookie);
        }
    }
    
    public String getSavedUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("savedUserId".equals(cookie.getName())) {
                    String savedUserId = cookie.getValue();
                    if (savedUserId != null && !savedUserId.trim().isEmpty()) {
                        return savedUserId;
                    }
                    break;
                }
            }
        }
        return null;
    }
    
    public boolean isUserIdExists(String userId) throws Exception {
        if (userId == null || userId.trim().isEmpty()) {
            return true;
        }
        return userService.isUserIdExists(userId);
    }
    
    public boolean isEmailExists(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return userService.isEmailExists(email);
    }
    
    public EmailCheckResult checkEmailForRegistration(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            return new EmailCheckResult(true, false);
        }
        
        User existingUser = userService.findByEmail(email);
        if (existingUser == null) {
            return new EmailCheckResult(false, false);
        }
        
        if ("kakao".equals(existingUser.getLoginType())) {
            return new EmailCheckResult(true, true);
        }
        
        return new EmailCheckResult(true, false);
    }
    
    public static class EmailCheckResult {
        private boolean exists;
        private boolean isKakaoLinkable;
        
        public EmailCheckResult(boolean exists, boolean isKakaoLinkable) {
            this.exists = exists;
            this.isKakaoLinkable = isKakaoLinkable;
        }
        
        public boolean isExists() {
            return exists;
        }
        
        public boolean isKakaoLinkable() {
            return isKakaoLinkable;
        }
    }
    
    public User registerUser(String userId, String password, String confirmPassword, 
                              String koreanName, String englishName, String birthDateStr, 
                              String gender, String email, String phone, String address) throws Exception {
        
        if (userId == null || password == null || koreanName == null || englishName == null ||
            birthDateStr == null || gender == null || email == null || phone == null ||
            userId.trim().isEmpty() || password.trim().isEmpty() || koreanName.trim().isEmpty() ||
            englishName.trim().isEmpty() || birthDateStr.trim().isEmpty() || gender.trim().isEmpty() ||
            email.trim().isEmpty() || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("모든 필수 항목을 입력해주세요.");
        }
        
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        User existingUser = userService.findByEmail(email);
        if (existingUser != null) {
            if ("kakao".equals(existingUser.getLoginType())) {
                existingUser.setUserId(userId);
                // 비밀번호 암호화 처리
                String hashedPassword = PasswordUtil.hashPassword(password);
                existingUser.setPassword(hashedPassword);
                existingUser.setKoreanName(koreanName);
                existingUser.setEnglishName(englishName);
                // 생년월일 처리
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date utilDate = sdf.parse(birthDateStr);
                    existingUser.setBirthDate(new java.sql.Date(utilDate.getTime()));
                } catch (Exception e) {
                    throw new IllegalArgumentException("생년월일 형식이 올바르지 않습니다.");
                }
                existingUser.setGender(gender);
                existingUser.setPhone(phone);
                existingUser.setAddress(address);
                existingUser.setLoginType("both");
                
                userDAO.updateUser(existingUser);
                return existingUser;
            } else {
                throw new IllegalArgumentException("해당 이메일로 계정이 이미 존재합니다.");
            }
        }
        
        User user = new User();
        user.setUserId(userId);
        user.setPassword(password);
        user.setKoreanName(koreanName);
        user.setEnglishName(englishName);
        user.setGender(gender);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(birthDateStr);
            user.setBirthDate(new java.sql.Date(utilDate.getTime()));
        } catch (Exception e) {
            throw new IllegalArgumentException("생년월일 형식이 올바르지 않습니다.");
        }
        
        return userService.registerUser(user);
    }
    
    public boolean logoutUser(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
        boolean isKakaoUser = false;
        
        if (session != null) {
            User user = (User) session.getAttribute("user");
            String kakaoAccessToken = (String) session.getAttribute("kakaoAccessToken");
            
            if (user != null && 
                ("kakao".equals(user.getLoginType()) || "both".equals(user.getLoginType()))) {
                isKakaoUser = true;
                
                if (kakaoAccessToken != null) {
                    try {
                        KakaoApiService.logoutKakao(kakaoAccessToken);
                    } catch (Exception e) {
                        System.out.println("카카오 로그아웃 실패: " + e.getMessage());
                    }
                }
            }
            
            session.invalidate();
        }
        
        if (isKakaoUser) {
            String kakaoLogoutUrl = KakaoConfig.getKakaoBrowserLogoutUrl(request);
            response.sendRedirect(kakaoLogoutUrl);
            return true;
        }
        
        return false;
    }
}