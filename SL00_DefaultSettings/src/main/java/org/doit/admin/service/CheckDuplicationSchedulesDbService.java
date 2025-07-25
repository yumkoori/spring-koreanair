package org.doit.admin.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.doit.admin.dto.SaveSchedulesDBDTO;
import org.doit.admin.mapper.CheckDuplicationSchedulesDbMapper;
import org.doit.admin.mapper.FlightScheduleSaveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckDuplicationSchedulesDbService {

	@Autowired
	private CheckDuplicationSchedulesDbMapper checkDuplicationSchedulesDbMapper;

	public int DuplicationSchedulesDB(List<SaveSchedulesDBDTO> scheduleList) throws Exception {
		System.out.println("중복서비스에서 받고 있습니다>>>>>>>");
		for (SaveSchedulesDBDTO saveSchedulesDBDTO : scheduleList) {
			String id = saveSchedulesDBDTO.getId(); // 예: "2025-06-16-all-FLZE821A_0"
			String dateStr = id.split("-all-")[0]; // "2025-06-16"
			LocalDate date = LocalDate.parse(dateStr); // LocalDate 객체로 변환


				String departureDateTimeStr = date + " " + saveSchedulesDBDTO.getDepartureTime() + ":00"; // "2025-06-16 00:17:00"
				saveSchedulesDBDTO.setDepartureTime(departureDateTimeStr);

				String arrivalDateTimeStr = date + " " + saveSchedulesDBDTO.getArrivalTime() + ":00";
				saveSchedulesDBDTO.setArrivalTime(arrivalDateTimeStr);
			
		}
			return checkDuplicationSchedulesDbMapper.duplicationSchedules(scheduleList);			
		}
	
}
