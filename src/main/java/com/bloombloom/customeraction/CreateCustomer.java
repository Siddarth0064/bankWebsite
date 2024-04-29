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


@WebServlet("/CreateCustomer")
public class CreateCustomer extends HttpServlet {
  
	private static final long serialVersionUID = 1L;

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String userName = req.getParameter("name");
	    String useremail = req.getParameter("email");
		String userPasswprd = req.getParameter("password");
		
	     CustomerDAOImplement userDAO = new CustomerDAOImplement();
	     
	     boolean user = userDAO.insertCustomer(userName, useremail, userPasswprd);
	     
	     resp.setContentType("text/html");
	     PrintWriter writer = resp.getWriter();
	     
	     
	     
	     if (user==false) {
	    	 writer.print("<h3> Invalid User Details</h3>");
	    	 return;
	     }
	     writer.print("<h3> WelCome to "+ userName+"</h3>");
	     RequestDispatcher dispatcher = req.getRequestDispatcher("/homepage.html");
	     dispatcher.forward(req, resp);
	}
}


