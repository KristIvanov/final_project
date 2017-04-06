package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.UsersManager;
import model.InvalidInputException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String fileName = "index.jsp";
	private static String errorMsg = " ";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		String firstname = req.getParameter("firstname").trim();
		String lastname = req.getParameter("lastname").trim();
		String email = req.getParameter("email").trim();
		//saving old inputs in session to have filled inputs if something was invalid
		req.getSession().setAttribute("username", username);
		req.getSession().setAttribute("password", password);
		req.getSession().setAttribute("firstname", firstname);
		req.getSession().setAttribute("lastname", lastname);
		req.getSession().setAttribute("email", email);
		
		try {
			this.validateData(username, password, firstname, lastname, email);
			UsersManager.getInstance().register(username, password, firstname, lastname, email);
			fileName= "index.jsp";
		} catch (InvalidInputException e) {
			fileName= "register.jsp";
		}
		resp.sendRedirect(fileName);
	}

	public static String getErrorMsg() {
		return RegisterServlet.errorMsg;
	}
	
	private  void validateData(String username, String password, String firstName, String lastName, String email) throws InvalidInputException{
		if(username == null || username.isEmpty() || UsersManager.getInstance().getRegisteredUsers().containsKey(username)) {
			errorMsg = "Invalid username!";
			throw new InvalidInputException("Invalid username!");
		}
		if(!checkString(firstName)) {
			errorMsg = "Invalid first name!";
			throw new InvalidInputException("Invalid first name!");
		}
		if(!checkString(lastName)) {
			errorMsg = "Invalid last name!";
			throw new InvalidInputException("Invalid last name!");
		}
		
		if (!UsersManager.getInstance().validateEmailAddress(email)) {
			errorMsg = "Invalid email!";
			throw new InvalidInputException("Invalid email!");
		}
		
		if(!UsersManager.getInstance().validatePassword(password)){
			errorMsg = "Invalid password!";
			throw new InvalidInputException("Invalid password!");
		}
	}
	
	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
	

}
