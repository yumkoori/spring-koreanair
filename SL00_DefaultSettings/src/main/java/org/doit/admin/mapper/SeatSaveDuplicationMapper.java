package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.SeatPriceSaveDTO;

public interface SeatSaveDuplicationMapper {
	
	int seatSaveDuplication(@Param("list") List<SeatPriceSaveDTO> seatList, @Param("flightid") String flightid);
	
	int seatSavePirceExistence(@Param("list") List<SeatPriceSaveDTO> seatList, @Param("flightid") String flightid);
}
