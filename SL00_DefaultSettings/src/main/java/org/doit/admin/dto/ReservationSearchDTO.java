package org.doit.admin.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReservationSearchDTO {
	
	private String bookingId;
	private String userName;
	private String email;
	private String phone;
	private String start;
	private String end;
	private String startDate;
	private String bookingDate;
	private	String status;
	private	String seatClass;
	private	int passenger;
	private	int totalPrice;
	private	LocalTime expireTime;
	private	String endDate;
	private	String flightNO;
	private	String birthDate;
}
