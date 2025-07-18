package org.doit.flight.mapper;

import java.util.List;

import org.doit.flight.dto.FlightSearchRequestDTO;
import org.doit.flight.dto.FlightSearchResponseDTO;

public interface FlightMapper {
	
	public List<FlightSearchResponseDTO> getSearchFlight(FlightSearchRequestDTO flightSearchDTO);
	
}
