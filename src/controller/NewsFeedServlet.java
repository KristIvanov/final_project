package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import managers.PostManager;
import managers.UsersManager;
import model.Post;
import model.User;

/**
 * Servlet implementation class NewsFeedServlet
 */
@WebServlet("/newsFeed")
public class NewsFeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Integer counter = 1;
       
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		if(ses.getAttribute("logged")!= null){
			boolean logged = (Boolean) request.getSession().getAttribute("logged");
			if(logged){
				ArrayList<Post> posts = new ArrayList<>();
				String username = (String) request.getParameter("username");
				User u = UsersManager.getInstance().getRegisteredUsers().get(username);
				for (Post post : PostManager.getInstance().getPosts().values()) {
					if (u.getFollowing().contains(post.getAuthor())){
						posts.add(post);
					}
				}
				//TODO return posts to be printed
				//each post in different form ?
			}
			else{
				response.sendRedirect("login.jsp");
			}
		}
		else{
			response.sendRedirect("login.jsp");
		}
	}

	

}
