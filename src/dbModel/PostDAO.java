package dbModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import managers.UsersManager;
import model.Category;
import model.Comment;
import model.InvalidInputException;
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
		HashSet<Post> posts = new HashSet<>();
			try { 
				con.setAutoCommit(false);
				Statement postST = con.createStatement();
				ResultSet postRS = postST.executeQuery("SELECT post_id,post_name,post_description,author_id,categories_category_id,date,destination_name,longitude,latitude,picture_url FROM posts ");
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
			  								postRS.getString("post_description"), 
			  								author, 
			  								postRS.getTimestamp("date").toLocalDateTime(), 
			  								postRS.getString("destination_name"),
			  								postRS.getDouble("longitude"),
			  								postRS.getDouble("latitude"),
			  								postRS.getString("picture_url"));
			  		posts.add(post);
			    	post.setPostId(post_id);
			  		
			  		//get all comments
			  		PreparedStatement commentsST = con.prepareStatement("SELECT author_id,text,videoURL FROM comments");
			  		ResultSet commentsRS = commentsST.executeQuery();
			  		while(commentsRS.next()) {
			  			PreparedStatement commentAuthorST = con.prepareStatement("SELECT username FROM users WHERE user_id=?"); 
			  			commentAuthorST.setLong(1, commentsRS.getLong("author_id"));
				  		ResultSet commentAuthorRS = commentAuthorST.executeQuery();
				  		authorRS.next();
				  		User commentAuthor = UsersManager.getInstance().getRegisteredUsers().get(commentAuthorRS.getString("username"));
				  		post.addComment(new Comment(	commentAuthor,
				  										commentsRS.getString("text"), 
				  										post,
				  										commentsRS.getString("videoURL")));
				  		commentAuthorRS.close();
				  		commentAuthorST.close();
			  		}
			  		authorRS.close();
			  		authorST.close();
			  		commentsRS.close();
			  		commentsST.close();
			  		postRS.close();
			  		postST.close();
			  		
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
	
	public synchronized void addNewPost(Post p) {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement("INSERT INTO posts (post_name,author_id,post_description,categories_category_id,date,destination_name,longitude,latitude,picture_url) VALUES (?,?,?,(SELECT category_id FROM categories WHERE name=?),?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getPostName());
			ps.setLong(2, p.getAuthor().getUserId());
			ps.setString(3, p.getDescription());
			System.out.println(p.getCategory().getName());
			ps.setString(4, p.getCategory().getName());
			ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
			ps.setString(6, p.getDestination());
			ps.setBigDecimal(7, new BigDecimal(p.getLongitude()));
			ps.setBigDecimal(8, new BigDecimal(p.getLatitude()));
			ps.setString(9, p.getPictureURL());
			ps.executeUpdate();
			//TODO fix problem with double and decimal in DB
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			long postId = rs.getLong(1);
			p.setPostId(postId);
			rs.close();
			ps.close();
			System.out.println("Post added successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	public synchronized void deletePost(Post p){
		PreparedStatement prepSt;
		  try {
			prepSt = DBManager.getInstance().getConnection().prepareStatement("DELETE FROM TABLE posts WHERE post_id=?");
			prepSt.setLong(1, p.getPostId());
			prepSt.executeUpdate();
			prepSt.close();
			System.out.println("Post successfully deleted!");
		  } catch (Exception e) {
			 System.out.println(e.getMessage());
		  }
	}
	
	
	//TODO create view all posts with their comments jsp 
	//TODO create view particular post jsp
}
	
	