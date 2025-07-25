package org.doit.flight.mapper;


import java.util.List;


import org.doit.flight.dto.FlightSeatResponseDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class FlightSeatMapperTest {

	@Autowired
	private FlightSeatMapper flightSeatMapper;
	
	@Test
	public void getFlightSeatsGroupBySeatClassTest() {
		//given
		String flightId = "02ce9cbe-4da8-4449-b1b6-0d40da6ff6fb";
		int passengers = 1;
		
		//when
		
		List<FlightSeatResponseDTO> seats = flightSeatMapper.getFlightSeatsGroupBySeatClass(flightId, passengers);
		
		//then
		
		System.out.println(seats);
		
	}
}
