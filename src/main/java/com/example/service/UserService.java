package com.example.service;

import java.util.List;

import com.example.dto.UserDTO;
import com.example.entity.User;

public interface UserService {

	User createUser(UserDTO userDTO);
	User updateUser(UserDTO userDTO, Long uId);
	List<User> viewAllUsers();
	String deleteUser(Long uId);
	User findUserById(Long uId);
	User findByEmail(String email);
	List<User> findByFirstName(String firstName);	
}