package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.SeatPriceSaveDTO;

public interface SeatPriceDuplicationMapper {
	
	int seatPriceDuplication(@Param("list") List<SeatPriceSaveDTO> listPrice, @Param("flightid") String flightid);
	
	int seatEachPriceDuplication(@Param("each") SeatPriceSaveDTO eachPrice, @Param("flightid") String flightid);
}
