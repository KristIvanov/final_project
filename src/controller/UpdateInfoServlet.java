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
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/updateInfo")
public class UpdateInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String errorMsg = " ";
	private static String fileName = "updateInfo.jsp";
       
    public static String getErrorMsg() {
		return errorMsg;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession ses = req.getSession();
		if(ses.getAttribute("logged")!= null){
			boolean logged = (Boolean) req.getSession().getAttribute("logged");
			if(logged){
				String newUsername = req.getParameter("newUsername").trim();
				String newFirstname = req.getParameter("newFirstname").trim();
				String newLastname = req.getParameter("newLastname").trim();
				String newEmail = req.getParameter("newEmail").trim();
				String confirmPass = req.getParameter("confirmPassword").trim();
				HttpSession session = req.getSession();
				User u = UsersManager.getInstance().getRegisteredUsers().get((String )session.getAttribute("username"));
				if (UsersManager.getInstance().hashPassword(confirmPass).equals(u.getPassword())){
					if (newEmail != u.getEmail()){
						if (!UsersManager.getInstance().validateEmailAddress(newEmail)){
							errorMsg = "New email address not valid";
						}
						else {
							u.setEmail(newEmail);
						}
					}
					System.out.println(newLastname + "newlastname");
					System.out.println(u.getLast_name() + " u.getLastname");
					if (newLastname != u.getLast_name()){
						if (newLastname==null || newLastname.isEmpty()){
							errorMsg = "Enter last name please!";
						}
						else{
							u.setLast_name(newLastname);
							System.out.println(u.getLast_name());
						}
					}
					if (newFirstname != u.getFirst_name()){
						System.out.println(newFirstname + " <- newFirsname if");
						if (newFirstname==null || newFirstname.isEmpty()){
							errorMsg = "Enter first name please!";
						}
						else{
							u.setFirst_name(newFirstname);
							System.out.println(u.getFirst_name());
						}
					}
					if ((String )session.getAttribute("username")==newUsername){
						//check if username is valid
						if(newUsername == null || newUsername.isEmpty() || UsersManager.getInstance().getRegisteredUsers().containsKey(newUsername)){
							errorMsg = "Username already taken";
						}
						else {
							u.setUsername(newUsername);
							System.out.println(u.getUsername());
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
