package org.doit.flight.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightSearchResponseDTO {
    private String flightId;             
    private String airlineName;          
    private Timestamp departureTime; 
    private Timestamp arrivalTime;   
    private Long durationMinutes;         
    private Long availableSeatCount;
    
}
