package com.microservice.timesheet.Services;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.Repositories.TimeSheetRepo;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.dto.TimeSheetMapper;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@Service
public class ITimeSheetImpl<T> implements ITimeSheet {

	@Autowired
	TimeSheetRepo loRepo;

	@Autowired
	private TimeSheetMapper timeSheetMapper;

	@Override
	public TimeSheetDto saveTimeSheetEntity(TimeSheetDto poTimeSheetDto) {
		TimeSheetEntity poTimeSheetEntity = timeSheetMapper.toEntity(poTimeSheetDto);
		TimeSheetEntity lsTimeSheetEntity = loRepo.save(poTimeSheetEntity);
		return timeSheetMapper.toDto(lsTimeSheetEntity);
	}

	
	@Override
	public TimeSheetDto[] approveTimeSheetEntity(TimeSheetDto[] poTimeSheet) {

		return Arrays.stream(poTimeSheet).peek(timeSheet -> timeSheet.setLsStatus("Approved"))
				.toArray(TimeSheetDto[]::new);
	}

	@Override
	public TimeSheetDto[] rejectTimeSheetEntity(TimeSheetDto[] poTimeSheet) {

		return Arrays.stream(poTimeSheet).peek(timeSheet -> timeSheet.setLsStatus("Rejected"))
				.toArray(TimeSheetDto[]::new);
	}

	@Override
	public TimeSheetDto[] submitTimeSheetEntity(TimeSheetDto[] poTimeSheet) {

		return Arrays.stream(poTimeSheet).peek(timeSheet -> timeSheet.setLsStatus("Submitted"))
				.toArray(TimeSheetDto[]::new);

	}
	
	@Override
	public <T> T submitTimeSheetEntity1(T poTimeSheet) {

		return (T) Arrays.stream((T[]) poTimeSheet).peek(timeSheet -> ((TimeSheetEntity) timeSheet).setLsStatus("Submitted"))
				.toArray(TimeSheetDto[]::new);

	}

	@Override
	public TimeSheetDto getTimeSheetEntry(int lnId) throws CustomBusinessException {

		Optional<TimeSheetEntity> loTimeSheetEntity = loRepo.findById(lnId);
		(loTimeSheetEntity).orElseThrow(() -> new CustomBusinessException("There is no Entry for the given Id"));
		return timeSheetMapper.toDto(loTimeSheetEntity.get());
	}

	
	@Override
	public Page<TimeSheetEntity> getEntriesBasedOnPaginationAndSorting(Integer psId, Integer offset, Integer pagesize,
			String psFieldName) throws CustomBusinessException {

		if (pagesize != null && pagesize > 200) {
			throw new CustomBusinessException("PageSize cannot be greater than 200");
		}

		if (offset == null && pagesize == null) {

			offset = 0;
			pagesize = 20;
		}

		Sort sort;
		if (StringUtils.hasText(psFieldName)) {
			sort = Sort.by(Sort.Direction.ASC, psFieldName);
		} else {
			sort = Sort.unsorted();
		}

		PageRequest pageable = PageRequest.of(offset, pagesize, sort);

		Page<TimeSheetEntity> entityPage = loRepo.findByLsUserEntity_LsUserid(psId, pageable);

		return entityPage;

	}

}
