package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.entity.User;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationDTO {

	private Long orgId;
	
	@NotBlank(message = "organization name is required")
	@Size(min = 8, max = 100)
	private String orgName;

	@NotBlank(message = "unique identity number is required")
	@Pattern(regexp = "^[a-zA-Z0-9]{12}", message = "uniqueIdentityNumber can't get the regx format as applied")
	private String uniqueIdentityNumber;
	
	@NotBlank(message = "email is required")
	@Email(message = "must be a well-formed email address like example@exp.com")
	private String email;
	
	private LocalDateTime createdOrg;
	private LocalDateTime updateOrg;
	private LocalDateTime closedOrg;
	
	@Column(name="is_active", insertable = false)
	private Boolean status;
	
	@NotBlank(message = "password is required")
	@Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
    )
	private String password;
	
	private List<User> users;

}
