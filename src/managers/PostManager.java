package managers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import dbModel.PostDAO;
import model.Post;

public class PostManager {
	
	private static PostManager instance;
	private static HashMap<Long,Post> allPosts; //postId-> post

	
	private PostManager() {
		allPosts = new HashMap<>();
		try {
			for(Post p : PostDAO.getInstance().getAllPosts()) {
				allPosts.put(p.getPostId(), p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized PostManager getInstance() {
	    if (instance == null)
	      instance = new PostManager();
	    return instance;
	  }

	
	public Map<Long,Post> getPosts() {
		return Collections.unmodifiableMap(allPosts);
	}
	
}
