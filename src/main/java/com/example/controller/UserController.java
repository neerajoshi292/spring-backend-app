package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.service.UserService;
import com.google.common.base.Strings;

@RestController
@RequestMapping(value="u1/api/users")
@CrossOrigin
public class UserController {

	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<?> editUser(@RequestBody UserDTO userDTO, @RequestParam("uId") Long uId) {
		return ResponseEntity.ok(userService.updateUser(userDTO, uId));
	}
	
	@GetMapping
	public ResponseEntity<?> viewAllUser() {
		log.info("Hii inside the view all user");
		List<User> viewAllUsers = userService.viewAllUsers();
		return !CollectionUtils.isEmpty(viewAllUsers) ? ResponseEntity.ok(viewAllUsers) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchUser(
	        @RequestParam(required = false) Long uId,
	        @RequestParam(required = false) String email,
	        @RequestParam(required = false) String firstName
	) {
		int count = 0;
	    if (uId != null) count++;
	    if (!Strings.isNullOrEmpty(email)) count++;
	    if (!Strings.isNullOrEmpty(firstName)) count++;

	    if (count == 0) {
	        return ResponseEntity.badRequest().body("At least one search parameter must be provided.");
	    } else if (count > 1) {
	        return ResponseEntity.badRequest().body("Only one search parameter is allowed at a time.");
	    }
	    try {
		    if (uId != null) return ResponseEntity.ok(userService.findUserById(uId));
		    if (!Strings.isNullOrEmpty(email)) return ResponseEntity.ok(userService.findByEmail(email));
		    if (!Strings.isNullOrEmpty(firstName)) return ResponseEntity.ok(userService.findByFirstName(firstName));
	    } catch (Exception e) {
	    	log.error("Error searching User with parameters - uId: {}, email: {}, firstName: {}", 
	    			uId, email, firstName, e);
	    }
	    return ResponseEntity.internalServerError().body("Unexpected error in search"); 
	 }
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestParam("userId") Long userId) {
		log.info("Hii inside the delete user");
		String deleteUser = userService.deleteUser(userId);
		return ResponseEntity.ok(deleteUser);
	}
}