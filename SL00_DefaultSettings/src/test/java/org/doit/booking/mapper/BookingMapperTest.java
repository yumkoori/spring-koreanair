package org.doit.booking.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.doit.booking.dto.BookingDTO;
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
public class BookingMapperTest {

	@Autowired
	private BookingMapper bookingMapper;
	
	@Test
	public void insertPendingBookingTest() {
		//given
        String bookingId = UUID.randomUUID().toString();
        
		BookingDTO bookingDTO = BookingDTO.builder().bookingId(bookingId)
							.outboundFlightId("02ce9cbe-4da8-4449-b1b6-0d40da6ff6fb")
							.returnFlightId("acc6e700-52c5-4295-ba6b-9826029a6747")
							.userNo(null)
							.promotionId("PROMO10")
							.bookingPw("1234")
							.expireTime(LocalDateTime.now())
							.build();
		//when
		int row = bookingMapper.insertPendingBooking(bookingDTO);
			
		//then
		System.out.println(bookingId);
		System.out.println(row);	
		
	}
	
}
