package com.microservice.timesheet.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.timesheet.Entity.TimeSheetEntity;
import com.microservice.timesheet.Services.TimeSheetService;
import com.microservice.timesheet.dto.TimeSheetDto;
import com.microservice.timesheet.exceptionadvice.CustomBusinessException;

@RestController
@RequestMapping
public class TimeSheetServiceController {

	@Autowired
	TimeSheetService loTimeSheetService;
	
	/**
	 * Saves a time sheet entry.
	 * 
	 * @param poTimeSheet The time sheet entry to be saved.
	 * @return ResponseEntity containing the saved TimeSheetDto.
	 * @throws CustomBusinessException if an error occurs during the operation.
	 */

	@PostMapping(path = "/saveTimeSheetEntry")
	public ResponseEntity<TimeSheetDto> saveTimeSheetEntry(@RequestBody TimeSheetDto poTimeSheet)
			throws CustomBusinessException {
		poTimeSheet = loTimeSheetService.saveTimeSheetEntry(poTimeSheet);
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);

	}

	
	/**
	 * Retrieves a time sheet entry based on the Time sheet ID.
	 * 
	 * @param Id The ID of the time sheet entry to retrieve.
	 * @return ResponseEntity containing the retrieved TimeSheetDto.
	 * @throws Exception if an error occurs during the operation.
	 */
	@GetMapping(path = "/getEntriesBasedOnTimeSheetId/{Id}")
	public ResponseEntity<TimeSheetDto> getEntryBasedOnTimeSheetId(@PathVariable("Id") int Id) throws Exception {

		TimeSheetDto poTimeSheetDto = loTimeSheetService.getEntryBasedOnTimeSheetId(Id);
		return new ResponseEntity<>(poTimeSheetDto, HttpStatus.OK);
	}

	
	/**
	 * Processes time sheet entries based on the provided mode.
	 * 
	 * @param poTimeSheet Array of TimeSheetDto objects to process.
	 * @param psMode The mode of processing.
	 * @return ResponseEntity containing the processed TimeSheetDto array.
	 * @throws CustomBusinessException if an error occurs during the operation.
	 */
	@PostMapping(path = "/processTimeSheet/{Mode}")
	public ResponseEntity<TimeSheetDto[]> processTimeSheet(@RequestBody TimeSheetDto[] poTimeSheet,
			@PathVariable("Mode") String psMode) throws CustomBusinessException {
		poTimeSheet = loTimeSheetService.performTimeSheetTask(poTimeSheet, psMode);
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);

	}
	
	
	@PostMapping(path = "/processTimeSheetDynamic/{Mode}")
	public <T> ResponseEntity<T> processTimeSheet1(@RequestBody T poTimeSheet,
			@PathVariable("Mode") String psMode) throws CustomBusinessException {
		 Class<T> returnType = (Class<T>) poTimeSheet.getClass();
		poTimeSheet = loTimeSheetService.performTimeSheetTask(poTimeSheet, psMode,returnType);
		return new ResponseEntity<>(poTimeSheet, HttpStatus.OK);

	}
	
	
	private <T> Class<T> getTypeToken(T poTimeSheet) {
	    return (Class<T>) poTimeSheet.getClass();
	}

	/**
	 * Retrieves time sheet entries based on pagination and sorting.
	 * 
	 * @param psOffset Offset for pagination.
	 * @param psPageSize Size of each page.
	 * @param psFieldName Name of the field to sort by.
	 * @param lsUserId ID of the user whose time sheet entries to retrieve.
	 * @return ResponseEntity containing the Page of TimeSheetEntity objects.
	 * @throws Exception if an error occurs during the operation.
	 */
	@GetMapping(path = "/getEntriesBasedOnUserId/{lsUserId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<TimeSheetEntity>> getEntriesBasedOnPaginationAndSorting(
			@RequestParam(name = "offset", required = false) Integer psOffset,
			@RequestParam(name = "pagesize", required = false) Integer psPageSize,
			@RequestParam(name = "fieldName", required = false) String psFieldName,
			@PathVariable("lsUserId") int lsUserId) throws Exception {

		return new ResponseEntity<Page<TimeSheetEntity>>(
				loTimeSheetService.getEntriesBasedOnPaginationAndSorting(lsUserId, psOffset, psPageSize, psFieldName),
				HttpStatus.OK);

	}

}
