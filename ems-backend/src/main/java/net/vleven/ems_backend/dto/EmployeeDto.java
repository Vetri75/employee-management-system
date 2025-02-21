package net.vleven.ems_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private Long id;
	
	@NotBlank(message = "first name is required")
	@Size(min = 2, message = "First name should have at least 2 characters")
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	private String LastName;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;
	
}
