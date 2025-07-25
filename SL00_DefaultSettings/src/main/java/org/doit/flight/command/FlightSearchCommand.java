package org.doit.flight.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchCommand {
    private String departure;       // 출발지
    private String arrival;         // 도착지
    private String departureDate;   // 가는 날짜 (yyyy-MM-dd)
    private String returnDate;      // 오는 날짜 (yyyy-MM-dd)
    private String passengers;      // "성인 1명", "성인 1명, 소아 1명" 등의 문자열
    private String seatClass;       // "일반석", "비즈니스", 등
    private String tripType;  
}
