package org.doit.member.interceptor;

import org.doit.member.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        
        // Spring Security와 함께 사용되는 추가 인증 체크
        
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(contextPath + "/login");
            return false;
        }
        
        User user = (User) session.getAttribute("user");
        Boolean isAuthenticated = (Boolean) session.getAttribute("isAuthenticated");
        
        if (user == null || isAuthenticated == null || !isAuthenticated) {
            response.sendRedirect(contextPath + "/login");
            return false;
        }
        
        if (uri.contains("/admin/") && !"admin".equals(user.getUserId())) {
            response.sendRedirect(contextPath + "/dashboard");
            return false;
        }
        
        return true;
    }
}