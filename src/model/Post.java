package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Post {
	
	private long postId;
	private String postName;
	private Category category;
	private ArrayList<Picture> pictures;
	private String description;
	private User author;
	private LocalDateTime date;
	private HashSet<User> likers;
	private ArrayList<Comment> comments;
	private String destination;
	private double longitude;
	private double latitude;
	
	
	public Post(String postName,Category category, String description, User author, LocalDateTime date,
			String destinationName, double longitude, double latitude) throws InvalidInputException {
		
		if(checkString(postName)) {
			this.postName=postName;
		}
		if(checkString(destinationName)) {
			this.destination=destinationName;
		}
		this.latitude=latitude;
		this.longitude=longitude;
		this.category=category;
		
		if(checkString(description)) {
			this.description = description;
		}
		else {
			throw new InvalidInputException("Invalid post description!");
		}
		this.author = author;
		this.date = date;
		this.pictures = new ArrayList<>();
		this.likers = new HashSet<>();
		this.comments = new ArrayList<>();
	}
	
	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public void addPicture(Picture p) {
		this.pictures.add(p);
	}
	
	public void addComment(Comment c) {
		this.comments.add(c);
	}
	
	public void addLiker(User u) {
		this.likers.add(u);
	}
	
	

	public List<Picture> getPictures() {
		return Collections.unmodifiableList(pictures);
	}

	public String getDescription() {
		return description;
	}

	public User getAuthor() {
		return author;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public Set<User> getLikers() {
		return Collections.unmodifiableSet(likers);
	}

	public List<Comment> getComments() {
		return Collections.unmodifiableList(comments);
	}

	public String getDestination() {
		return destination;
	}
	

	public String getPostName() {
		return postName;
	}

	public Category getCategory() {
		return category;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
	
	
	
	
	
}
