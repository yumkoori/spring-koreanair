package org.doit.admin.service;

import java.util.List;

import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.mapper.SeatPriceDuplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatPriceDuplicationService {
	
	@Autowired
	SeatPriceDuplicationMapper seatPriceDuplicationMapper;
	
	public int duplicationSeatPrice(List<SeatPriceSaveDTO> listPrice, String flightid) {
		System.out.println("SeatPriceDuplicationService 시작 ! ");
		
		return seatPriceDuplicationMapper.seatPriceDuplication(listPrice, flightid);
	}
	
	public int duplicationSeatEachPrice(SeatPriceSaveDTO eachprice, String flightid) {
		System.out.println("duplicationSeatEachPrice Service 시작");
		
		return seatPriceDuplicationMapper.seatEachPriceDuplication(eachprice, flightid);
	}
}
