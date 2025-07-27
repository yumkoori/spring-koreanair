package org.doit.booking.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.doit.booking.dto.BookingDTO;
import org.doit.booking.mapper.BookingMapper;
import org.doit.member.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
	
	@Autowired
	private BookingMapper bookingMapper;
	
	public String savePendingBooking(String outboundFlightId, String returnFlightId, HttpSession session) {
		
		String bookingId = UUID.randomUUID().toString();	
		int expireMinutes = 10;										//만료 시간 

        User user = (User) session.getAttribute("user");
        Integer userNo = null;
        
        if (user != null) {
            userNo = user.getUserNo();

        } else {
            System.out.println("세션에 사용자 정보가 없습니다. (비회원)");
        }

		BookingDTO bookingDTO = BookingDTO.builder()
				.bookingId(bookingId)
				.outboundFlightId(outboundFlightId)
				.returnFlightId(returnFlightId)
				.userNo(userNo)								//수정 필요 
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
