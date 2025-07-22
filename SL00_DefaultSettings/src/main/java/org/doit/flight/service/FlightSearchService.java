package org.doit.flight.service;

import java.util.List;

import org.doit.flight.dto.FlightSearchRequestDTO;
import org.doit.flight.dto.FlightSearchResponseDTO;
import org.doit.flight.mapper.FlightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightSearchService {

	@Autowired
	private FlightMapper flightMapper;
	
	public List<FlightSearchResponseDTO> searchFlight(FlightSearchRequestDTO requestDTO) {
		
		return flightMapper.getSearchFlight(requestDTO);
	}
}
