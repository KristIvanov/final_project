package dbModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import managers.UsersManager;
import model.Category;
import model.InvalidInputException;
import model.Picture;
import model.Post;
import model.User;

public class PostDAO {

	private static PostDAO instance;
	
	private PostDAO() {
	}
	
	public static synchronized PostDAO getInstance() {
		if(instance == null) 
			instance = new PostDAO();
		return instance;
	}
	
	public synchronized Set<Post> getAllPosts() throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		con.setAutoCommit(false);
		HashSet<Post> posts = new HashSet<>();
			try { 
		      Statement postST = con.createStatement();
		      ResultSet postRS = postST.executeQuery("SELECT post_id,post_name,post_description,author_id,categories_category_id,date,destination_name,longitude,latitude FROM posts ");
		      while (postRS.next()) {
		    	long post_id = postRS.getLong("post_id");
		    	//get the category
		    	Category category = CategoryDAO.getInstance().categories.get(postRS.getLong("categories_category_id"));
		  		//get the author
		  		PreparedStatement authorST = con.prepareStatement("SELECT username FROM users WHERE user_id=?"); 
		  		authorST.setLong(1, postRS.getLong("author_id"));
		  		ResultSet authorRS = authorST.executeQuery();
		  		authorRS.next();
		  		User author = UsersManager.getInstance().getRegisteredUsers().get(authorRS.getString("username"));
		  		//create the post and add it in the hashset
		  		Post post = new Post	(postRS.getString("post_name"),
		  								category,
		  								postRS.getString("description"), 
		  								author, 
		  								postRS.getTimestamp("date").toLocalDateTime(), 
		  								postRS.getString("destination"),
		  								postRS.getDouble("longitude"),
		  								postRS.getDouble("latitude"));
		  		
			    	posts.add(post);
			    	post.setPostId(post_id);
			    	
			    	//get the pictures
			    	PreparedStatement pictureSt = con.prepareStatement("SELECT pictureURL FROM posts_has_pictures pp JOIN posts p ON pp.post_id=p.post_id WHERE pp.post_id=?");
			    	pictureSt.setLong(1, post_id);
			    	ResultSet pictureRS = pictureSt.executeQuery();
			    	while(pictureRS.next()) {
			    		post.addPicture(new Picture(pictureRS.getString("pictureURL")));
			    	}
			    	con.commit();
		      }
			}
			catch (SQLException e) {
			    System.out.println("Something went wrong while trying to get all posts!");
			    e.printStackTrace();
			    con.rollback();
			} catch (InvalidInputException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} 
			finally {
			   con.setAutoCommit(true);

			}
		
		return Collections.unmodifiableSet(posts);
	}
}
	
	