package org.doit.booking.controller;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.doit.booking.service.BookingService;
import org.doit.flight.service.FlightSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private FlightSeatService flightSeatService;
	
	@GetMapping("/booking")
	public String getViewBookingPage(
			@RequestParam(value = "outboundFlightId", required = false) String outboundFlightId, 
			@RequestParam(value = "returnFlightId", required = false) String returnFlightId,
			@RequestParam(value = "flightId", required = false) String flightId,
			@RequestParam(value = "tripType", defaultValue = "oneway") String tripType,
			@RequestParam(value = "passengers", defaultValue = "성인+1명") String passengers,
			@RequestParam(value = "seatClass" , required = false) String seatClass,
			@RequestParam(value = "outboundSeatClass", required = false) String outboundSeatClass,
			@RequestParam(value = "returnSeatClass", required = false) String returnSeatClass,
			Model model) {
		
		String text = passengers;
		try {
			text = java.net.URLDecoder.decode(text, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Pattern pattern = java.util.regex.Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(text);

		int totalPassengers = 0;
		
		while (matcher.find()) {
		    totalPassengers += Integer.parseInt(matcher.group());
		}
		
		
		
		// 편도인 경우 flightId를 outboundFlightId로 사용
		if ("oneway".equals(tripType) && flightId != null && outboundFlightId == null) {
			outboundFlightId = flightId;
			System.out.println("편도 처리: outboundFlightId를 " + outboundFlightId + "로 설정");
			flightSeatService.updateSeatStatusToPending(flightId, seatClass, totalPassengers);

		} else {
			flightSeatService.updateSeatStatusToPending(outboundFlightId, outboundSeatClass, totalPassengers);
			flightSeatService.updateSeatStatusToPending(returnFlightId, returnSeatClass, totalPassengers);
		}
		
		String bookingId = bookingService.savePendingBooking(outboundFlightId, returnFlightId);
		

		model.addAttribute("bookingId", bookingId);
		model.addAttribute("outBookingId", outboundFlightId);
		model.addAttribute("returnBookingId", returnFlightId);
		
		return "booking/booking";
	}
}
