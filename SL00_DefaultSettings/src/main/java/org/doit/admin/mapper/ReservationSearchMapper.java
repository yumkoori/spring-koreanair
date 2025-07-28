package org.doit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.admin.dto.ReservationSearchDTO;
import org.doit.admin.dto.SeatLoadDTO;
import org.doit.admin.dto.SeatPriceLoadDTO;
import org.doit.admin.dto.SeatPriceSaveDTO;

public interface ReservationSearchMapper {
	
	public List<ReservationSearchDTO> reservationSearch (
		@Param("searchType") String searchType, 
		@Param("searchKeyword") String searchKeyword, 
		@Param("reservationStatus") String reservationStatus
	);
}
