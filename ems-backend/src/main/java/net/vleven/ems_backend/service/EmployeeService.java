package net.vleven.ems_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.vleven.ems_backend.dto.EmployeeDto;


@Service
public interface EmployeeService {
		
	EmployeeDto createEmployee(EmployeeDto employeeDto);

	boolean existsByEmail(String email);
	
	EmployeeDto getEmployeeById(Long employeeId);
	
	List<EmployeeDto> getAllEmployee();
	
	EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee);
	
	void deleteEmployee(Long employeeId);
}
