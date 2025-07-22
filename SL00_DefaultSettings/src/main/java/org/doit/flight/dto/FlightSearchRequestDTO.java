package org.doit.flight.dto;

import java.time.LocalDate;

import org.doit.util.SeatClass;
import org.doit.util.TripType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightSearchRequestDTO {
    private String departure;      
    private String arrival;      
    private LocalDate departureDate;  
    private LocalDate returnDate;     
    private int passengers;    
    private SeatClass seatClass;     
    private TripType tripType;
     
}
