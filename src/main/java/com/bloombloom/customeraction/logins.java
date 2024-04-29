package com.bloombloom.customeraction;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bloombloom.jdbc.dao.CustomerDAOImplement;
import com.bloombloom.jdbc.dto.CustomerDTO;


/**
 * Servlet implementation class login
 */
@WebServlet("/logins")
public class logins extends HttpServlet {
	@Override
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Retrieve email and password from request parameters
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Create an instance of the DAO implementation
        CustomerDAOImplement userDAO = new CustomerDAOImplement();
        
        // Fetch the user from the database
        CustomerDTO user = userDAO.getCustomer(email, password);
        
        // Set response content type
        resp.setContentType("text/html");
        
        // Check if user is found
        if (user == null) {
            // If user not found, print an error message
            PrintWriter writer = resp.getWriter();
            writer.print("<h3>USER NOT FOUND</h3>");
            return;
        }
        
        // Forward the request to homepage.html
        // Set an attribute in the request to pass the user's name to the homepage
        req.setAttribute("account-holder-name", user.getName());
        
        // Obtain a RequestDispatcher for forwarding the request to homepage.html
//        RequestDispatcher dispatcher = req.getRequestDispatcher("homepage.html");
//        
//        // Forward the request and response to homepage.html
//        dispatcher.forward(req, resp);
        
        req.setAttribute("account-holder-name", user.getName());
//System.out.println(user.getName());
//        RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage.jsp");
//        dispatcher.forward(req, resp);
        
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage.html");
        dispatcher.forward(req, resp);

        
        
    }

}
