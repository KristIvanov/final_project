package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import managers.UsersManager;

public class User {
	
	private long userId;
	private String username;
	private String password;
	private String email;
	private String first_name;
	private String last_name;
	private String photoURL;
	private HashSet<User> followers;
	private HashSet<User> following;
	private ArrayList<Post> posts;
	private HashSet<Destination> favorites;
	private HashSet<Destination> wishList;
	
	public User(String username, String password, String email, String first_name, String last_name) throws InvalidInputException {
		if(checkString(username)) {
			this.username = username;
		}
		else {
			throw new InvalidInputException("Invalid username!");
		}
		if(password!=null && !password.isEmpty()) {//cannot use regex validation here because the hashed pass would be different
			//this.password = password;
			this.password = password;
		}
		else {
			throw new InvalidInputException("Invalid password!");
		}
		if(validateEmailAddress(email)) {
			this.email = email;
		}
		else {
			throw new InvalidInputException("Invalid email!");
		}
		if(checkString(first_name)) {
			this.first_name = first_name;
		}
		else {
			throw new InvalidInputException("Invalid firstName!");
		}
		if(checkString(last_name)) {
			this.last_name = last_name;
		}
		else {
			throw new InvalidInputException("Invalid lastName!");
		}
		followers = new HashSet<>();
		following = new HashSet<>();
		posts = new ArrayList<>();
		favorites = new HashSet<>();
		wishList = new HashSet<>();
	}
	
	
	
	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public Set<User> getFollowers() {
		return Collections.unmodifiableSet(followers);
	}

	public Set<User> getFollowing() {
		return Collections.unmodifiableSet(following);
	}

	public List<Post> getPosts() {
		return Collections.unmodifiableList(posts);
	}

	public Set<Destination> getFavorites() {
		return Collections.unmodifiableSet(favorites);
	}

	public Set<Destination> getWishList() {
		return Collections.unmodifiableSet(wishList);
	}
	
	public void addFollower(User follower) {
		this.followers.add(follower);
	}
	
	public void addFollowing(User following) {
		this.following.add(following);
	}
	
	public void addFavorite(Destination d) {
		this.favorites.add(d);
	}
	
	public void addToWishList(Destination d) {
		this.wishList.add(d);
	}
	
	public void addPost(Post p) {
		this.posts.add(p);
	}

	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean validatePassword (String password) {
		boolean upperCaseLetter = false; 
		boolean lowerCaseLetter = false;
		boolean digit = false;
		if(password.length() < 5 || password.length() > 15) { 
			return false;
		}
		for (int i = 0; i < password.length(); i++) {
			if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') {
				upperCaseLetter = true;
				continue;
			}
			if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z' ) { 
				lowerCaseLetter = true;
				continue;
			}
			if (password.charAt(i) >= '0' && password.charAt(i) <= '9'){ 
				digit = true;
				continue;
			}
			if (upperCaseLetter && lowerCaseLetter && digit) { 
				break;
			}
		}
		if (upperCaseLetter && lowerCaseLetter && digit) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean validateEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}

	
	
	
	
	
	

}
