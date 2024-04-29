package com.bloombloom.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.bloombloom.jdbc.connect.ConnectJDBC;
import com.bloombloom.jdbc.dto.CustomerBankDeatils;
import com.bloombloom.jdbc.dto.CustomerDTO;

public class CustomerDAOImplement implements CustomerDAO {
   public static  int currectCustomerID ;
	@Override
	public List<CustomerDTO> getCustomer() {
		List<CustomerDTO> customersList = null;
		try {
			Connection reqCon = ConnectJDBC.requestConnection();
			String query = "select * from bank";
			Statement stment = reqCon.createStatement();
			ResultSet exQuery = stment.executeQuery(query);

			customersList = new ArrayList<CustomerDTO>();

			while (exQuery.next()) {
				int id = exQuery.getInt(1);
				String name = exQuery.getString(2);
				String email = exQuery.getString(3);
				String password = exQuery.getString(4);

				int accNumber = exQuery.getInt(5); // Assuming the account number is in column 5
				int balance = exQuery.getInt(6); // Assuming the balance is in column 6

				// Create a CustomerBankDetails object with the account number and balance
				CustomerBankDeatils customerBankDetails = new CustomerBankDeatils(accNumber, balance);

				CustomerDTO customer = new CustomerDTO(id, name, email, password, customerBankDetails);
				customersList.add(customer);

			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return customersList;
	}

	@Override
	public CustomerDTO getCustomer(String email, String password) {
		CustomerDTO customer = null;
		Connection reqCon = null;
		PreparedStatement preStment = null;
		ResultSet exQuery = null;

		try {
			reqCon = ConnectJDBC.requestConnection();
			String query = "SELECT b.id, b.name, b.email, b.password, bd.accnumber, bd.balance " +
		               "FROM bank b " +
		               "JOIN bankdetails bd ON b.id = bd.accnumber " +
		               "WHERE b.email = ? AND b.password = ?";
			preStment = reqCon.prepareStatement(query);
			System.out.println("query executing");
			preStment.setString(1, email);
			preStment.setString(2, password);

			exQuery = preStment.executeQuery();
             System.out.println(exQuery+"   empty");
			if (exQuery.next()) { // Check if ResultSet has next row
				
				int customerId = exQuery.getInt(1);
				String customerName = exQuery.getString(2);
				String customerEmail = exQuery.getString(3);
				String customerPassword = exQuery.getString(4);
                
				System.out.println(customerId+" "+customerName+" "+customerEmail+" "+customerPassword);
				
				System.out.println(customerId);
				
				  
				
				
				int accNumber = exQuery.getInt(5); // Assuming the account number is in column 5
				int balance = exQuery.getInt(6); // Assuming the balance is in column 6
  
				
				System.out.println(accNumber+" "+balance);
				
				// Create a CustomerBankDetails object with the account number and balance
				CustomerBankDeatils customerBankDetails = new CustomerBankDeatils(accNumber, balance);

				customer = new CustomerDTO(customerId, customerName, customerEmail, customerPassword, customerBankDetails);
				currectCustomerID = customer.getId();
			    System.out.println(currectCustomerID);
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

	@Override
	public boolean getCustomer(String password) {
		//CustomerDTO customer = null;
		Connection reqCon = null;
		PreparedStatement preStment = null;
		ResultSet exQuery = null;

		try {
			reqCon = ConnectJDBC.requestConnection();
			String query = "SELECT * FROM bank WHERE password = ? ";
			preStment = reqCon.prepareStatement(query);
			preStment.setString(1, password);

			exQuery = preStment.executeQuery();

			if (exQuery.next()) { // Check if ResultSet has next row
//				int customerId = exQuery.getInt(1);
//				String customerName = exQuery.getString(2);
//				String customerEmail = exQuery.getString(3);
				String customerPassword = exQuery.getString(4);

				if (password.equals(customerPassword)) {
					return true;

				}

//				int accNumber = exQuery.getInt(5); // Assuming the account number is in column 5
//				int balance = exQuery.getInt(6); // Assuming the balance is in column 6

				// Create a CustomerBankDetails object with the account number and balance
			//	CustomerBankDeatils customerBankDetails = new CustomerBankDeatils(accNumber, balance);

//				CustomerDTO customer = new CustomerDTO(id, name, email, password, customerBankDetails);

				//customer = new CustomerDTO(customerId, customerName, customerEmail, customerPassword, customerBankDetails);
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

		return false;
	}

	@Override
	public boolean insertCustomer(String name, String email, String password) {
		int exQuery = 0;
		Connection reqCon = null;
		try {
			reqCon = ConnectJDBC.requestConnection();
			PreparedStatement preStment = reqCon.prepareStatement("INSERT INTO bank (name, email, password) VALUES (?, ?, ?)");
			preStment.setString(1, name);
			preStment.setString(2, email);
			preStment.setString(3, password);

			exQuery = preStment.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			if (reqCon != null) {
				try {
					reqCon.rollback();
				} catch (SQLException rollbackException) {
					rollbackException.printStackTrace();
				}
			}
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			if (reqCon != null) {
				try {
					// Commit the transaction if no exception occurred during execution
					if (exQuery == 1) {
						reqCon.commit();
					} else {
						// Rollback the transaction if the execution failed
						reqCon.rollback();
					}
				} catch (SQLException commitException) {
					commitException.printStackTrace();
				}
				try {
					reqCon.close();
				} catch (SQLException closeException) {
					closeException.printStackTrace();
				}
			}
		}
		return exQuery == 1;
	}

	@Override
	public boolean deleteCustomer(String emial) {

		return false;
	}

	@Override
	public boolean updateCustomer(CustomerDAO user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int amountWithDraw(int withDrawAmount) {
		if (withDrawAmount <= 0) {
	        System.out.println("Invalid amount entered. Please enter an amount greater than zero.");
	        return 0;
	    }
		Connection reqCon = null;
		PreparedStatement preStment = null;
		ResultSet exQuery = null;
		
		 try {
		        // Assuming you have a method to get the current customer data
		        CustomerDTO customer = CustomerCurrentBankDeatiles.getCurrentCustomerByID(currectCustomerID); // Implement this method to retrieve the current customer data
		        if (customer == null) {
		            System.out.println("Customer not found.");
		            return 0;
		        }
		        
		        // Get the current balance from the customer's bank details
		        CustomerBankDeatils customerBankDeatils = customer.getCustomerBankDeatils();
		        int currentBalance = customerBankDeatils.getBalance(); // Assuming getBalance() returns the current balance
		        
		        // Check if the withdrawal amount is within the current balance
		        if (withDrawAmount > currentBalance) {
		            System.out.println("Insufficient balance. Please enter a lesser amount.");
		            return 0;
		        }
		        
		        // Deduct the withdrawal amount from the current balance
		        int newBalance = currentBalance - withDrawAmount;
		        
		        // Update the user's balance in the database
		        try {
					reqCon = ConnectJDBC.requestConnection();
				} catch (ClassNotFoundException | NamingException e) {
					
					e.printStackTrace();
				} // Implement this method to establish a database connection
		        preStment = reqCon.prepareStatement("UPDATE customers SET balance = ? WHERE id = ?");
		        preStment.setInt(1, newBalance);
		        preStment.setInt(2, customer.getId());
		        
		        // Execute the update
		        int rowsUpdated = preStment.executeUpdate();
		        if (rowsUpdated > 0) {
		            System.out.println("Withdrawal successful. Your new balance is: " + newBalance);
		            // Update the customer object with the new balance
		            customerBankDeatils.setBalance(newBalance); // Assuming setBalance() method sets the new balance
		            return newBalance;
		        } else {
		            System.out.println("An error occurred while updating your balance. Please try again later.");
		        }
		        
		    } catch (SQLException e) {
		        System.out.println("An error occurred: " + e.getMessage());
		        return 0;
		    } finally {
		        // Close resources
		        try {
		            if (exQuery != null) exQuery.close();
		            if (preStment != null) preStment.close();
		            if (reqCon != null) reqCon.close();
		        } catch (SQLException e) {
		            System.out.println("Error closing resources: " + e.getMessage());
		        }
		    }
		    return 0;
	}

	@Override
	public int amountDeposite(int balance) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkBalance(int currectCustomerID) {
	 CustomerDTO currentCustomerByID = CustomerCurrentBankDeatiles.getCurrentCustomerByID(currectCustomerID);
	 CustomerBankDeatils customerBankDeatils = currentCustomerByID.getCustomerBankDeatils();
	 int balance = customerBankDeatils.getBalance();
	 return balance;
	}

	@Override
	public boolean updateCustomerBalance(int newBalance, int customerId) {
		 String query = "UPDATE bankdetails SET balance = ? WHERE accnumber = ?";

	        try (Connection connection = ConnectJDBC.requestConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	            preparedStatement.setInt(1, newBalance);
	            preparedStatement.setInt(2, customerId);

	            int rowsAffected = preparedStatement.executeUpdate();

	            // Return true if the update was successful (one or more rows affected)
	            return rowsAffected > 0;

	        } catch (SQLException | NamingException | ClassNotFoundException e) {
	            // Log errors
	            e.printStackTrace();
	            return false;
	        }	
		 }
	
	

	
}