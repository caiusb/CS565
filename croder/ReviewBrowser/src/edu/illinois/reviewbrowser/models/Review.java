package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

public class Review {
	private String url;
	private String name;

	public Review(String url, String name) {
		this.url = url;
		this.name = name;
	}

	public List<Comment> getComments() {
		ArrayList<Comment> comments = new ArrayList<Comment>();

		comments.add(new Comment("url", "msg"));
		comments.add(new Comment("url", "msg"));

		return comments;
	}
}
