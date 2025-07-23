package org.doit.flight.dto;

import java.time.LocalDate;

import org.doit.util.SeatClass;
import org.doit.util.TripType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FlightSearchRequestDTO {
    private String departure;      
    private String arrival;      
    private LocalDate departureDate;  
    private LocalDate returnDate;     
    private int passengers;    
    private SeatClass seatClass;     
    private TripType tripType;
     
}
