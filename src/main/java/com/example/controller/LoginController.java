package com.example.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.LoginDTO;
import com.example.dto.LoginResponseDTO;
import com.example.dto.UserDTO;
import com.example.entity.Organization;
import com.example.entity.User;
import com.example.repository.OrganizationRepository;
import com.example.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/home")
@CrossOrigin(origins = "*")
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
    public ResponseEntity<?> loginOrg(@Valid @RequestBody LoginDTO loginDTO) {
        Optional<Organization> optionalOrg = organizationRepository
                .findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

        if (optionalOrg.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        Organization org = optionalOrg.get();
        
        List<User> users = userRepository.findAllByOrgId(org.getOrgId());

        List<UserDTO> userDTOs = users.stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
        
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        responseDTO.setOrgName(org.getOrgName());
        responseDTO.setUniqueIdentityNumber(org.getUniqueIdentityNumber());
        responseDTO.setEmail(org.getEmail());
        responseDTO.setCreatedOrg(org.getCreatedOrg());
        responseDTO.setUsers(userDTOs);
        
        return ResponseEntity.ok(responseDTO);
    }
	
	private UserDTO convertToUserDTO(User user) {
		
		try {
			if(user == null) return null;
		
	        UserDTO dto = new UserDTO();
	        dto.setUserId(user.getUserId());
	        dto.setFirstName(user.getFirstName());
	        dto.setMiddleName(user.getMiddleName());
	        dto.setLastName(user.getLastName());
	        dto.setEmail(user.getEmail());
	        dto.setDob(user.getDob());
	        dto.setAge(user.getAge());
	        dto.setGender(user.getGender());
	        dto.setDesignation(user.getDesignation());
	        dto.setPhone(user.getPhone());
	        dto.setStatus(user.getStatus());
	        return dto;
		} catch(Exception e) {
			log.error("Exception  : "+e.getMessage());
			return null;
		}
    }
}