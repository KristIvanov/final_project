package managers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dbModel.PostDAO;
import model.Category;
import model.InvalidInputException;
import model.Post;
import model.User;

public class PostManager {
	
	private static PostManager instance;
	private ConcurrentHashMap<Long,Post> allPosts; //postId-> post

	
	private PostManager() {
		allPosts = new ConcurrentHashMap<>();
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
	
	public boolean checkString(String s) {
		if(s!=null &&!s.isEmpty()) {
			return true;
		}
		return false;
	}
	public boolean validatePost(String postName, String postDescription, String destinationName) {
		if(!checkString(destinationName)) {
			return false;
		}
		if(!checkString(postDescription)) {
			return false;
		}
		if(!checkString(postName)) {
			return false;
		}
		return true;
	}
	
	public void addNewPost(String postName, String userName, String postDescription, Category category, LocalDateTime date, String destinationName, double longitude, double latitude, String pictureURL, String[] keywords, String videoURL) {
		User u = UsersManager.getInstance().getRegisteredUsers().get(userName);
		Post p;
		try {
			p = new Post(postName, category, postDescription, u, date, destinationName, longitude, latitude, pictureURL,videoURL);
			p.addHashtags(keywords);
			PostDAO.getInstance().addNewPost(p);
			long id = p.getPostId();
			allPosts.put(id, p);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println("Post not added");
		}
		
	}
	
	public void deletePost(Post p){
		allPosts.remove(p);
		PostDAO.getInstance().deletePost(p);
	}
	
	public List<Post> searchByName(String name){
		ArrayList<Post> searchResults = new ArrayList<>();
		for (Post post : allPosts.values()) {
			if (post.getDestination().contains(name)) searchResults.add(post);
		}
		return Collections.unmodifiableList(searchResults);
	}
	
	
	public List<Post> searchByTag(String name){
		ArrayList<Post> searchResults = new ArrayList<>();
		for (Post post : allPosts.values()) {
			if (post.getPostName().contains(name)) searchResults.add(post);
		}
		return Collections.unmodifiableList(searchResults);
	}
	
	
	
}
