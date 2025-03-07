package com.in.cafe.pojo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

@Entity
@Table(name= "user")
public class User implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "Id")
	private Integer id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "ContactNumber")
	private String contactNumber;
	
	@Column(name = "Email")
	private String email;
	
    @Column(name = "password")
	private String password;
    
    @Column(name = "Status")
    private String status;
	
	@Column(name = "Role")
	private String role;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Integer id, String name, String contactNumber, String email, String password, String status,
			String role) {
		super();
		this.id = id;
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.password = password;
		this.status = status;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	
	

}
