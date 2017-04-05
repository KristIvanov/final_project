package model;

public class Category {

	private long category_id;
	private String name;
	private String description;
	
	
	public Category(String name, String description) throws InvalidInputException {
		if(checkString(name)) {
			this.name = name;
		}
		else {
			throw new InvalidInputException("Invalid category name!");
		}
		if(checkString(description)) {
			this.description = description;
		}
		else {
			throw new InvalidInputException("Invalid category description!");
		}
	}

	

	public long getCategory_id() {
		return category_id;
	}



	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}



	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}



	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
}
