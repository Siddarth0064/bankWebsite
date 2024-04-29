package com.bloombloom.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.bloombloom.jdbc.connect.ConnectJDBC;
import com.bloombloom.jdbc.dto.CustomerBankDeatils;
import com.bloombloom.jdbc.dto.CustomerDTO;

public class CustomerCurrentBankDeatiles {
	
	public static CustomerDTO getCurrentCustomerByID(int id) {
		CustomerDTO customer = null;
		Connection reqCon = null;
		PreparedStatement preStment = null;
		ResultSet exQuery = null;

		try {
			reqCon = ConnectJDBC.requestConnection();
			String query = "SELECT b.id, b.name, b.email, b.password, bd.accnumber, bd.balance " +
		               "FROM bank b " +
		               "JOIN bankdetails bd ON b.id = bd.accnumber " +
		               "WHERE b.id = ?";
			preStment = reqCon.prepareStatement(query);
			preStment.setInt(1, id);
			

			exQuery = preStment.executeQuery();

			if (exQuery.next()) { // Check if ResultSet has next row
				int customerId = exQuery.getInt(1);
				String customerName = exQuery.getString(2);
				String customerEmail = exQuery.getString(3);
				String customerPassword = exQuery.getString(4);
                
				
				
				
				int accNumber = exQuery.getInt(5); // Assuming the account number is in column 5
				int balance = exQuery.getInt(6); // Assuming the balance is in column 6

				// Create a CustomerBankDetails object with the account number and balance
				CustomerBankDeatils customerBankDetails = new CustomerBankDeatils(accNumber, balance);

				customer = new CustomerDTO(customerId, customerName, customerEmail, customerPassword, customerBankDetails);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				if (exQuery != null) {
					exQuery.close();
				}
				if (preStment != null) {
					preStment.close();
				}
				if (reqCon != null) {
					reqCon.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        System.out.println(customer);
		return customer;
	}



}
