package com.example.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.dto.OrganizationDTO;
import com.example.entity.Organization;
import com.example.entity.User;
import com.example.exception.CustomErrorHandleException;
import com.example.exception.OrganizationNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.repository.OrganizationRepository;
import com.example.repository.UserRepository;
import com.example.service.OrganizationService;
import com.example.utility.CommanUtilityClass;
import com.google.common.base.Strings;

import jakarta.transaction.Transactional;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrganizationServiceImpl implements OrganizationService{

	private static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
	
	@Autowired
	private OrganizationRepository organizationRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Organization createOrgaization(OrganizationDTO organizationDTO) {
		// TODO Auto-generated method stub
		Organization organization = new Organization();
		
		mapDtoToEntity(organizationDTO, organization);
		
		return organizationRepository.save(organization);
	}

	@Override
	public Organization updateOrgaization(Organization organization, Long orgId) {
		// TODO Auto-generated method stub
		Organization existOrg = findByOrganizationId(orgId);
		
		if(!Strings.isNullOrEmpty(organization.getOrgName())) {
			existOrg.setOrgName(organization.getOrgName());
		}
		if(!Strings.isNullOrEmpty(organization.getUniqueIdentityNumber())) {
			existOrg.setUniqueIdentityNumber(organization.getUniqueIdentityNumber());
		}
		if(!Strings.isNullOrEmpty(organization.getEmail())) {
			existOrg.setEmail(organization.getEmail());
		}
		if(!Strings.isNullOrEmpty(organization.getPassword())) {
			existOrg.setPassword(organization.getPassword());
		}
		if(organization.getStatus() != null) {
			existOrg.setStatus(organization.getStatus());
			
			if(!organization.getStatus()) {
				existOrg.setClosedOrg(LocalDateTime.now());
			}
		}
		
//		here we also write a logic for user if we want to update from organization end
		
		if(!CollectionUtils.isEmpty(organization.getUsers())) {
			log.info("---------- inside the update Organization_User block ----------");
			List<User> listUser = new ArrayList<>();
			
			for(User u : organization.getUsers()) {
				
				User user = null;
				if(u.getUserId() != null) {
					user = userRepository.findById(u.getUserId()).orElseThrow(()-> new UserNotFoundException("User Not Found with Id : "+u.getUserId()));
				}else {
					user = new User();
				}
				
				if(!Strings.isNullOrEmpty(u.getFirstName())) {
					user.setFirstName(u.getFirstName());
				}
				if(!Strings.isNullOrEmpty(u.getMiddleName())) {
					user.setMiddleName(u.getMiddleName());
				}
				if(!Strings.isNullOrEmpty(u.getLastName())) {
					user.setLastName(u.getLastName());
				}
				if(!Strings.isNullOrEmpty(u.getEmail())) {
					user.setEmail(u.getEmail());
				}
				if(u.getDob() != null) {
					user.setDob(u.getDob());
					user.setAge(CommanUtilityClass.calculateAge(u.getDob()));
				}
				if(!Strings.isNullOrEmpty(u.getPhone())) {
					user.setPhone(u.getPhone());
				}
				if(!Strings.isNullOrEmpty(u.getGender())) {
					user.setGender(u.getGender());
				}
				if(!Strings.isNullOrEmpty(u.getDesignation())) {
					user.setDesignation(u.getDesignation());
				}
				if(!CollectionUtils.isEmpty(u.getHobbies())) {
					user.setHobbies(u.getHobbies());
				}
				if(!Strings.isNullOrEmpty(u.getPassword())) {
					user.setPassword(u.getPassword());
				}
				if(u.getStatus() != null) {
					user.setStatus(u.getStatus());
				}
				user.setOrganization(existOrg);
				listUser.add(user);
			}
			existOrg.setUsers(listUser);
		}
		
		return organizationRepository.save(existOrg);
	}

	@Override
	public List<Organization> viewAllOrgaization() {
		// TODO Auto-generated method stub
		return organizationRepository.findAll();
	}

	@Override
	public Organization findByOrganizationId(Long orgId) {
		// TODO Auto-generated method stub
		return organizationRepository.findById(orgId).orElseThrow(()-> new OrganizationNotFoundException("Organization id not found : "+orgId));
	}

	@Override
	public Organization findByUniqueIdentityNumber(String uniqueIdentityNumber) {
		// TODO Auto-generated method stub
		return organizationRepository.findByUniqueIdentityNumber(uniqueIdentityNumber).orElseThrow(()-> new OrganizationNotFoundException("Organization uniqueIdentityNumber not found : "+uniqueIdentityNumber));
	}

	@Override
	public Organization findByEmail(String email) {
		// TODO Auto-generated method stub
		return organizationRepository.findByEmail(email).orElseThrow(()-> new OrganizationNotFoundException("Organization email not found : "+email));
	}

	@Override
	public Organization findByOrganizationName(String orgName) {
		// TODO Auto-generated method stub
		return organizationRepository.findByOrganizationName(orgName).orElseThrow(()-> new OrganizationNotFoundException("Organization name not found : "+orgName));
	}
	
	@Override
	public void deleteUserFromOrganization(Long orgId, Long userId) {
		log.info("------ inside the deleteUserFromOrganization method -----------{}{}",orgId, userId);
	    Organization organization = findByOrganizationId(orgId);

	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new CustomErrorHandleException("User not found with id: " + userId));
	    
	    if (!user.getOrganization().getOrgId().equals(orgId)) {
	        throw new CustomErrorHandleException("User does not belong to this organization");
	    }
	    log.info("------ user deleted process start ----------");
	    userRepository.delete(user);
	    log.info("------ user deleted process end ----------");
	}
	
	private void mapDtoToEntity(OrganizationDTO organizationDTO, Organization organization) {
		try {
			if(!Strings.isNullOrEmpty(organizationDTO.getOrgName())) {
				organization.setOrgName(organizationDTO.getOrgName());
			}
			if(!Strings.isNullOrEmpty(organizationDTO.getUniqueIdentityNumber())) {
				organization.setUniqueIdentityNumber(organizationDTO.getUniqueIdentityNumber());
			}
			if(!Strings.isNullOrEmpty(organizationDTO.getEmail())) {
				organization.setEmail(organizationDTO.getEmail());
			}
			if(!Strings.isNullOrEmpty(organizationDTO.getPassword())) {
				organization.setPassword(organizationDTO.getPassword());
			}
			if(organizationDTO.getStatus() != null) {
				organization.setStatus(organizationDTO.getStatus());
			}
			
//			handle the logic here for User 
			if(!CollectionUtils.isEmpty(organizationDTO.getUsers())) {
				log.info("---------- inside the Organization_User block ----------");
				List<User> listUser = new ArrayList<>();
				
				for(User u : organizationDTO.getUsers()) {
					User user = new User();
					
					if(!Strings.isNullOrEmpty(u.getFirstName())) {
						user.setFirstName(u.getFirstName());
					}
					if(!Strings.isNullOrEmpty(u.getMiddleName())) {
						user.setMiddleName(u.getMiddleName());
					}
					if(!Strings.isNullOrEmpty(u.getLastName())) {
						user.setLastName(u.getLastName());
					}
					if(!Strings.isNullOrEmpty(u.getEmail())) {
						user.setEmail(u.getEmail());
					}
					if(u.getDob() != null) {
						user.setDob(u.getDob());
						user.setAge(CommanUtilityClass.calculateAge(u.getDob()));
					}
					if(!Strings.isNullOrEmpty(u.getPhone())) {
						user.setPhone(u.getPhone());
					}
					if(!Strings.isNullOrEmpty(u.getGender())) {
						user.setGender(u.getGender());
					}
					if(!Strings.isNullOrEmpty(u.getDesignation())) {
						user.setDesignation(u.getDesignation());
					}
					if(!CollectionUtils.isEmpty(u.getHobbies())) {
						user.setHobbies(u.getHobbies());
					}
					if(!Strings.isNullOrEmpty(u.getPassword())) {
						user.setPassword(u.getPassword());
					}
					if(u.getStatus() != null) {
						user.setStatus(u.getStatus());
					}
					user.setOrganization(organization);
					listUser.add(user);					
				}
				organization.setUsers(listUser);
			} else {
				log.warn("----- At the create organization time user not saved -------------");
			}
		} catch (Exception e) {
			throw new CustomErrorHandleException("Error while mapping OrganizationDTO to Organization : "+e);
		}
	}	
}