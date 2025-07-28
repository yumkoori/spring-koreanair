package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.SeatPriceSaveDTO;

public interface SeatPriceSaveMapper {
	
	int seatPriceSave(@Param("list") List<SeatPriceSaveDTO> priceList, @Param("flightid") String flightid);
	
	int seatEachPriceSave(@Param("each") SeatPriceSaveDTO eachPrice, @Param("flightid") String flightid);
}
