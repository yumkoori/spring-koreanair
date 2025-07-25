package org.doit.booking.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.doit.booking.dto.BookingDTO;
import org.doit.booking.dto.PassengerDTO;
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
public class PassengerMapperTest {

	@Autowired
	private PassengerMapper passengerMapper;
	
	@Test
	public void insertPassengerTest() {
		//given
		String passengerId = UUID.randomUUID().toString();
		
		PassengerDTO passengerDto = PassengerDTO.builder()
				.passengerId(passengerId)							//DB 에서 자동 증가 되도록 변
				.userNo(null) // 세션에서 가져오거나 추후 설정
				.bookingId("cb3baf84-8f08-4c2c-8f53-a7aeee72ac31") // 기본 예약ID 또는 가는편 예약ID 설정
				.lastName("KIM")
				.firstName("YUMI")
				.birthDate(LocalDate.now().toString())
				.gender("F")
				.type("ADULT") // 기본값
				.build();
		
		//when
		passengerMapper.insertPassenger(passengerDto);
			
		//then
		System.out.println(passengerDto.getPassengerId());
		
	}
	
}
