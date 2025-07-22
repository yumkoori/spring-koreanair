package org.doit.flight.service;

import java.util.List;

import org.doit.flight.dto.FlightSeatResponseDTO;
import org.doit.flight.mapper.FlightSeatMapper;
import org.springframework.stereotype.Service;

@Service
public class FlightSeatService {

	FlightSeatMapper flightSeatMapper;
	
	public List<FlightSeatResponseDTO> getFlightSeatsByFlightIdAndPassengers(String flightId, int passengers){
		
		return flightSeatMapper.getFlightSeatsGroupBySeatClass(flightId, passengers);
		
	}
	
}
