package org.doit.booking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
	private String bookingId;
	private String outboundFlightId;
	private String returnFlightId;
	private Integer userNo;
	private String promotionId;
	private String bookingPw;
	private LocalDateTime expireTime;
}
