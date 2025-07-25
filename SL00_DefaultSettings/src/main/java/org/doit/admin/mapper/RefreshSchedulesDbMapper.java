package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.SaveSchedulesDBDTO;

public interface RefreshSchedulesDbMapper {
	
	int refreshSchedules(@Param("list") List<SaveSchedulesDBDTO> list);
}
