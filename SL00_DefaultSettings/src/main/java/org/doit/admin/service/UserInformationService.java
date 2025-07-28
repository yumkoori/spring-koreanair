package org.doit.admin.service;

import java.util.List;

import org.doit.admin.dto.SeatLoadDTO;
import org.doit.admin.dto.SeatPriceLoadDTO;
import org.doit.admin.dto.UserInfoDTO;
import org.doit.admin.mapper.SeatLoadMapper;
import org.doit.admin.mapper.UserInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {
	
	@Autowired
	UserInformationMapper userInformationMapper;
	
	public List<UserInfoDTO> userInfoList( String userName ){
		System.out.println("UserInformationService 시작");
		
		return userInformationMapper.userInfoList(userName);
	}
	
	
}
