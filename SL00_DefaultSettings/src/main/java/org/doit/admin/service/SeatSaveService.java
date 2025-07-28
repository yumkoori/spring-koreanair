package org.doit.admin.service;

import java.util.List;

import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.mapper.SeatPriceSaveMapper;
import org.doit.admin.mapper.SeatSaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatSaveService {
	
	@Autowired
	SeatSaveMapper seatSaveMapper;
	
	public int SeatSave(List<SeatPriceSaveDTO> seatList, String flightid, int totalCount) {
		System.out.println("SeatSaveService 시작");
		return seatSaveMapper.seatSave(seatList, flightid, totalCount);
		
	}
	
	public int priceExistence(List<SeatPriceSaveDTO> seatList, String flightid ) {
		System.out.println("priceExistence 시작");
		
		return seatSaveMapper.priceExistence(seatList, flightid);
	}
	
	
}
