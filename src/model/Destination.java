package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Destination {
	
	private long destination_id;
	private String name;
	private Location location;
	private ArrayList<Post> posts;
	
	
	public Destination(String name, Location location) throws InvalidInputException {
		if(checkString(name)) {
			this.name = name;
		}
		else {
			throw new InvalidInputException("Invalid destination name!");
		}
		this.location = location;
		this.posts = new ArrayList<>();
	}
	
	
	
	public long getDestination_id() {
		return destination_id;
	}


	public void setDestination_id(long destination_id) {
		this.destination_id = destination_id;
	}



	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public void addPost(Post p) {
		this.posts.add(p);
	}


	public String getName() {
		return name;
	}


	public Location getLocation() {
		return location;
	}


	public List<Post> getPosts() {
		return Collections.unmodifiableList(posts);
	}
	
	
	

}
