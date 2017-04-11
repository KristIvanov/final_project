package managers;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dbModel.PostDAO;
import model.Category;
import model.InvalidInputException;
import model.Post;
import model.User;

public class PostManager {
	
	private static PostManager instance=new PostManager();
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
			p = new Post(postName, category, postDescription, u, date, destinationName, longitude, latitude, pictureURL,videoURL,new ArrayList<>());
			p.addHashtags(keywords);
			PostDAO.getInstance().addNewPost(p);
			long id = p.getPostId();
			allPosts.put(id, p);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			System.out.println("Post not added");
		}
		
	}
	public void likePost(Post p,User u) {
		p.like(u);
		PostDAO.getInstance().likePost(p,u);
	}
	
	public void deletePost(Post p){
		allPosts.remove(p);
		PostDAO.getInstance().deletePost(p);
	}
	
	public List<Post> searchByDestination(String name){
		ArrayList<Post> searchResults = new ArrayList<>();
		for (Post post : allPosts.values()) {
			if (post.getDestination().contains(name)) {
				searchResults.add(post);
			}
		}
		return Collections.unmodifiableList(searchResults);
	}
	
	
	public List<Post> searchByTags(String[] words){
		ArrayList<Post> searchResults = new ArrayList<>();
		for (Post post : allPosts.values()) {
			boolean containsAll = true;
			for (int i = 0; i < words.length; i++) {
				if (!post.getHashtags().contains(words[i])) {
					containsAll = false;
					break;
				}
			}
			if (containsAll) searchResults.add(post);
		}
		return Collections.unmodifiableList(searchResults);
	}
	
	public List<Post> orderByLikes(List<Post> posts){
		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post o1, Post o2) {
				if (o1.getLikes() == o2.getLikes()) {
					return o1.hashCode() - o2.hashCode();
				}
				return o1.getLikes() - o2.getLikes();
			}
		});
		return posts;
	}
	
	public List<Post> orderByDate(List<Post> posts){ //TODO ascending or descending ? 
		Collections.sort(posts, new Comparator<Post>() {

			@Override
			public int compare(Post o1, Post o2) {
				if (o1.getDate().isEqual(o2.getDate())){
					return o1.hashCode() - o2.hashCode();
				}
				else if (o1.getDate().isAfter(o2.getDate())) {
					return -1;
				}
				else {
					return 1;
				}
			}
		});
		return posts;
	}
	//sort posts by likes and date - create treesets??
	
	
	
	
	
	
}
