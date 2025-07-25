package org.doit.flight.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class FlightServiceTest {

	@Autowired
	private FlightSearchService flightSearchService;
	
	
	@Test
	public void getWeekLowPricesTest() {
		//given
		LocalDate departureDate = LocalDate.of(2025, 6, 10);
		String departure = "ICN";
		String arrival = "PUS";
		//when
		Map<String, Integer> weekprice = flightSearchService.getLowPriceList(departureDate, departure, arrival);
		//then
		
		System.out.println(weekprice);
		
	}
}
