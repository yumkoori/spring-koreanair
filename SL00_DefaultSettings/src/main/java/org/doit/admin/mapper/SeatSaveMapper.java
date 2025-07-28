package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.SeatPriceSaveDTO;

public interface SeatSaveMapper {
	
	int seatSave(@Param("list") List<SeatPriceSaveDTO> seatList, @Param("flightid") String flightid, @Param("totalCount") int totalCount);
	
	int priceExistence(@Param("list") List<SeatPriceSaveDTO> seatList, @Param("flightid") String flightid);
}
