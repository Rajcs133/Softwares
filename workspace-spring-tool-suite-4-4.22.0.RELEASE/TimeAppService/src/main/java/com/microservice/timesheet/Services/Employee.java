package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@Service(Employee.BEAN_ID)
public class Employee {

	public static final String BEAN_ID = "USER";


	@Autowired
	ITimeSheet loTimeSheetService;

	public TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] TimeSheet, String ModeValue)
			throws CustomBusinessException {
		TimeSheetDto[] loTimeSheetDto = null;

		if (ModeValue.equalsIgnoreCase(Mode.SUBMIT.toString())) {
			loTimeSheetDto = submitTimeSheetEntry(TimeSheet);
		}

		return loTimeSheetDto;
	}
	
	public <T> T performTimeSheetTask1(T loTimeSheet, String lsMode, Class<T> returnType)
			throws CustomBusinessException {
		T loTimeSheetDto = null;

		if (lsMode.equalsIgnoreCase(Mode.SUBMIT.toString())) {
			loTimeSheetDto = submitTimeSheetEntry1(loTimeSheet);
		}

		return returnType.cast(loTimeSheetDto);
	}

	public TimeSheetDto[] submitTimeSheetEntry(TimeSheetDto[] loTimeSheet) {

		return loTimeSheetService.submitTimeSheetEntity(loTimeSheet);

	}
	
	public <T> T submitTimeSheetEntry1(T loTimeSheet) {

		return loTimeSheetService.submitTimeSheetEntity1(loTimeSheet);

	}
	
	
	public TimeSheetDto fillTimeSheet(TimeSheetDto poTimeSheetEntity) {		
		return loTimeSheetService.saveTimeSheetEntity(poTimeSheetEntity);
	}
	
	
    public TimeSheetDto getEntryBasedOnTimeSheetId(int id) throws Exception {
		
    	return loTimeSheetService.getTimeSheetEntry(id);

	}
    
    public Page<TimeSheetEntity> getEntriesBasedOnPaginationAndSorting(Integer lsUserId,Integer offset, Integer pagesize,
			String psFieldName) throws CustomBusinessException {
    	
    	return loTimeSheetService.getEntriesBasedOnPaginationAndSorting(lsUserId, offset, pagesize, psFieldName);
    }
	
	
	

}
