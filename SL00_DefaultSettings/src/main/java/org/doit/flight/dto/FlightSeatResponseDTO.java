package org.doit.flight.dto;

import org.doit.util.SeatClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSeatResponseDTO {
    private SeatClass classId;           
    private String className;          
    private String detailClassName;    
    private int availableSeatCount;    
    private int price;
}
