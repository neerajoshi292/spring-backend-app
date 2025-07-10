package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class LoginResponseDTO {

	private Long orgId;
	private String orgName;
    private String UniqueIdentityNumber;
    private String email;
    private LocalDateTime createdOrg;
    private LocalDateTime updateOrg;
	private LocalDateTime closedOrg;
	private Boolean status;
    private List<UserDTO> users;
}
