package com.microservice.timesheet.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@Service(Manager.BEAN_ID)
public class Manager extends Employee {

	public static final String BEAN_ID = "ADMIN";

	@Autowired
	ITimeSheet loTimeSheetService;

	private TimeSheetDto[] approveTimeSheet(TimeSheetDto[] loTimeSheet) {
		return loTimeSheetService.approveTimeSheetEntity(loTimeSheet);
	}

	@Override
	public TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] lsTimeSheet, String lsMode)
			throws CustomBusinessException {
		if (lsMode.equalsIgnoreCase(Mode.APPROVE.toString())) {
			lsTimeSheet = approveTimeSheet(lsTimeSheet);
			return (TimeSheetDto[]) lsTimeSheet;
		}

		if (lsMode.equalsIgnoreCase(Mode.REJECT.toString())) {
			TimeSheetDto[] poTimeSheetPaged = rejectTimeSheet(lsTimeSheet);
			return poTimeSheetPaged;
		}
		return lsTimeSheet;
	}

	private TimeSheetDto[] rejectTimeSheet(TimeSheetDto[] lsTimeSheet) {
		return loTimeSheetService.rejectTimeSheetEntity(lsTimeSheet);
	}

}
