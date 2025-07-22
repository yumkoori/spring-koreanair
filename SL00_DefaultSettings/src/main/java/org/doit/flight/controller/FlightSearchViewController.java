package org.doit.flight.controller;

import java.time.LocalDate;
import java.util.List;

import org.doit.flight.command.FlightSearchCommand;
import org.doit.flight.dto.FlightSearchRequestDTO;
import org.doit.flight.dto.FlightSearchResponseDTO;
import org.doit.flight.service.FlightSearchService;
import org.doit.util.SeatClass;
import org.doit.util.TripType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class FlightSearchViewController {
	
	@Autowired
	private FlightSearchService flightSearchService;
	
	@GetMapping("/search/flight")
	public String getSearchResultView(FlightSearchCommand command, Model model) {
		
		System.out.println("FlightSearchViewController 호출됨!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		
	    System.out.println("[DEBUG] flightSearchService: " + flightSearchService);

	    // 문자열 → LocalDate
	    LocalDate departureDate = LocalDate.parse(command.getDepartureDate());
	    LocalDate returnDate = LocalDate.parse(command.getReturnDate());

	    int passengerCount = parsePassengerCount(command.getPassengers());

	    SeatClass seatClass = SeatClass.fromDisplayName(command.getSeatClass());
	    TripType tripType = TripType.fromDisplayName(command.getTripType());
	    
	    
	    // DTO 생성
	    FlightSearchRequestDTO requestDTO = FlightSearchRequestDTO.builder()
	            .departure(command.getDeparture())
	            .arrival(command.getArrival())
	            .departureDate(departureDate)
	            .returnDate(returnDate)
	            .passengers(passengerCount)
	            .seatClass(seatClass)
	            .tripType(tripType)
	            .build();
		
	    try {
			List<FlightSearchResponseDTO> flights = flightSearchService.searchFlight(requestDTO);
			
			model.addAttribute("flights", flights);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		return "search/search";
	}
	
	private int parsePassengerCount(String passengerStr) {
	    // "성인 1명" 또는 "성인1명 소아1명" 중 첫 숫자만 추출
	    java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d+)").matcher(passengerStr);
	    if (matcher.find()) {
	        return Integer.parseInt(matcher.group(1));
	    }
	    return 1; // 기본값
	}
	
}
