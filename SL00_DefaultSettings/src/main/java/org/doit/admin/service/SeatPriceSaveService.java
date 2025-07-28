package org.doit.admin.service;

import java.util.List;

import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.mapper.SeatPriceSaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatPriceSaveService {

	@Autowired
	private SeatPriceSaveMapper seatPriceSaveMapper;

	public int seatPriceSave(List<SeatPriceSaveDTO> pirceList, String flightid) throws Exception {
		    System.out.println("SeatPriceSaveService 시작!!");
			return seatPriceSaveMapper.seatPriceSave(pirceList, flightid);
		}
	public int seatEachPriceSave(SeatPriceSaveDTO eachPrice, String flightid) throws Exception {
		System.out.println("seatEachPriceSave 시작 !! ");
		return seatPriceSaveMapper.seatEachPriceSave(eachPrice, flightid);
	}
	
}
