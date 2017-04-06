package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.UsersManager;

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
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		
		if(UsersManager.getInstance().validateRegistration(username, password, firstname, lastname, email)) {
			UsersManager.getInstance().register(username, password, firstname, lastname, email);
			fileName= "index.jsp";
		
		}
		else{
			fileName= "register.jsp";
			errorMsg = "Registration failed!";
		}
		
		resp.sendRedirect(fileName);
	}

	public static String getErrorMsg() {
		return RegisterServlet.errorMsg;
	}
	
	

}
