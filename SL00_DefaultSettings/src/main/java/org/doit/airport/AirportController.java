package org.doit.airport;

import java.util.List;

import org.doit.airport.domain.Airport;
import org.doit.airport.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AirportController {

	@Autowired
	private AirportService airportService;
	
	@GetMapping(value = "/airport" , produces = "application/json")
	public ResponseEntity<List<Airport>> autoComplete(@RequestParam("keyword") String keyword) {
		
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }
        
        List<Airport> airports = airportService.searchAirportsByKeyword(keyword);
      
        return ResponseEntity.ok(airports); 
	}
}
