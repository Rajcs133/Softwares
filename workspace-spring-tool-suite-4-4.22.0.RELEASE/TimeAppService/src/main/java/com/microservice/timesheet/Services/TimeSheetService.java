package com.microservice.timesheet.Services;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@Service
public class TimeSheetService {

	@Autowired
	UserFactory loUserFactory;

	public Employee getUserObject() {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();

		boolean isAdmin = authorities.stream().map(GrantedAuthority::getAuthority)
				.anyMatch(authority -> "ROLE_ADMIN".equals(authority));

		return isAdmin ? ((UserFactory) loUserFactory).getUserObj("ADMIN")
				: ((UserFactory) loUserFactory).getUserObj("EMPLOYEE");
	}

	public TimeSheetDto saveTimeSheetEntry(TimeSheetDto poTimeSheetEntity) throws CustomBusinessException {

		TimeSheetDto lsTimeSheetEntity = (TimeSheetDto) getUserObject().fillTimeSheet(poTimeSheetEntity);

		return lsTimeSheetEntity;

	}

	public TimeSheetDto getEntryBasedOnTimeSheetId(int id) throws Exception {

		TimeSheetDto lsTimeSheetDto = new TimeSheetDto();

		lsTimeSheetDto = (TimeSheetDto) getUserObject().getEntryBasedOnTimeSheetId(id);

		return lsTimeSheetDto;

	}

	public Page<TimeSheetEntity> getEntriesBasedOnPaginationAndSorting(Integer psUserId, Integer offset,
			Integer pagesize, String psFieldName) throws Exception {

		return (Page<TimeSheetEntity>) getUserObject().getEntriesBasedOnPaginationAndSorting(psUserId, offset, pagesize,
				psFieldName);

	}

	public TimeSheetDto[] performTimeSheetTask(TimeSheetDto[] loTimeSheet, String lsMode)
			throws CustomBusinessException {

		return (TimeSheetDto[]) (getUserObject()).performTimeSheetTask(loTimeSheet, lsMode);

	}
	
	
	
	public <T> T performTimeSheetTask(T loTimeSheet, String lsMode, Class<T> returnType) throws CustomBusinessException {
	    return returnType.cast(getUserObject().performTimeSheetTask1(loTimeSheet, lsMode,returnType));
	}
	


}
