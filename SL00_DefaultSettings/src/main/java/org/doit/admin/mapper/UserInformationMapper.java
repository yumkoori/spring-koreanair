package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.UserInfoDTO;

public interface UserInformationMapper {
	
	List<UserInfoDTO> userInfoList ( @Param("userName") String userName);
}
