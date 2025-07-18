package org.doit.flight.dto;

import java.time.LocalDate;

import org.doit.util.SeatClass;
import org.doit.util.TripType;

public class FlightSearchRequestDTO {
    private String departure;      
    private String arrival;      
    private LocalDate departureDate;  
    private LocalDate returnDate;     
    private int passengers;    
    private SeatClass seatClass;     
    private TripType tripType;
    
	public FlightSearchRequestDTO(String departure, String arrival, LocalDate departureDate, LocalDate returnDate,
			int passengers, SeatClass seatClass, TripType tripType) {
		super();
		this.departure = departure;
		this.arrival = arrival;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.passengers = passengers;
		this.seatClass = seatClass;
		this.tripType = tripType;
	}

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}

	public LocalDate getDepartureDate() {
		return departureDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public int getPassengers() {
		return passengers;
	}

	public SeatClass getSeatClass() {
		return seatClass;
	}

	public TripType getTripType() {
		return tripType;
	}      
    
}
