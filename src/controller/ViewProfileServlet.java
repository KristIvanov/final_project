package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.UsersManager;
import model.User;

/**
 * Servlet implementation class ViewProfile
 */
@WebServlet("/ViewProfile")
public class ViewProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("userviewed_username");
		User u = UsersManager.getInstance().getRegisteredUsers().get(username);
		request.setAttribute("userviewed", u);
		request.getRequestDispatcher("profile.jsp").forward(request, response);
	}

	

}
