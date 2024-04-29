package com.bloombloom.jdbc.dao;

import java.util.List;

import com.bloombloom.jdbc.dto.CustomerDTO;

public interface CustomerDAO {
	 List<CustomerDTO> getCustomer();
	 CustomerDTO getCustomer(String name,String password);
	 boolean getCustomer(String password);
	 boolean insertCustomer (String name,String email, String password);
	 boolean updateCustomer (CustomerDAO user);
	 boolean deleteCustomer (String emial);
	 
	 
	 int amountWithDraw (int balance);
	 int amountDeposite (int balance);
	 int checkBalance (int id);
	boolean updateCustomerBalance(int customerId,int newBalance);
	 
	 
	 
}
