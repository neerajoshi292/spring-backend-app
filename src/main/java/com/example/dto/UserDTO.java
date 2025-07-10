package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.entity.Organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

	private Long userId;

	@NotBlank(message = "first name is required")
	@Pattern(regexp = "^[A-Za-z]{3,15}$", message = "first name only in alphabets")
	private String firstName;
	
	@Pattern(regexp = "^[A-Za-z]{3,15}$", message = "middle name only in alphabets")
	private String middleName;
	
	@NotBlank(message = "last name is required")
	@Pattern(regexp = "^[A-Za-z]{3,15}$", message = "last name only in alphabets")
	private String lastName;
	
	@NotBlank(message = "email name is required")
	@Email(message = "must be a well-formed email address like example@exp.com")
	private String email;
	
	@NotNull(message = "Date of birth is required")
	private LocalDate dob;
	
	private Integer age;
	
	@NotBlank(message = "phone number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "phone number must in number(0-9)")
	private String phone;
	
	@NotBlank(message = "gender is required")
	private String gender;
	
	@NotBlank(message = "designation is required")
	private String designation;
	
	private List<String> hobbies;
	
	@NotBlank(message = "password is required")
	@Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
    )
	private String password;
	
	@NotNull(message = "status is required")
	private Boolean status;
	
	private LocalDateTime createdUser;
	
	private LocalDateTime updateUser;
	
	@NotNull
	private Organization organization;
}