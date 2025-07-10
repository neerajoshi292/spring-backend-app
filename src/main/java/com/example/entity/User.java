package com.example.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="first_name", length=20)
	private String firstName;
	
	@Column(name="middle_name", length=20)
	private String middleName;
	
	@Column(name="last_name", length=20)
	private String lastName;
	
	@Column(name="email", length=50)
	private String email;
	
	@Column(name="date_of_birth")
	private LocalDate dob;
	
	@Column(name="age", length=3)
	private Integer age;
	
	@Column(name="phone_number", length=10)
	private String phone;
	
	@Column(name="gender", length=12)
	private String gender;
	
	@Column(name="designation", length=50)
	private String designation;
	
	@ElementCollection
    @CollectionTable(name = "user_hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name="hobbies")
	private List<String> hobbies = new ArrayList<String>();
	
	@Column(name="password", length=20)
	private String password;
	
	@Column(name="user_status")
	private Boolean status;
	
	@Column(name="created_user")
	@CreationTimestamp
	private LocalDateTime createdUser;
	
	@Column(name="update_user")
	@UpdateTimestamp
	private LocalDateTime updateUser;
	
	@ManyToOne
	@JoinColumn(name = "org_id")
	@JsonBackReference
	private Organization organization;
}