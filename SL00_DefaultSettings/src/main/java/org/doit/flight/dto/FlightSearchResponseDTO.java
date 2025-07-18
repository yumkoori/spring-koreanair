package org.doit.flight.dto;

import java.sql.Timestamp;

public class FlightSearchResponseDTO {
    private String flightId;             
    private String airlineName;          
    private Timestamp departureTime; 
    private Timestamp arrivalTime;   
    private Long durationMinutes;         
    private Long availableSeatCount;
    
	public FlightSearchResponseDTO(String flightId, String airlineName, Timestamp departureTime,
			Timestamp arrivalTime, Long durationMinutes, Long availableSeatCount) {
		super();
		this.flightId = flightId;
		this.airlineName = airlineName;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.durationMinutes = durationMinutes;
		this.availableSeatCount = availableSeatCount;
	}

	public String getFlightId() {
		return flightId;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public Long getDurationMinutes() {
		return durationMinutes;
	}

	public Long getAvailableSeatCount() {
		return availableSeatCount;
	}

	@Override
	public String toString() {
		return "FlightSearchResponseDTO [flightId=" + flightId + ", airlineName=" + airlineName + ", departureTime="
				+ departureTime + ", arrivalTime=" + arrivalTime + ", durationMinutes=" + durationMinutes
				+ ", availableSeatCount=" + availableSeatCount + "]";
	}     
    
	
}
