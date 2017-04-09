package dbModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import managers.PostManager;
import managers.UsersManager;
import model.Comment;
import model.InvalidInputException;
import model.Post;
import model.User;

public class CommentDAO {

private static CommentDAO instance;
	
	public ConcurrentHashMap<Long, Comment> comments;

	private CommentDAO() {
		comments= new ConcurrentHashMap<>();
		Connection con = DBManager.getInstance().getConnection();
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("SELECT comment_id,author_id,text,posts_post_id FROM comments");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PreparedStatement authorST = con.prepareStatement("SELECT username FROM users WHERE user_id=?"); 
		  		authorST.setLong(1, rs.getLong("author_id"));
		  		ResultSet authorRS = authorST.executeQuery();
		  		authorRS.next();
		  		User author = UsersManager.getInstance().getRegisteredUsers().get(authorRS.getString("username"));
		  		Post post = PostManager.getInstance().getPosts().get(rs.getLong("posts_post_id"));
				comments.put(rs.getLong("comment_id"), new Comment(author, rs.getString("text"), post));
				con.commit();
				rs.close();
				ps.close();
				authorRS.close();
				authorST.close();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (InvalidInputException e) {
			e.printStackTrace();
		}
		finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static synchronized CommentDAO getInstance() {
		if(instance == null) 
			instance = new CommentDAO();
		return instance;
	}
	
	public Map<Long, Comment> getALlComments() {
		return Collections.unmodifiableMap(comments);
	}
	

	public synchronized void deleteComment(Comment com){
		PreparedStatement prepSt;
		  try {
			prepSt = DBManager.getInstance().getConnection().prepareStatement("DELETE FROM comments WHERE comment_id=?");
			prepSt.setLong(1, com.getComment_id());
			prepSt.executeUpdate();
			prepSt.close();
			System.out.println("Post successfully deleted!");
		  } catch (Exception e) {
			 System.out.println(e.getMessage());
		  }
	}
	
	
	//TODO add comment and delete comment
	//TODO create add comment jsp and servlet
	
	
}
