package com.bloombloom.jdbc.dto;

public class CustomerDTO {

	private int id;
	private String name;
	private String email;
	private String password;
	private CustomerBankDeatils customerBankDeatils;
	
	public CustomerDTO() {
		super();
	}
	
	public CustomerDTO(int id, String name, String email, String password, CustomerBankDeatils customerBankDeatils) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.customerBankDeatils = customerBankDeatils;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		return id+" "+name+" "+email+" "+password;
	}
	public CustomerBankDeatils getCustomerBankDeatils() {
		return customerBankDeatils;
	}
	public void setCustomerBankDeatils(CustomerBankDeatils customerBankDeatils) {
		this.customerBankDeatils = customerBankDeatils;
	}
	
	
	


}
