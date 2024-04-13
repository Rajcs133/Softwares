package com.microservice.timesheet.Services;

import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserFactoryImpl implements UserFactory {

	public final Map<String,Employee> allUserObj;
	
	
	@Override
	public Employee getUserObj(String psRole) {
		return allUserObj.get(psRole);
	}

}
