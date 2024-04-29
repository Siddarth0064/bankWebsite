package com.bloombloom.customeraction;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloombloom.jdbc.dao.CustomerCurrentBankDeatiles;
import com.bloombloom.jdbc.dao.CustomerDAOImplement;
import com.bloombloom.jdbc.dto.CustomerBankDeatils;
import com.bloombloom.jdbc.dto.CustomerDTO;


@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	
	
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  System.out.println("do post function called");
		  req.setCharacterEncoding("UTF-8");
	       
	        String withdrawalAmount = req.getParameter("amount");
	        if (withdrawalAmount == null || withdrawalAmount.isEmpty()) {
	            // Handle the case where the amount parameter is null or empty
//	            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Amount parameter is missing or empty.");
//	            return;
	        }
	        System.out.println(withdrawalAmount+"  balance");
	        
	        
	        
	        
	        CustomerDAOImplement customerDAO = new CustomerDAOImplement();
            int customerId = CustomerDAOImplement.currectCustomerID;
	        
	        CustomerDTO currentCustomer = CustomerCurrentBankDeatiles.getCurrentCustomerByID(customerId);
            System.out.println(currentCustomer);
	        
	        
	        
	        // Initialize the response content type as JSON
	        resp.setContentType("application/json");

	        PrintWriter out = resp.getWriter();

	        if (currentCustomer == null) {
	            // If customer not found, send an error response
	        	out.write(getServletInfo());
	            out.println("{\"status\": \"error\", \"message\": \"Customer not found.\"}");
	            return;
	        }

	        // Get the customer's bank details
	         CustomerBankDeatils bankDetails = currentCustomer.getCustomerBankDeatils();
	        int currentBalance = bankDetails.getBalance();
	        System.out.println(currentBalance);
  
	        if (Integer.parseInt(withdrawalAmount) <= 0) {
	            // If withdrawal amount is invalid, send an error response
	            out.println("{\"status\": \"error\", \"message\": \"Invalid withdrawal amount.\"}");
	            return;
	        }

	        if (Integer.parseInt(withdrawalAmount) > currentBalance) {
	            // If insufficient funds, send an error response
	            out.println("{\"status\": \"error\", \"message\": \"Insufficient balance.\"}");
	            return;
	        }

	        // Deduct the withdrawal amount from the current balance
	        int newBalance = currentBalance - Integer.parseInt(withdrawalAmount);
	        bankDetails.setBalance(newBalance);

	        // Update the customer's balance in the database
	        boolean updateSuccess = customerDAO.updateCustomerBalance(customerId, newBalance);
            System.out.println(updateSuccess);
	        
	        if (updateSuccess) {
	            // Send a success response with the new balance
	            out.println("{\"status\": \"success\", \"newBalance\": \"" + newBalance + "\"}");
	        } else {
	            // If an error occurred during the update, send an error response
	            out.println("{\"status\": \"error\", \"message\": \"An error occurred while updating balance.\"}");
	        }
	    }
	
	
}
