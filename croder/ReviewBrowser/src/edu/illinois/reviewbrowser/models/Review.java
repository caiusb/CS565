package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

public class Review {
	private String url;
	private String title;

	public Review(String url, String title) {
		this.url = url;
		this.title = title;
	}

	public List<Comment> getComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>();

		comments.add(new Comment("url", "msg"));
		comments.add(new Comment("url", "msg"));

		return comments;
	}
	
	public String getTitle(){
		return title;
	}
}
