package com.example.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "organization")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="org_id")
	private Long orgId;
	
	@Column(name="org_name", length = 150)
	private String orgName;
	
	@Column(name="unique_identity_number", unique = true, length = 12)
	private String uniqueIdentityNumber;
	
	@Column(name="email", unique = true, length=50)
	private String email;
	
	@Column(name="created_org")
	@CreationTimestamp
	private LocalDateTime createdOrg;
	
	@Column(name="update_org")
	@UpdateTimestamp
	private LocalDateTime updateOrg;
	
	@Column(name="closed_org")
	private LocalDateTime closedOrg;
	
	@Column(name="status")
	private Boolean status;
	
	@Column(name="password", length=50)
	private String password;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<User> users;
}