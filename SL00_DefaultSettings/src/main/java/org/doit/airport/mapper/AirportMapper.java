package org.doit.airport.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.doit.airport.domain.Airport;

public interface AirportMapper {
	public List<Airport> findAirportsByKeyword(String keyword);
	
}
