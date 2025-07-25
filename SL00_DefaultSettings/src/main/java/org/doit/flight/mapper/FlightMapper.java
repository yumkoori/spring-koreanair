package org.doit.flight.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.doit.flight.dto.FlightSearchRequestDTO;
import org.doit.flight.dto.FlightSearchResponseDTO;

public interface FlightMapper {
	
	//사용자의 검색 결과로 조회된 항공편들 
	public List<FlightSearchResponseDTO> getSearchFlight(FlightSearchRequestDTO flightSearchDTO);
	
	//출발 날짜를 기준으로 주간 항공편 최저가 리스트 조회
	@MapKey("target_date")
	Map<String, Map<String, Object>> getWeekLowPrices(
	    @Param("departureDate") LocalDate departureDate,
	    @Param("departure") String departure,
	    @Param("arrival") String arrival
	);


}
