package org.doit.booking.service;

import java.util.UUID;

import org.doit.booking.dto.PassengerDTO;
import org.doit.booking.mapper.PassengerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

	@Autowired
	private PassengerMapper passengerMapper;
	
	public void savePassengerInfo(PassengerDTO passengerDTO) {
		String passengerId = UUID.randomUUID().toString();				//DTO 자체가 생성될때 자동으로 세팅 되도록 수
		passengerDTO.setPassengerId(passengerId);				
		
		passengerMapper.insertPassenger(passengerDTO);	
	}
}
