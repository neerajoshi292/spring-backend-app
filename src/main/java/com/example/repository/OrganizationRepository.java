package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long>{

	Optional<Organization> findByUniqueIdentityNumber(String uniqueIdentityNumber);
	
	Optional<Organization> findByEmail(String email);
	
	@Query(value = "select * from organization where org_name LIKE CONCAT(:orgName, '%')", nativeQuery = true)
	Optional<Organization> findByOrganizationName(@Param("orgName") String orgName);
	
	Optional<Organization> findByEmailAndPassword(String email, String password);
}