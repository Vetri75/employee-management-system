package net.vleven.ems_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.vleven.ems_backend.dto.EmployeeDto;
import net.vleven.ems_backend.repository.EmployeeRepository;
import net.vleven.ems_backend.service.EmployeeService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "Endpoints for managing employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	
	//Build Add Employee REST API
	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	@Operation(summary = "Create a new employee", description = "Adds a new employee to the database")
	public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
		
		 // Check if the email already exists in the database
		if (employeeService.existsByEmail(employeeDto.getEmail())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists!");
	    }
		
		EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
		
	}
	
	//Build Get Employee REST API
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'HR', 'EMPLOYEE')")
	@Operation(summary = "Get an employee by ID", description = "Fetches employee details using employee ID")
	public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("id") Long employeeId){
		
		EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
		return ResponseEntity.ok(employeeDto);	
	
	}
	
	//Build Get All Employees REST API
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
	@Operation(summary = "Get all employees", description = "Fetches a list of all employees")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees(){
		
		List<EmployeeDto> employees = employeeService.getAllEmployee();
		return ResponseEntity.ok(employees);
		
	}
	
	//Build Updated Employee REST API
	@PutMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update an employee", description = "Updates details of an existing employee")
	public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long employeeId, @RequestBody EmployeeDto updateEmployee){
		
		EmployeeDto employeeDto = employeeService.updateEmployee(employeeId, updateEmployee);
		return ResponseEntity.ok(employeeDto);
	
	}
	
	//Delete Employee REST API
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete an employee", description = "Deletes an employee from the system")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long employeeId){
		employeeService.deleteEmployee(employeeId);
		return ResponseEntity.ok("Employee Deleted SuccessFully!.");
	}
	
	
}
