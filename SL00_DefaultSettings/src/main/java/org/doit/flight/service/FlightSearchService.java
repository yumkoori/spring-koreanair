package org.doit.flight.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

		List<FlightSearchResponseDTO> flights = flightMapper.getSearchFlight(requestDTO);
	
		//시간 포맷팅
		for (FlightSearchResponseDTO flightSearchResponseDTO : flights) {
			Timestamp departure = flightSearchResponseDTO.getDepartureTime();
			String departureFormatted = new SimpleDateFormat("HH:mm").format(departure);

			Timestamp arrival = flightSearchResponseDTO.getArrivalTime();
			String arrivalFormatted = new SimpleDateFormat("HH:mm").format(arrival);
			
			flightSearchResponseDTO.setDepartureTimeFormatted(departureFormatted);
			flightSearchResponseDTO.setArrivalTimeFormatted(arrivalFormatted);
		}
		
		//소요시간 계산
		for (FlightSearchResponseDTO flight : flights) {
		    Timestamp departure = flight.getDepartureTime();
		    Timestamp arrival = flight.getArrivalTime();
		    
		    if (departure != null && arrival != null) {
		        long durationMillis = departure.getTime() - arrival.getTime();
		        long durationMinutes = Math.abs(durationMillis / (1000 * 60));
		        long hours = durationMinutes / 60;
		        long minutes = durationMinutes % 60;

		        String formatted = String.format("%d시간 %d분", hours, minutes);
		        flight.setDurationFormatted(formatted);
		    }
		}
		
		return flights;
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
