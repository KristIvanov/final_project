package model;

public class Comment {
	
	private long comment_id;
	private User author;
	private String text;
	private Post post;
	
	public Comment(User author, String text,Post post) throws InvalidInputException {
		this.author = author;
		this.post=post;
		if(checkString(text)) {
			this.text = text;
		}
		else {
			throw new InvalidInputException("Invalid comment text!");
		}
	}
	
	
	
	public Post getPost() {
		return post;
	}

public long getComment_id() {
		return comment_id;
	}


	public void setComment_id(long comment_id) {
		this.comment_id = comment_id;
	}



	public User getAuthor() {
		return author;
	}

	public String getText() {
		return text;
	}

	private boolean checkString(String name) {
		if (name != null && !name.isEmpty()) {
			return true;
		}
		return false;
	}
	
	

}
