package org.doit.flight.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	
	public Map<String, Integer> getLowPriceList(LocalDate departureDate, String departure, String arrival) {
	    Map<String, Map<String, Object>> rawMap = flightMapper.getWeekLowPrices(departureDate, departure, arrival);

	    Map<String, Integer> result = new LinkedHashMap<>();
	    for (Map.Entry<String, Map<String, Object>> entry : rawMap.entrySet()) {
	        String date = entry.getKey();
	        Object priceObj = entry.getValue().get("price");
	        Integer price = priceObj != null ? ((Number) priceObj).intValue() : null;
	        result.put(date, price);
	    }

	    return result;
	}

}
