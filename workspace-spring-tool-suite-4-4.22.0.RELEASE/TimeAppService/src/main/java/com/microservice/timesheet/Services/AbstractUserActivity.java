package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

public abstract class AbstractUserActivity {

	@Autowired
	ITimeSheet loTimeSheetService;
	
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
	
	
	public abstract TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] TimeSheet, String lsMode) throws CustomBusinessException;

}
