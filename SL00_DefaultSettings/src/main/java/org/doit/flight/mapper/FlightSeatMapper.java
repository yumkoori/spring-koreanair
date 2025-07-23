package org.doit.flight.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.doit.flight.dto.FlightSeatResponseDTO;

public interface FlightSeatMapper {

	List<FlightSeatResponseDTO> getFlightSeatsGroupBySeatClass(
			@Param("flightId") String flightId, @Param("passengers") int passengers);
}
