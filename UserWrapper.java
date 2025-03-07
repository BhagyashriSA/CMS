package com.in.cafe.wrapper;

public class UserWrapper {
	
	private Integer Id;
	private String name;
	private String contactNumber;;
	private String email;
	private String status;
	
	public UserWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserWrapper(Integer id, String name, String contactNumber, String email, String status) {
		super();
		Id = id;
		this.name = name;
		this.contactNumber = contactNumber;
		this.email = email;
		this.status = status;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
