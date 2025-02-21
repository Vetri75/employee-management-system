package net.vleven.ems_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import net.vleven.ems_backend.dto.EmployeeDto;
import net.vleven.ems_backend.entity.Employee;
import net.vleven.ems_backend.exception.EmailAlreadyExistsException;
import net.vleven.ems_backend.exception.ResourceNotFoundException;
import net.vleven.ems_backend.mapper.EmployeeMapper;
import net.vleven.ems_backend.repository.EmployeeRepository;
import net.vleven.ems_backend.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService{

	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		
		if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
	        throw new EmailAlreadyExistsException("Email already exists!");
	    }
		
		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		Employee savedEmployee = employeeRepository.save(employee);
		
		return EmployeeMapper.mapToEmployeeDto(savedEmployee);
	
	}
	
	@Override
	public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

	@Override
	public EmployeeDto getEmployeeById(Long employeeId) {
		
		Employee employee = employeeRepository
				.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee isn't exist with given id" + employeeId));
		
		return EmployeeMapper.mapToEmployeeDto(employee);
	
	}

	@Override
	public List<EmployeeDto> getAllEmployee() {
		
	    List<Employee> employees = employeeRepository.findAll();
	    
	    return employees.stream()
	            .map(EmployeeMapper::mapToEmployeeDto) 
	            .collect(Collectors.toList()); 
	
	}

	@Override
	public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
		
		Employee employee = employeeRepository.findById(employeeId)
		.orElseThrow(() -> new ResourceNotFoundException("Employee isn't exist with given id" + employeeId));
		
		// Check if email is already taken by another employee
	    Optional<Employee> employeeWithSameEmail = employeeRepository.findByEmail(updatedEmployee.getEmail());
	    if (employeeWithSameEmail.isPresent() && !employeeWithSameEmail.get().getId().equals(employeeId)) {
	        throw new EmailAlreadyExistsException("Email already exists!");
	    }
		
		employee.setFirstName(updatedEmployee.getFirstName());
		employee.setLastName(updatedEmployee.getLastName());
		employee.setEmail(updatedEmployee.getEmail());
		
		Employee updateEmployeeObj = employeeRepository.save(employee);
		
		return EmployeeMapper.mapToEmployeeDto(updateEmployeeObj);
	
	}

	@Override
	public void deleteEmployee(Long employeeId) {
		
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee isn't exist with given id" + employeeId));
		
		employeeRepository.deleteById(employeeId);
		
	}

	
	
	

}
