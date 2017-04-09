package controller;

import java.io.IOException;

import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dbModel.CommentDAO;
import managers.PostManager;
import managers.UsersManager;
import model.Comment;
import model.InvalidInputException;
import model.Post;
import model.User;

@WebServlet("/addComment")
public class AddCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String fileName = "index.jsp";
	private static String errorMsg = " ";
  
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("username") != null ) {
			String commentText = request.getParameter("text").trim();
			String username = (String) request.getSession().getAttribute("username");
			Long postId = (Long) request.getSession().getAttribute("postId"); //or maybe set the post id as parameter of the request
			
			request.getSession().setAttribute("text", commentText);
			
			try {
				this.validateData(commentText);
				User author = UsersManager.getInstance().getRegisteredUsers().get(username);
				Post post = PostManager.getInstance().getPosts().get(postId);
				LocalDateTime date = LocalDateTime.now();
				CommentDAO.getInstance().addNewComment(new Comment(author, commentText, post, date));
				fileName= "index.jsp";
			} catch (InvalidInputException e) {
				fileName= "addPost.jsp";
			}
		}
		else {
			fileName = "login.jsp";
		}
		response.sendRedirect(fileName);
	}

	public static String getErrorMsg() {
		return AddCommentServlet.errorMsg;
	}
	
	private  void validateData(String text) throws InvalidInputException{
		if(text == null || text.isEmpty()) {
			errorMsg = "Empty comment text!";
			throw new InvalidInputException("Empty comment text!");
		}
	
	}
	
}
