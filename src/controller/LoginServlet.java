package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.UsersManager;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String fileName = "index.jsp";
	private static String errorMsg = " ";
       
    

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if(UsersManager.getInstance().validateLogin(username, UsersManager.getInstance().hashPassword(password))){
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("logged", true);
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-control", "no-cache");
			fileName = "profile.jsp";
		}
		else{
			fileName = "login.jsp";
		}
		response.sendRedirect(fileName);
	}
	
	public static String getErrorMsg() {
		return LoginServlet.errorMsg;
	}

}
