package com.bloombloom.customeraction;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloombloom.jdbc.dao.CustomerDAOImplement;

@WebServlet("/ChechBalance")
public class ChechBalance extends HttpServlet {
	
	
	
	
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		    System.out.println("the fucntion called chechkbalance");
	      
	        CustomerDAOImplement userDAO = new CustomerDAOImplement();
	        
	        int id = CustomerDAOImplement.currectCustomerID;
	         System.out.println(id);
	        int balance = userDAO.checkBalance(id );  // Assume a method that fetches balance
           
	        
	        System.out.println(id+" "+balance);
	        resp.setContentType("application/json");

	       
	        PrintWriter writer = resp.getWriter();
	       
	        writer.print("{\"balance\": " + balance + "}");
	    }
}
