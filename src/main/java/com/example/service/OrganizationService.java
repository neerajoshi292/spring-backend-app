package com.example.service;

import java.util.List;

import com.example.dto.OrganizationDTO;
import com.example.entity.Organization;

public interface OrganizationService {

	Organization createOrgaization(OrganizationDTO organizationDTO);
	Organization updateOrgaization(Organization organization, Long orgId);
	List<Organization> viewAllOrgaization();
	Organization findByOrganizationId(Long orgId);
	Organization findByUniqueIdentityNumber(String uniqueIdentityNumber);
	Organization findByEmail(String email);
	Organization findByOrganizationName(String orgName);
	
	void deleteUserFromOrganization(Long orgId, Long userId);
}