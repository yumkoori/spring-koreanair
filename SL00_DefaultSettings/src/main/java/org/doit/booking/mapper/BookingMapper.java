package org.doit.booking.mapper;

import org.apache.ibatis.annotations.Param;
import org.doit.booking.dto.BookingDTO;

public interface BookingMapper {
	
	public int insertPendingBooking(BookingDTO bookingDTO);
	
    public void updateNonUserPW(@Param("bookingId") String bookingId, @Param("bookingPW") String bookingPW);

}
