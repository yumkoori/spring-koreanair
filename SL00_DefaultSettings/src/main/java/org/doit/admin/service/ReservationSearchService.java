package org.doit.admin.service;

import java.util.List;

import org.doit.admin.dto.ReservationSearchDTO;
import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.mapper.ReservationSearchMapper;
import org.doit.admin.mapper.SeatPriceSaveMapper;
import org.doit.admin.mapper.SeatSaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationSearchService {
	
	@Autowired
	ReservationSearchMapper reservationSearchMapper;
	

	public List<ReservationSearchDTO> reservationSearch (String searchType, String searchKeyword, String reservationStatus) throws Exception {
				
		List<ReservationSearchDTO> result = reservationSearchMapper.reservationSearch(searchType, searchKeyword, reservationStatus);
		
		return result;
	}
	
	
	
}
