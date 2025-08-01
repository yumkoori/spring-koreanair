package org.doit.flight.service;

import java.util.List;

import org.doit.flight.dto.FlightSeatResponseDTO;
import org.doit.flight.mapper.FlightSeatMapper;
import org.doit.util.SeatClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightSeatService {

	@Autowired
	FlightSeatMapper flightSeatMapper;
	
	public List<FlightSeatResponseDTO> getFlightSeatsByFlightIdAndPassengers(String flightId, int passengers){
		
		return flightSeatMapper.getFlightSeatsGroupBySeatClass(flightId, passengers);
		
	}
	
	public void updateSeatStatusToPending(String flightId, String seatClass, int passengers) {
		flightSeatMapper.updateSeatToPending(flightId, seatClass, passengers);
	}
	
	public void releaseExpiredPendingSeats() {
		flightSeatMapper.releaseExpiredPendingSeats();
	}
	
}
