package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.UsersManager;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String fileName = "index.jsp";
	private static String errorMsg = " ";
       
    

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username").trim();
		String password = request.getParameter("password").trim();

		if(UsersManager.getInstance().validateLogin(username, UsersManager.getInstance().hashPassword(password))){
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			session.setAttribute("logged", true);
			User u = UsersManager.getInstance().getRegisteredUsers().get(username);
			request.getSession().setAttribute("username", u.getUsername());
			request.getSession().setAttribute("firstname", u.getFirst_name());
			request.getSession().setAttribute("lastname", u.getLast_name());
			request.getSession().setAttribute("email", u.getEmail());
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-control", "no-cache");
			fileName = "settings.jsp";
			if(session.getAttribute("url") != null) {
				fileName = (String) session.getAttribute("url");
			}
		}
		else{
			fileName = "login.jsp";
			errorMsg = "We did not recognise your username and password";
		}
		response.sendRedirect(fileName);
	}
	
	public static String getErrorMsg() {
		return LoginServlet.errorMsg;
	}

}
