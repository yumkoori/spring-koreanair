package org.doit.booking.controller;

import org.doit.booking.service.BookingService;
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
	
	@GetMapping("/booking")
	public String getViewBookingPage(
			@RequestParam(value = "outboundFlightId", required = false) String outboundFlightId, 
			@RequestParam(value = "returnFlightId", required = false) String returnFlightId,
			@RequestParam(value = "flightId", required = false) String flightId,
			@RequestParam(value = "tripType", defaultValue = "oneway") String tripType,
			Model model) {
		
		System.out.println("=== BookingController 파라미터 ===");
		System.out.println("outboundFlightId: " + outboundFlightId);
		System.out.println("returnFlightId: " + returnFlightId);
		System.out.println("flightId: " + flightId);
		System.out.println("tripType: " + tripType);
		
		// 편도인 경우 flightId를 outboundFlightId로 사용
		if ("oneway".equals(tripType) && flightId != null && outboundFlightId == null) {
			outboundFlightId = flightId;
			System.out.println("편도 처리: outboundFlightId를 " + outboundFlightId + "로 설정");
		}
		
		String bookingId = bookingService.savePendingBooking(outboundFlightId, returnFlightId);
		
		model.addAttribute("bookingId", bookingId);
		model.addAttribute("outBookingId", outboundFlightId);
		model.addAttribute("returnBookingId", returnFlightId);
		
		return "booking/booking";
	}
}
