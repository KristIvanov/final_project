package dbModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import managers.PostManager;
import managers.UsersManager;
import model.InvalidInputException;
import model.User;


public class UserDAO {
	
  private static UserDAO instance;
  
  private UserDAO() {}
  
  public static synchronized UserDAO getInstance() {
    if (instance == null) {
      instance = new UserDAO();
    }
    return instance;
  }
  
  public synchronized Set<User> getAllUsers() throws SQLException {
	  Set<User> users = new HashSet<User>();
	  Connection con = DBManager.getInstance().getConnection();
    try {
    	con.setAutoCommit(false);
    	//get all users
    	Statement userST = con.createStatement();
    	ResultSet usersRS = userST.executeQuery("SELECT user_id,username,password,first_name,last_name,email,pictureURL FROM users;");
      	while (usersRS.next()) {
      		
      		User u = new User(	usersRS.getString("username"), 
      							usersRS.getString("password"), 
      							usersRS.getString("first_name"), 
      							usersRS.getString("last_name"), 
      							usersRS.getString("email"));  //TODO hash pass);
      		users.add(u);
      		
      		//set user's id
      		u.setUserId(usersRS.getLong("user_id"));
      		//set user's picture if there is one
      		String pictureUrl ="";
      		if((pictureUrl=usersRS.getString("pictureURL"))!=null) {
      			u.setPhotoURL(pictureUrl);
      		}
      		
      		
	    	String postsSQL = "SELECT posts_post_id FROM users_has_posts WHERE users_user_id=?";
	    	String followersSQL = "SELECT follower_id FROM users_has_followers WHERE user_id=?";
	    	String followingSQL = "SELECT user_id FROM users_has_followers WHERE follower_id=?";
	    	
	    	//get user's posts
	    	PreparedStatement postsST = con.prepareStatement(postsSQL);
	    	postsST.setLong(1, u.getUserId());
	    	ResultSet postsRS = postsST.executeQuery();
	    	while(postsRS.next()) {
	    		u.addPost(PostManager.getInstance().getPosts().get(postsRS.getLong("posts_post_id")));
	    	}
	    	
	    	
	    	//get user's followers
	    	PreparedStatement followersPS = con.prepareStatement(followersSQL);
	    	followersPS.setLong(1, u.getUserId());
	    	ResultSet followersRS = followersPS.executeQuery();
	    	while(followersRS.next()) {
	    		long followerId = followersRS.getLong("follower_id");
	    		PreparedStatement followerName = con.prepareStatement("SELECT username FROM users WHERE user_id=?");
	    		followerName.setLong(1, followerId);
	    		ResultSet rs = followerName.executeQuery();
	    		rs.next();
	    		User follower = UsersManager.getInstance().getRegisteredUsers().get(rs.getString("username"));
	    		u.addFollower(follower);
	    	}
	    	
	    	//get user's following
	    	PreparedStatement followingPS = con.prepareStatement(followingSQL);
	    	followingPS.setLong(1, u.getUserId());
	    	ResultSet followingRS = followingPS.executeQuery();
	    	while(followingRS.next()) {
	    		long followingId = followingRS.getLong("user_id");
	    		PreparedStatement followingName = con.prepareStatement("SELECT username FROM users WHERE user_id=?");
	    		followingName.setLong(1, followingId);
	    		ResultSet rs = followingName.executeQuery();
	    		rs.next();
	    		User following = UsersManager.getInstance().getRegisteredUsers().get(rs.getString("username"));
	    		u.addFollowing(following);
	    	}
	    	
	    	
      	}
    	DBManager.getInstance().getConnection().commit();;

    }
    catch (SQLException e) {
    	System.out.println("Cannot make statement." + e.getMessage());
    	con.rollback();
    } catch (InvalidInputException e) {
		e.printStackTrace();
	} 
    finally {
    	con.setAutoCommit(true);
    }
    System.out.println("Users loaded successfully");
	return Collections.unmodifiableSet(users);
  }
  
  public void saveUser(User user) {
      PreparedStatement st;
	try {
		st = DBManager.getInstance().getConnection().prepareStatement("INSERT INTO users (username,password,first_name,last_name,email) VALUES (?,?,?,?,?);",Statement.RETURN_GENERATED_KEYS);
	    st.setString(1, user.getUsername());
	    st.setString(2, user.getPassword());
	    st.setString(3, user.getFirst_name());
	    st.setString(4, user.getLast_name());
	    st.setString(5, user.getEmail());
	    st.executeUpdate();
	    ResultSet res = st.getGeneratedKeys();
		res.next();
		long id = res.getLong(1);
		user.setUserId(id);
	    System.out.println("User added successfully");
	    res.close();
	    st.close();
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}
  }
  
  public  void deleteUser(User u) {
	  PreparedStatement prepSt;
	  try {
		prepSt = DBManager.getInstance().getConnection().prepareStatement("DELETE FROM TABLE users WHERE user_id=?");
		prepSt.setLong(1, u.getUserId());
		prepSt.executeUpdate();
		System.out.println("A user successfully deleted!");
	  } catch (Exception e) {
		 System.out.println(e.getMessage());
	  }
  }
  
  
  public synchronized void updateUser(User u){
	  
	  Connection con = DBManager.getInstance().getConnection();
	  try {
		  con.setAutoCommit(false);
		  PreparedStatement prepSt;
		  prepSt = con.prepareStatement("UPDATE users SET first_name = ? WHERE user_id=?");
		  prepSt.setString(1, u.getFirst_name());
		  prepSt.setLong(2, u.getUserId());
		  prepSt.executeUpdate();
		  
		  prepSt = con.prepareStatement("UPDATE users SET last_name = ? WHERE user_id=?");
		  prepSt.setString(1, u.getLast_name());
		  prepSt.setLong(2, u.getUserId());
		  prepSt.executeUpdate();
		  
		  prepSt = con.prepareStatement("UPDATE users SET email = ? WHERE user_id=?");
		  prepSt.setString(1, u.getEmail());
		  prepSt.setLong(2, u.getUserId());
		  prepSt.executeUpdate();
		  
		  prepSt = con.prepareStatement("UPDATE users SET username = ? WHERE user_id=?");
		  prepSt.setString(1, u.getUsername());
		  prepSt.setLong(2, u.getUserId());
		  prepSt.executeUpdate();
		  
		  con.commit();
	} catch (SQLException e) {
		try {
			con.rollback();
		} catch (SQLException e1) {
			System.out.println("wtf");
		}
	} finally {
		try {
			con.setAutoCommit(true);
		} catch (SQLException e) {
			System.out.println("wtf");
		}
	}
  }

  public void updatePass(String hashPassword, User u) {
	  Connection con = DBManager.getInstance().getConnection();
	  PreparedStatement prepSt = null;
	  try {
		prepSt = con.prepareStatement("UPDATE users SET password = ? WHERE user_id=?");
		prepSt.setString(1, hashPassword);
		prepSt.setLong(2, u.getUserId());
	} catch (SQLException e) {
		System.out.println("sql pass change failed");
	}
	};
}
