package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

	@NotBlank(message = "email is required")
	@Email(message = "Email must be valid")
	private String email;
	@NotBlank(message = "password is required")
	private String password;
}
