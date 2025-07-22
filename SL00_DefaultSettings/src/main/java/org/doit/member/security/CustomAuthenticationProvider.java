package org.doit.member.security;

import org.doit.member.model.User;
import org.doit.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Autowired
    private UserService userService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userId = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        try {
            User user = userService.authenticateUser(userId, password);
            
            if (user == null) {
                throw new BadCredentialsException("아이디 또는 비밀번호가 잘못되었습니다.");
            }
            
            List<GrantedAuthority> authorities = new ArrayList<>();
            
            if ("admin".equals(user.getUserId())) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            
            return new UsernamePasswordAuthenticationToken(user, password, authorities);
            
        } catch (Exception e) {
            throw new BadCredentialsException("인증 처리 중 오류가 발생했습니다.");
        }
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}