package org.doit.member.service;

import org.doit.member.dao.UserDAO;
import org.doit.member.model.User;
import org.doit.member.util.KakaoApiService;
import org.doit.member.util.KakaoApiService.KakaoUserInfo;
import org.doit.member.util.KakaoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@Transactional
public class KakaoService {
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private UserService userService;
    
    public String getKakaoLoginUrl(HttpServletRequest request) {
        return KakaoConfig.getKakaoLoginUrl(request);
    }
    
    public String processKakaoCallback(String code, String error, HttpServletRequest request, 
                                     HttpServletResponse response, HttpSession session) throws Exception {
        
        if (error != null) {
            throw new IllegalArgumentException("카카오 로그인이 취소되었습니다.");
        }
        
        if (code == null) {
            throw new IllegalArgumentException("카카오 로그인 중 오류가 발생했습니다.");
        }
        
        String redirectUri = KakaoConfig.getRedirectUri(request);
        String accessToken = KakaoApiService.getAccessToken(code, redirectUri);
        
        KakaoUserInfo kakaoUserInfo = KakaoApiService.getUserInfo(accessToken);
        
        User existingUser = userService.findByKakaoId(kakaoUserInfo.getId());
        
        if (existingUser != null) {
            // 사용자 인증 성공 - Spring Security 컨텍스트에 설정
            Authentication auth = new UsernamePasswordAuthenticationToken(
                existingUser,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            session.setAttribute("user", existingUser);
            session.setAttribute("kakaoAccessToken", accessToken);
            session.setAttribute("isAuthenticated", true);
            return "redirect:/dashboard";
        }
        
        if (kakaoUserInfo.getEmail() != null) {
            User emailUser = userService.findByEmail(kakaoUserInfo.getEmail());
            if (emailUser != null && "normal".equals(emailUser.getLoginType())) {
                // 기존 일반 계정에 카카오 정보 추가
                emailUser.setKakaoId(kakaoUserInfo.getId());
                emailUser.setLoginType("both");
                if (kakaoUserInfo.getProfileImageUrl() != null) {
                    emailUser.setProfileImage(kakaoUserInfo.getProfileImageUrl());
                }
                
                // 계정 업데이트
                userDAO.updateUser(emailUser);
                
                session.setAttribute("linkSuccessMessage", "기존 일반 계정과 카카오 계정이 연동되었습니다. 로그인해주세요.");
                return "redirect:/login";
            }
        }
        
        session.setAttribute("kakaoUserInfo", kakaoUserInfo);
        session.setAttribute("tempKakaoAccessToken", accessToken);
        
        return "login/kakao_signup";
    }
    
    public boolean completeKakaoSignup(HttpSession session, String koreanName, String englishName, 
                                     String birthDateStr, String gender, String phone, String address) throws Exception {
        
        KakaoUserInfo kakaoUserInfo = (KakaoUserInfo) session.getAttribute("kakaoUserInfo");
        
        if (kakaoUserInfo == null) {
            throw new IllegalArgumentException("세션이 만료되었습니다. 다시 로그인해주세요.");
        }
        
        if (koreanName == null || englishName == null || birthDateStr == null || 
            gender == null || phone == null ||
            koreanName.trim().isEmpty() || englishName.trim().isEmpty() || 
            birthDateStr.trim().isEmpty() || gender.trim().isEmpty() || 
            phone.trim().isEmpty()) {
            throw new IllegalArgumentException("모든 필수 항목을 입력해주세요.");
        }
        
        // 한 번에 모든 정보로 사용자 등록
        User user = userService.registerKakaoUser(
            kakaoUserInfo.getId(),
            kakaoUserInfo.getEmail(),
            koreanName,
            englishName,
            birthDateStr,
            gender,
            phone,
            address,
            kakaoUserInfo.getProfileImageUrl()
        );
        
        session.removeAttribute("kakaoUserInfo");
        session.removeAttribute("tempKakaoAccessToken");
        
        session.setAttribute("signupSuccessMessage", "카카오 회원가입이 완료되었습니다. 카카오 로그인을 이용해주세요.");
        
        return true;
    }
    
    /**
     * 카카오 로그아웃 처리
     */
    public boolean processKakaoLogout(HttpSession session) {
        try {
            // 세션에서 카카오 액세스 토큰 가져오기
            String kakaoAccessToken = (String) session.getAttribute("kakaoAccessToken");
            
            if (kakaoAccessToken != null) {
                // 카카오 API를 통해 로그아웃 호출
                boolean logoutSuccess = KakaoApiService.logoutKakao(kakaoAccessToken);
                
                if (!logoutSuccess) {
                    System.err.println("카카오 API 로그아웃 실패");
                }
            }
            
            // Spring Security 컨텍스트 초기화
            SecurityContextHolder.clearContext();
            
            // 세션에서 카카오 관련 정보 제거
            session.removeAttribute("kakaoAccessToken");
            session.removeAttribute("user");
            session.removeAttribute("isAuthenticated");
            
            // 세션 무효화
            session.invalidate();
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}