package edu.illinois.reviewbrowser.models;

import java.io.Serializable;
import java.util.List;

public class Reply implements Serializable{
	private static final long serialVersionUID = 8228535704322840566L;
	private String text;
	private List<Comment> comments;

	public Reply(String text) {

		this.text = text;
	}
	
	public String getText(){
		return text;
	}
}
