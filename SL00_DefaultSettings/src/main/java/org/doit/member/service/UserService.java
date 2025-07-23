package org.doit.member.service;

import org.doit.member.dao.UserDAO;
import org.doit.member.model.User;
import org.doit.member.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserDAO userDAO;
    
    public User findByUserId(String userId) {
        return userDAO.findByUserId(userId);
    }
    
    public User findByKakaoId(String kakaoId) {
        return userDAO.findByKakaoId(kakaoId);
    }
    
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }
    
    public User findByUserNo(int userNo) {
        return userDAO.findByUserNo(userNo);
    }
    
    public boolean isUserIdExists(String userId) {
        return userDAO.existsByUserId(userId);
    }
    
    public boolean isEmailExists(String email) {
        return userDAO.existsByEmail(email);
    }
    
    public boolean isKakaoIdExists(String kakaoId) {
        return userDAO.existsByKakaoId(kakaoId);
    }
    
    public User registerUser(User user) throws Exception {
        if (isUserIdExists(user.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        
        if (isEmailExists(user.getEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }
        
        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
        }
        
        if (user.getGrade() == null) {
            user.setGrade("skypass");
        }
        
        if (user.getLoginType() == null) {
            user.setLoginType("normal");
        }
        
        userDAO.insertUser(user);
        return user;
    }
    
    public User authenticateUser(String userId, String password) {
        User user = userDAO.findByUserId(userId);
        if (user != null && PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    
    public boolean updateProfile(User currentUser, String koreanName, String englishName, 
                               String birthDateStr, String gender, String email, 
                               String phone, String address) throws Exception {
        
        if ("kakao".equals(currentUser.getLoginType())) {
            throw new IllegalArgumentException("카카오 계정은 프로필 정보를 수정할 수 없습니다.");
        }
        
        if (koreanName == null || koreanName.trim().isEmpty() ||
            englishName == null || englishName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("필수 항목을 모두 입력해주세요.");
        }
        
        if (!email.equals(currentUser.getEmail()) && isEmailExists(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        
        User updateUser = new User();
        updateUser.setUserNo(currentUser.getUserNo());
        updateUser.setKoreanName(koreanName.trim());
        updateUser.setEnglishName(englishName.trim());
        updateUser.setGender(gender);
        updateUser.setEmail(email.trim());
        updateUser.setPhone(phone.trim());
        updateUser.setAddress(address != null ? address.trim() : "");
        updateUser.setGrade(currentUser.getGrade());
        updateUser.setStatus(currentUser.getStatus());
        // 기존 카카오 정보 유지
        updateUser.setKakaoId(currentUser.getKakaoId());
        updateUser.setProfileImage(currentUser.getProfileImage());
        updateUser.setLoginType(currentUser.getLoginType());
        
        if (birthDateStr != null && !birthDateStr.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = sdf.parse(birthDateStr);
                updateUser.setBirthDate(new Date(utilDate.getTime()));
            } catch (ParseException e) {
                throw new IllegalArgumentException("생년월일 형식이 올바르지 않습니다.");
            }
        }
        
        return userDAO.updateUser(updateUser) > 0;
    }
    
    public boolean updatePassword(User currentUser, String currentPassword, 
                                String newPassword, String confirmPassword) throws Exception {
        
        if ("kakao".equals(currentUser.getLoginType())) {
            throw new IllegalArgumentException("카카오 계정은 비밀번호를 변경할 수 없습니다.");
        }
        
        if (currentPassword == null || currentPassword.trim().isEmpty() ||
            newPassword == null || newPassword.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("모든 비밀번호 필드를 입력해주세요.");
        }
        
        if (!PasswordUtil.verifyPassword(currentPassword, currentUser.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }
        
        if (newPassword.length() < 4) {
            throw new IllegalArgumentException("비밀번호는 4자 이상이어야 합니다.");
        }
        
        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        return userDAO.updatePassword(currentUser.getUserNo(), hashedPassword) > 0;
    }
    
    public boolean linkKakaoAccount(int userNo, String kakaoId) {
        return userDAO.linkKakaoAccount(userNo, kakaoId) > 0;
    }
    
    public boolean updateProfileImage(int userNo, String profileImage) {
        return userDAO.updateProfileImage(userNo, profileImage) > 0;
    }
    
    public boolean deleteUser(int userNo) {
        return userDAO.deleteUser(userNo) > 0;
    }
    
    public List<User> getAllUsers() {
        return userDAO.findAllUsers();
    }
    
    public List<User> searchUsers(String keyword, String loginType, int page, int size) {
        int offset = (page - 1) * size;
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("loginType", loginType);
        params.put("offset", offset);
        params.put("limit", size);
        return userDAO.searchUsers(params);
    }
    
    public int countUsers(String keyword, String loginType) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("loginType", loginType);
        return userDAO.countUsers(params);
    }
    
    public User registerKakaoUser(String kakaoId, String email, String koreanName, String englishName, 
                                  String birthDateStr, String gender, String phone, String address, String profileImage) throws Exception {
        if (isKakaoIdExists(kakaoId)) {
            throw new IllegalArgumentException("이미 연동된 카카오 계정입니다.");
        }
        
        User user = new User();
        user.setKakaoId(kakaoId);
        user.setEmail(email);
        user.setKoreanName(koreanName);
        user.setEnglishName(englishName);
        user.setGender(gender);
        user.setPhone(phone);
        user.setAddress(address != null ? address : "");
        user.setGrade("skypass");
        user.setLoginType("kakao");
        user.setProfileImage(profileImage);
        
        // 생년월일 설정
        try {
            user.setBirthDate(Date.valueOf(birthDateStr));
        } catch (Exception e) {
            throw new IllegalArgumentException("생년월일 형식이 올바르지 않습니다.");
        }
        
        userDAO.insertUser(user);
        return user;
    }
}