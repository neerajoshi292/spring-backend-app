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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrganizationDTO;
import com.example.entity.Organization;
import com.example.service.OrganizationService;
import com.google.common.base.Strings;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/o1/api/organizations")
@CrossOrigin
public class OrganizationController {

	private final Logger log = LoggerFactory.getLogger(OrganizationController.class); 
	
	@Autowired
	private OrganizationService organizationService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) {
		return new ResponseEntity<>(organizationService.createOrgaization(organizationDTO), HttpStatus.CREATED);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<?> editOrganization(@Valid @RequestBody Organization organizationDTO, @RequestParam("orgId") Long orgId) {
		return ResponseEntity.ok(organizationService.updateOrgaization(organizationDTO, orgId));
	}
	
	@GetMapping
	public ResponseEntity<?> viewAllOrganizationList() {
		List<Organization> viewAllOrgaization = organizationService.viewAllOrgaization();
		return !CollectionUtils.isEmpty(viewAllOrgaization) ? ResponseEntity.ok(viewAllOrgaization) : ResponseEntity.noContent().build();
	}
/*	
	@GetMapping("search/{orgId}")
	public ResponseEntity<?> viewByOrganizationId(@PathVariable("orgId") Long orgId) {
		return ResponseEntity.ok(organizationService.findByOrganizationId(orgId));
	}
	
	@GetMapping("search/{email}")
	public ResponseEntity<?> viewByOrganizationEmail(@RequestParam("email") String email) {
		return ResponseEntity.ok(organizationService.findByEmail(email));
	}
	
	@GetMapping("search/{orgName}")
	public ResponseEntity<?> viewByOrganizationOrgName(@RequestParam("orgName") String orgName) {
		return ResponseEntity.ok(organizationService.findByOrganizationName(orgName));
	}
	
	@GetMapping("search/{uniqueIdentityNumber}")
	public ResponseEntity<?> viewByOrganizationuniqueIdentityNumber(@RequestParam("uniqueIdentityNumber") String uniqueIdentityNumber) {
		return ResponseEntity.ok(organizationService.findByUniqueIdentityNumber(uniqueIdentityNumber));
	}
*/
	
	@GetMapping("/search")
	public ResponseEntity<?> searchOrganization(
	        @RequestParam(required = false) Long orgId,
	        @RequestParam(required = false) String email,
	        @RequestParam(required = false) String orgName,
	        @RequestParam(required = false) String uniqueIdentityNumber
	) {
		int count = 0;
	    if (orgId != null) count++;
	    if (!Strings.isNullOrEmpty(email)) count++;
	    if (!Strings.isNullOrEmpty(orgName)) count++;
	    if (!Strings.isNullOrEmpty(uniqueIdentityNumber)) count++;

	    if (count == 0) {
	        return ResponseEntity.badRequest().body("At least one search parameter must be provided.");
	    } else if (count > 1) {
	        return ResponseEntity.badRequest().body("Only one search parameter is allowed at a time.");
	    }
	    try {
		    if (orgId != null) return ResponseEntity.ok(organizationService.findByOrganizationId(orgId));
		    if (!Strings.isNullOrEmpty(email)) return ResponseEntity.ok(organizationService.findByEmail(email));
		    if (!Strings.isNullOrEmpty(orgName)) return ResponseEntity.ok(organizationService.findByOrganizationName(orgName));
		    if (!Strings.isNullOrEmpty(uniqueIdentityNumber)) return ResponseEntity.ok(organizationService.findByUniqueIdentityNumber(uniqueIdentityNumber));
	    } catch (Exception e) {
	        log.error("Error searching organization with parameters - orgId: {}, email: {}, orgName: {}, uniqueIdentityNumber: {}", 
	                  orgId, email, orgName, uniqueIdentityNumber, e);
	        return ResponseEntity.internalServerError().body("An error occurred while searching for the organization.");
	    }
	    return ResponseEntity.internalServerError().body("Unexpected error in search");
	}
	
	@DeleteMapping("/org-user/{orgId}/users/{userId}")
	public ResponseEntity<?> deleteUserByOrganization(
	        @PathVariable("orgId") Long orgId,
	        @PathVariable("orgId") Long userId) {
	    
	    organizationService.deleteUserFromOrganization(orgId, userId);
	    return ResponseEntity.ok("User deleted successfully");
	}
	
	@GetMapping("/org-list")
	public ResponseEntity<?> viewAllOrgList() {
		List<Organization> viewAllOrgaization = organizationService.viewAllOrgaization();
		return !CollectionUtils.isEmpty(viewAllOrgaization) ? ResponseEntity.ok(viewAllOrgaization) : ResponseEntity.noContent().build();
	}
}