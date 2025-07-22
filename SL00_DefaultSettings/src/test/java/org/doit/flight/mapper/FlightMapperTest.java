package org.doit.flight.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.doit.flight.dto.FlightSearchRequestDTO;
import org.doit.flight.dto.FlightSearchResponseDTO;
import org.doit.util.SeatClass;
import org.doit.util.TripType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class FlightMapperTest {

	@Autowired
	private FlightMapper flightMapper;
	
	@Test
	public void getSearchFlightTest() {
		//given
		FlightSearchRequestDTO requestDTO = new FlightSearchRequestDTO(
				"SIN", 
				"PUS", 
				LocalDate.now(), 
				LocalDate.now(), 
				1, 
				SeatClass.ECONOMY, 
				TripType.ONEWAY);
		
		//when
		List<FlightSearchResponseDTO> flights = flightMapper.getSearchFlight(requestDTO);
		//then
		
		for (FlightSearchResponseDTO flightSearchResponseDTO : flights) {
			System.out.println(flightSearchResponseDTO);
		}
	}
}
