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
import com.bloombloom.jdbc.dto.CustomerDTO;

@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set the response content type to JSON
        response.setContentType("application/json");
        
        // Create a PrintWriter to write the response
        PrintWriter out = response.getWriter();
        
        // Parse the request body to retrieve the amount the user wants to deposit
//        Gson gson = new Gson();
//        JsonObject requestBody = gson.fromJson(request.getReader(), JsonObject.class);
        String depositAmount = request.getParameter("amount");
        		//("amount").getAsDouble();
        System.out.println(depositAmount);
//        // Retrieve the user's email from the request (you may use session or other method as required)
//        String userEmail = request.getParameter("email");
        int currectCustomerID = CustomerDAOImplement.currectCustomerID;
        CustomerDTO user = CustomerCurrentBankDeatiles.getCurrentCustomerByID(currectCustomerID);
        

//        // Create an instance of the DAO implementation
//        CustomerDAOImplement userDAO = new CustomerDAOImplement();
//        
//        // Fetch the user from the database using the email
//        CustomerDTO user = userDAO.getCustomerByEmail(userEmail);
        
        if (user == null) {
            // If the user is not found, return an error response
//            JsonObject errorResponse = new JsonObject();
//            errorResponse.addProperty("success", false);
//            errorResponse.addProperty("message", "User not found.");
            out.print("Error while Deposit the Amount");
            return;
        }
        
        // Update the user's balance by adding the deposit amount
        String newBalance = user.getCustomerBankDeatils().getBalance() + depositAmount;
           CustomerDAOImplement cust = new CustomerDAOImplement();
           boolean updateSuccess = cust.updateCustomerBalance(Integer.parseInt(newBalance), currectCustomerID);
        
        //.set(Integer.parseInt(newBalance));
        
        // Update the user record in the database
        //boolean updateSuccess = userDAO.updateCustomer(user);
        
        if (updateSuccess) {
            // Create a JSON object with success status and the new balance
//            JsonObject successResponse = new JsonObject();
//            successResponse.addProperty("success", true);
//            successResponse.addProperty("newBalance", newBalance);
            
            // Write the JSON response
            out.print("Successfully Updated");
        } else {
            // If the update fails, return an error response
//            JsonObject errorResponse = new JsonObject();
//            errorResponse.addProperty("success", false);
//            errorResponse.addProperty("message", "Failed to update the balance.");
            out.print("failed to update");
        }
    }

}
