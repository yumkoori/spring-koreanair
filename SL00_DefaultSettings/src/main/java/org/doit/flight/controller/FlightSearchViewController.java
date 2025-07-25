package org.doit.flight.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.doit.flight.command.FlightSearchCommand;
import org.doit.flight.dto.FlightSearchRequestDTO;
import org.doit.flight.dto.FlightSearchResponseDTO;
import org.doit.flight.dto.FlightSeatResponseDTO;
import org.doit.flight.service.FlightSearchService;
import org.doit.flight.service.FlightSeatService;
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
	
	@Autowired
	private FlightSeatService flightSeatService;
	
	@GetMapping("/search/flight")
	public String getSearchResultView(FlightSearchCommand command, Model model) {		

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
	    
	    System.out.println(requestDTO);
		
	    //서비스로 옮기기 
	    try {
	    	//항공편 리스트 가져오기 
			List<FlightSearchResponseDTO> flights = flightSearchService.searchFlight(requestDTO);
			
			model.addAttribute("flightList", flights);
			//항공편id에 해당하는 좌석 정보 가져오기 
			Map<String, List<FlightSeatResponseDTO>> seatsKeyFlightIdMap = new HashMap<>();
			
			for (FlightSearchResponseDTO flightSearchResponseDTO : flights) {
				String flightId = flightSearchResponseDTO.getFlightId();		//버퍼로 바꿀것 
				
				seatsKeyFlightIdMap.put(flightId, flightSeatService.getFlightSeatsByFlightIdAndPassengers(flightId, passengerCount));
			}
			
			model.addAttribute("flightSeat", seatsKeyFlightIdMap);
			
			//주간 최저가 리스트 가져오기
			Map<String, Integer> lowPriceList = flightSearchService.getLowPriceList(departureDate, command.getDeparture(), command.getArrival());
			
			model.addAttribute("weekLowPrices", lowPriceList);
		
			System.out.println(lowPriceList);
			
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
