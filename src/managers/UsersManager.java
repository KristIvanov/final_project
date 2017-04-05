package managers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import dbModel.UserDAO;
import model.InvalidInputException;
import model.User;

public class UsersManager {
	//concurrenthashmap because a lot of threads can use it
	  private ConcurrentHashMap<String, User> registeredUsers; //username--> user
	  private static UsersManager instance;
	  
	  private UsersManager() {
	   
		registeredUsers = new ConcurrentHashMap<>();
	   
	    try {
			for (User u : UserDAO.getInstance().getAllUsers()) {
			  registeredUsers.put(u.getUsername(), u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	  }
	  
	  public static synchronized UsersManager getInstance() {
	    if (instance == null) {
	      instance = new UsersManager();
	    }
	    return instance;
	  }
	  
	  public Map<String, User> getRegisteredUsers() {
		return Collections.unmodifiableMap(registeredUsers);
	}
	  
	  public boolean validateLogin(String username, String password) {
	    if (!registeredUsers.containsKey(username)) { //if the username doesn't exist return false
	      return false;
	    }
	    //return if user's password matches the given password 
	    return (registeredUsers.get(username)).getPassword().equals(password);
	  }
	  
	 
	  
	  public boolean validateRegistration(String username, String password, String firstName, String lastName, String email) {
				if(username == null || username.isEmpty() || registeredUsers.contains(username)) {
					return false;
				}
				if(firstName == null || firstName.isEmpty()) {
					return false;
				}
				if(lastName == null || lastName.isEmpty()) {
					return false;
				}
				if (!validateEmailAddress(email)) {
					return false;
				}
				
				if(!validatePassword(password)){
					return false;
				}
				
		  return true;
	  } 
	  
	  public void register(String username, String password, String firstName, String lastName, String email) {
	    User user;
		try {
			user = new User(username,password,firstName,lastName,email);
			 registeredUsers.put(username, user); //put user in collection
			 UserDAO.getInstance().saveUser(user); //save user in DB
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public void delete(User u) {
		  registeredUsers.remove(u);
		  UserDAO.getInstance().deleteUser(u);
	  }
	  
	  //update user's information by given parameters.. 
	  public void updateUser(User u) {
		  //some validations of values for the fields to be changed
		  UserDAO.getInstance().updateUser(u);
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
			for(Entry<String, User> user : registeredUsers.entrySet()) {
				if(user.getValue().getEmail().equals(email)) {
					return false;
				}
			}
	      String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	      java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	      java.util.regex.Matcher m = p.matcher(email);
	      return m.matches();
		}
	  
	}



