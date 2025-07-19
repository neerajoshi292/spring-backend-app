package com.example.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dto.UserDTO;
import com.example.entity.Organization;
import com.example.entity.User;
import com.example.exception.CustomErrorHandleException;
import com.example.exception.OrganizationNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.repository.OrganizationRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.utility.CommanUtilityClass;
import com.google.common.base.Strings;

import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public User createUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		log.info("---------- inside the create user method ----------- req :: {} ",userDTO);
		if(userDTO.getOrganization() == null || userDTO.getOrganization().getOrgId() == null) {
			throw new CustomErrorHandleException("Organization can't get null if we want to save the user");
		}
		
		Organization organization = organizationRepository.findById(userDTO.getOrganization().getOrgId()).orElseThrow(()-> new OrganizationNotFoundException("Organization id not found : "+userDTO.getOrganization().getOrgId()));
		
		User user = new User();
		
		if(organization != null) {
			user.setOrganization(organization);
		}
		mapDtoToEntity(userDTO, user);
		
		return userRepository.save(user);
	}

	@Override
	public User updateUser(UserDTO userDTO, Long uId) {
		// TODO Auto-generated method stub
		
		log.info("---------- inside the update user method ----------- req :: {}",userDTO);
				
		User existUser = findUserById(uId);
		
		if(existUser.getOrganization() == null || existUser.getOrganization().getOrgId() == null) {
			throw new CustomErrorHandleException("Organization can't get null if we want to save the user");
		}
		
		Organization organization = organizationRepository.findById(existUser.getOrganization().getOrgId()).orElseThrow(()-> new OrganizationNotFoundException("Organization id not found : "+existUser.getOrganization().getOrgId()));
		
		if(organization != null) {
			existUser.setOrganization(organization);
		}
		mapDtoToEntity(userDTO, existUser);
		
		return userRepository.save(existUser);
	}

	@Override
	public List<User> viewAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User findUserById(Long uId) {
		// TODO Auto-generated method stub
		return userRepository.findById(uId).orElseThrow(()-> new UserNotFoundException("User id not found : "+uId));
	}

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User email not found : "+email));
	}

	@Override
	public List<User> findByFirstName(String firstName) {
		// TODO Auto-generated method stub
		return userRepository.findByFirstName(firstName).orElseThrow(()-> new UserNotFoundException("User first_name not found : "+firstName));
	}
	
	private void mapDtoToEntity(UserDTO userDTO, User user) {
		
		try {
			log.info("------- inside the mapDtoToEntity method -------start ");
			if(!Strings.isNullOrEmpty(userDTO.getFirstName())) {
				user.setFirstName(userDTO.getFirstName());
			}
			if(!Strings.isNullOrEmpty(userDTO.getMiddleName())) {
				user.setMiddleName(userDTO.getMiddleName());
			}
			if(!Strings.isNullOrEmpty(userDTO.getLastName())) {
				user.setLastName(userDTO.getLastName());
			}
			if(!Strings.isNullOrEmpty(userDTO.getEmail())) {
				user.setEmail(userDTO.getEmail());
			}
			if(userDTO.getDob() != null) {
				user.setDob(userDTO.getDob());
				user.setAge(CommanUtilityClass.calculateAge(userDTO.getDob()));
			}
			if(!Strings.isNullOrEmpty(userDTO.getPhone())) {
				user.setPhone(userDTO.getPhone());
			}
			if(!Strings.isNullOrEmpty(userDTO.getGender())) {
				user.setGender(userDTO.getGender());
			}
			if(!Strings.isNullOrEmpty(userDTO.getDesignation())) {
				user.setDesignation(userDTO.getDesignation());
			}
			if(!CollectionUtils.isEmpty(userDTO.getHobbies())) {
				user.setHobbies(userDTO.getHobbies());
			}
			if(!Strings.isNullOrEmpty(userDTO.getPassword())) {
				user.setPassword(userDTO.getPassword());
			}
			if(userDTO.getStatus() != null) {
				user.setStatus(userDTO.getStatus());
			}
			
			log.info("------- inside the mapDtoToEntity method -------end");
		} catch (Exception e) {
			log.error("------- Exception :: {}",e);
			throw new CustomErrorHandleException("Error while mapping UserDTO to User"+e);
		}
	}

	@Override
	public String deleteUser(Long uId) {
		// TODO Auto-generated method stub
		
		User find = findUserById(uId);
		userRepository.delete(find);
		return "User Deleted !";
	}	
}