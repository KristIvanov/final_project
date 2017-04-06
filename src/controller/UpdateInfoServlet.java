package controller;

import java.awt.SecondaryLoop;
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
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/updateInfo")
public class UpdateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String errorMsg = " ";
	private static String fileName = "updateInfo.jsp";
       
    

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession ses = req.getSession();
		if(ses.getAttribute("logged")!= null){
			boolean logged = (Boolean) req.getSession().getAttribute("logged");
			if(logged){
				String newUsername = req.getParameter("username");
				String newFirstname = req.getParameter("firstname");
				String newLastname = req.getParameter("lastname");
				String newEmail = req.getParameter("email");
				String confirmPass = req.getParameter("confirmPassword");
				HttpSession session = req.getSession();
				User u = UsersManager.getInstance().getRegisteredUsers().get((String )session.getAttribute("username"));
				if (UsersManager.getInstance().hashPassword(u.getPassword()).equals(confirmPass)){
					if (newEmail != u.getEmail()){
						if (!UsersManager.getInstance().validateEmailAddress(newEmail)){
							errorMsg = "New email address not valid";
						}
						else {
							u.setEmail(newEmail);
						}
					}
					if (newLastname != u.getLast_name()){
						if (newLastname==null || !newLastname.isEmpty()){
							errorMsg = "Enter last name please!";
						}
						else{
							u.setLast_name(newLastname);
						}
					}
					if (newFirstname != u.getFirst_name()){
						if (newFirstname==null || !newFirstname.isEmpty()){
							errorMsg = "Enter first name please!";
						}
						else{
							u.setFirst_name(newFirstname);
						}
					}
					if ((String )session.getAttribute("username")==newUsername){
						//check if username is valid
						if(newUsername == null || newUsername.isEmpty() || UsersManager.getInstance().getRegisteredUsers().containsKey(newUsername)){
							errorMsg = "Username already taken";
						}
						else {
							u.setUsername(newUsername);
						}
					}
					if (errorMsg == " "){
						UsersManager.getInstance().updateUser(u);
						errorMsg = "Update successful";
					}
				}
				else {
					errorMsg = "Password does not match our records, your changes were not saved!";
				}
				
				resp.sendRedirect(fileName);
			}
			else{
				resp.sendRedirect("login.jsp");
			}
		}
		else{
			resp.sendRedirect("login.jsp");
		}
		
	}
	

}
