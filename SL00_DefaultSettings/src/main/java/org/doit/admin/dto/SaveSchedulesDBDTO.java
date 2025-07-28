package org.doit.admin.dto;

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
public class SaveSchedulesDBDTO {
	
	private String id;
	private String flightNo;
	private String origin;
	private String destination;
	private String departureTime;
	private String arrivalTime;
	private String status;
	private String airline;
}
