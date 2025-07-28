package org.doit.admin.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.mapper.FlightScheduleSaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveSchedulesDBService {

	@Autowired
	private FlightScheduleSaveMapper flightScheduleSaveMapper;

	public int saveSchedulesDB(List<SaveSchedulesDBDTO> scheduleList) throws Exception {
		// System.out.println("서비스에서 받고 있습니다>>>>>>>" + scheduleList);
			return flightScheduleSaveMapper.saveSchedules(scheduleList);
		}
	
}
