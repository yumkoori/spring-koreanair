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
			@RequestParam("outboundFlightId") String outboundFlightId, 
			@RequestParam("returnFlightId") String returnFlightId, 
			Model model) {
		
		String bookingId = bookingService.savePendingBooking(outboundFlightId, returnFlightId);
		
		model.addAttribute("bookingId", bookingId);
		
		return "booking/booking";
	}
}
