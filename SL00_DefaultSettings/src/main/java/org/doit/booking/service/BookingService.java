package org.doit.booking.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.doit.booking.dto.BookingDTO;
import org.doit.booking.mapper.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	
	@Autowired
	private BookingMapper bookingMapper;
	
	public String savePendingBooking(String outboundFlightId, String returnFlightId) {
		
		String bookingId = UUID.randomUUID().toString();	
		int expireMinutes = 10;										//만료 시간 
		
		BookingDTO bookingDTO = BookingDTO.builder()
				.bookingId(bookingId)
				.outboundFlightId(outboundFlightId)
				.returnFlightId(returnFlightId)
				.userNo(null)								//수정 필요 
				.promotionId("PROMO10")						//수정 필요 				
				.expireTime(LocalDateTime.now().plusMinutes(expireMinutes))			
				.build();
		
		bookingMapper.insertPendingBooking(bookingDTO);
		
		return bookingId;
	}
	
	public void updateNonUserPW(String bookingId, String bookingPW) {
	    bookingMapper.updateNonUserPW(bookingId, bookingPW);
	}
}
