package model;

public class Picture {
	
	private long picture_id;
	private String Url;

	public Picture(String url) throws InvalidInputException {
		if(checkString(url)) {
			Url = url;
		}
		else {
			throw new InvalidInputException("Invalid picture url!");
		}
	}
	
	
	
	public long getPicture_id() {
		return picture_id;
	}



	public void setPicture_id(long picture_id) {
		this.picture_id = picture_id;
	}



	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
	
	

}
