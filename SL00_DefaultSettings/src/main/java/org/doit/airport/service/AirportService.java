package org.doit.airport.service;

import java.util.List;

import org.doit.airport.domain.Airport;
import org.doit.airport.mapper.AirportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirportService {

	@Autowired
    private AirportMapper airportMapper;

	public List<Airport> searchAirportsByKeyword(String keyword) {
        return airportMapper.findAirportsByKeyword(keyword);
    }
	
}
