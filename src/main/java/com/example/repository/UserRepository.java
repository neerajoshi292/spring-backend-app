package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
	
	@Query(value = "select * from user where first_name LIKE CONCAT(:firstName, '%')", nativeQuery = true)
	Optional<List<User>> findByFirstName(@Param("firstName") String firstName);
	
	@Query(value = "SELECT * FROM user WHERE org_id = :orgId", nativeQuery = true)
	List<User> findAllByOrgId(@Param("orgId") Long orgId);

}