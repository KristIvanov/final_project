package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.UsersManager;
import model.MailSender;

@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String htmlFile="";
		if(!UsersManager.getInstance().getRegisteredUsers().containsKey(username)) {
			htmlFile="forgotPassword.html";
		} 
		else{
			htmlFile="passwordSent.html";
			String password = UsersManager.getInstance().getRegisteredUsers().get(username).getPassword();
			new MailSender(email, "Забравена парола за Travelbook", "Вашата парола е " + password + " . Заповядайте отново!");
		}
		request.getRequestDispatcher(htmlFile).forward(request, response);
		
	}

}
