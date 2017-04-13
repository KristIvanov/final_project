package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbModel.UserDAO;
import managers.PostManager;
import managers.UsersManager;
import model.Post;
import model.User;

/**
 * Servlet implementation class QuickSearch
 */
@WebServlet("/quickSearch")
public class QuickSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String words = request.getParameter("searchFor").trim();
		String[] keywords = words.split(" ");
		System.out.println(words);
		List<Post> resultsByTag = PostManager.getInstance().searchByTags(keywords);
		List<Post> resultsByDestination = PostManager.getInstance().searchByDestination(words);
		List<User> resultsByUser = UsersManager.getInstance().searchUser(words);
		
		HttpSession session = request.getSession();
		session.setAttribute("resultsByTag", resultsByTag);
		session.setAttribute("resultsByDestination", resultsByDestination);
		session.setAttribute("resultsByUser", resultsByUser);
		response.sendRedirect("searchResults.jsp");
	}

	

}
