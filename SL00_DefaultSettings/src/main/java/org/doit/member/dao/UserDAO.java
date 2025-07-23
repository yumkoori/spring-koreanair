package org.doit.member.dao;

import org.doit.member.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDAO {
    
    User findByUserId(String userId);
    
    User findByKakaoId(String kakaoId);
    
    User findByEmail(String email);
    
    User findByUserNo(int userNo);
    
    boolean existsByUserId(String userId);
    
    boolean existsByEmail(String email);
    
    boolean existsByKakaoId(String kakaoId);
    
    int insertUser(User user);
    
    int updateUser(User user);
    
    int updatePassword(@Param("userNo") int userNo, @Param("password") String password);
    
    int linkKakaoAccount(@Param("userNo") int userNo, @Param("kakaoId") String kakaoId);
    
    int updateProfileImage(@Param("userNo") int userNo, @Param("profileImage") String profileImage);
    
    int deleteUser(int userNo);
    
    List<User> findAllUsers();
    
    List<User> searchUsers(Map<String, Object> params);
    
    int countUsers(Map<String, Object> params);
}