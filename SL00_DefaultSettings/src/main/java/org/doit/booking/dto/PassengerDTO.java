package org.doit.booking.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerDTO {
	private String passengerId;
	private Integer userNo;
	private String bookingId;
	private String firstName;
	private String lastName;
	private String birthDate;
	private String gender;
	private String type;
}
