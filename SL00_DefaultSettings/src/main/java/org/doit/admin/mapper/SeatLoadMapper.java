package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.SeatLoadDTO;
import org.doit.admin.dto.SeatPriceLoadDTO;
import org.doit.admin.dto.SeatPriceSaveDTO;

public interface SeatLoadMapper {
	
	List<SeatLoadDTO> seatLoad(@Param("flightid") String flightid);
	
	List<SeatPriceLoadDTO> seatPriceLoad(@Param("flightid") String flightid);
}
