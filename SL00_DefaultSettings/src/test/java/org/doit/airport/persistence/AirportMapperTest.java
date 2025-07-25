package org.doit.airport.persistence;

import static org.junit.Assert.*;

import java.util.List;

import org.doit.airport.domain.Airport;
import org.doit.airport.mapper.AirportMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class AirportMapperTest {

	@Autowired
	private AirportMapper airportMapper;
	
	@Test
	public void findAirportsByKeyword() {
		try {
			List<Airport> result = airportMapper.findAirportsByKeyword("CJU");
			System.out.println(result.get(0));
			
		    assertNotNull("결과 리스트가 null입니다.", result);
		    System.out.println("공항 개수: " + result.size());
		    result.forEach(System.out::println);
		    
		    // 추가적으로 결과가 0이면 경고
		    if (result.isEmpty()) {
		        System.err.println("⚠️  'CJU' 키워드로 검색된 공항이 없습니다.");
		    }
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		
	}

}
