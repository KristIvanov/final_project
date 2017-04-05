package dbModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import managers.DestinationManager;
import managers.UsersManager;
import model.Category;
import model.Destination;
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
		      Statement st = con.createStatement();
		      ResultSet resultSet = st.executeQuery("SELECT post_id,description,author_id,date,fk_destination_id FROM posts ");
		      while (resultSet.next()) {
		    	  //get all categories
		    	  PreparedStatement categoriesSt = con.prepareStatement("SELECT c.category_id,c.name,c.description FROM categories c JOIN posts_has_categories pc ON c.category_id=pc.categories_category_id WHERE pc.posts_post_id = ?");
		    	  long post_id = resultSet.getLong("post_id");
		    	  categoriesSt.setLong(1, post_id);
		    	  ResultSet categoriesRS = categoriesSt.executeQuery();
		      
		    	//temp collection for the post categories
		    	  HashSet<Category> categories = new HashSet<>();
		  		while(categoriesRS.next()) {
		  			categories.add(new Category(	categoriesRS.getString("name"),
		  											categoriesRS.getString("description")));
		  		}
		  		
		  		//get the author
		  		PreparedStatement authorST = con.prepareStatement("SELECT username FROM users WHERE user_id=?"); 
		  		authorST.setLong(1, resultSet.getLong("author_id"));
		  		ResultSet authorRS = authorST.executeQuery();
		  		authorRS.next();
		  		User author = UsersManager.getInstance().getRegisteredUsers().get(authorRS.getString("username"));
		  		Destination destination = DestinationManager.getInstance().getDestinations().get(resultSet.getLong("fk_destination_id"));
		  		//create the post and add it in the hashset
		  		Post post = new Post	(categories, 
		  								resultSet.getString("description"), 
		  								author, 
		  								resultSet.getTimestamp("date").toLocalDateTime(), 
		  								destination);
		  		
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
	
	