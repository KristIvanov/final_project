package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import managers.UsersManager;
import model.Post;
import model.User;

/**
 * Servlet implementation class PrintUserPostsServlet
 */
@WebServlet("/printPosts")
public class PrintUserPostsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getParameter("userviewed");
		User u = UsersManager.getInstance().getRegisteredUsers().get(username);
		List<Post> posts = u.getPosts();
		request.setAttribute("userPosts", posts);
		
		
	}

	

}
