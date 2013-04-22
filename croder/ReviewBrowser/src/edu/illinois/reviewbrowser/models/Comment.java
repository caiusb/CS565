package edu.illinois.reviewbrowser.models;

import java.util.List;

public class Comment {
	private String url;
	private String message;
	private Comment parent;

	public Comment(String url, String message) {
		this(url, message, null);
	}

	public Comment(String url, String message, Comment parent) {
		this.url = url;
		this.message = message;
		this.parent = parent;
	}

	public void reply(String reply) {
		// the parent of the reply will the parent of this
	}
	
	public List<Comment> getReplies(){
		// the parent of the reply will the parent of this
		return null;
	}
}
