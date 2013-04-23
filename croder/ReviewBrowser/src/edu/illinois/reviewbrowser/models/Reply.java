package edu.illinois.reviewbrowser.models;

import java.util.List;

public class Reply {
	private String text;
	private List<Comment> comments;
	
	
	public Reply(String text)
	{
		
		this.text=text;
	}
}
