package org.doit.admin.service;

import java.util.List;
import java.util.Objects;

import org.doit.admin.dto.SeatPriceSaveDTO;
import org.doit.admin.mapper.SeatSaveDuplicationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatSaveDuplicationService {
	
	@Autowired
	SeatSaveDuplicationMapper seatSaveDuplicationMapper;
		
	
	public int seatSaveDuplication(List<SeatPriceSaveDTO> seatList, String flightid) {
		System.out.println("SeatSaveDuplicationService 중복 확인검사에 도착");
		return seatSaveDuplicationMapper.seatSaveDuplication(seatList, flightid);
	}
	
	public int seatSavePirceExistence(List<SeatPriceSaveDTO> seatList, String flightid) {
		System.out.println("좌석에 값이 존재하는지 검사 ");
		long distinctCount = seatList.stream()
			    .map(SeatPriceSaveDTO::getSeatclass)
			    .filter(Objects::nonNull) // null 제외 (원하면 제거)
			    .distinct()
			    .count();
	    System.out.println("distinctCount       " + distinctCount);
	    int check = seatSaveDuplicationMapper.seatSavePirceExistence(seatList, flightid);
	    System.out.println("seatList   "   + seatList);
	    System.out.println("check     " + check);
		if( check == distinctCount) {
			return 1;
		} else {
			return 0;
		}
	      		
		
	}
	
	
}
