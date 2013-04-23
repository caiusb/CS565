package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

public class Review {
	private String url;
	private String title;
	
	private List<Reply> replies;
	private List<Comment> comments;
	
	public Review(String url, String title) {
		this.url = url;
		this.title = title;
		
		replies= new ArrayList<Reply>();
		comments= new ArrayList<Comment>();
	}

	
	
	
	
	
	public void setReplies(List<Reply> replies) {
		this.replies= replies;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getURL(){
		return url;
	}
}
