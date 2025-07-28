package org.doit.admin.service;

import java.util.List;

import org.doit.admin.dto.SeatLoadDTO;
import org.doit.admin.dto.SeatPriceLoadDTO;
import org.doit.admin.mapper.SeatLoadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatLoadService {
	
	@Autowired
	SeatLoadMapper seatLoadMapper;
	
	public List<SeatLoadDTO> seatLoad(String flightid) {
		System.out.println("SeatLoadService seatLoad 시작");
		
		return seatLoadMapper.seatLoad(flightid);
	}
	
	public List<SeatPriceLoadDTO> seatpriceLoad( String flightid ){
		System.out.println("SeatLoadService seatpriceLoad 시작");
		System.out.println("전달받은 flightid: " + flightid);
		
		List<SeatPriceLoadDTO> result = seatLoadMapper.seatPriceLoad(flightid);
		
		System.out.println("Mapper에서 받은 결과:");
		System.out.println("- 결과 리스트: " + result);
		System.out.println("- 결과 크기: " + (result != null ? result.size() : "null"));
		
		if (result != null && !result.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				SeatPriceLoadDTO dto = result.get(i);
				System.out.println("결과 " + i + ": " + dto);
			}
		} else {
			System.out.println("결과가 비어있거나 null입니다.");
		}
		
		return result;
	}
	
	
}
