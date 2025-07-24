package org.doit.booking.mapper;

import org.doit.booking.dto.BookingDTO;

public interface BookingMapper {
	
	public int insertPendingBooking(BookingDTO bookingDTO);
}
