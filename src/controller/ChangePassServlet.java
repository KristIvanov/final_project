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
 * Servlet implementation class ChangePassServlet
 */
@WebServlet("/changePass")
public class ChangePassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String errorMsg = " ";
	private static String fileName = "updateInfo.jsp";
       
	public static String getErrorMsg() {
		return errorMsg;
	}
    
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		errorMsg = " ";
		HttpSession ses = req.getSession();
		if(ses.getAttribute("logged")!= null){
			boolean logged = (Boolean) req.getSession().getAttribute("logged");
			if(logged){
				String oldPass = req.getParameter("oldPassword");
				String newPass = req.getParameter("newPassword");
				HttpSession session = req.getSession();
				User u = UsersManager.getInstance().getRegisteredUsers().get((String )session.getAttribute("username"));
				if (UsersManager.getInstance().hashPassword(oldPass).equals(u.getPassword())){
					if (!UsersManager.getInstance().validatePassword(newPass)){
						errorMsg = "Password is not safe!";
					}
					else {
						UsersManager.getInstance().updatePass(newPass, u);
					}
				}
				else{
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
