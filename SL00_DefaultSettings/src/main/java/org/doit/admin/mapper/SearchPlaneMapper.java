package org.doit.admin.mapper;

import org.apache.ibatis.annotations.Param;

public interface SearchPlaneMapper {
	
	int searchPlane(@Param("flightid") String flightid);
}
