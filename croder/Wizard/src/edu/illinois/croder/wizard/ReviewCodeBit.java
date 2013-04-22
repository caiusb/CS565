package edu.illinois.croder.wizard;

import edu.illinois.codeselector.models.snippets.Snippet;

public class ReviewCodeBit {
	
	private Snippet snippet;
	private String comment;

	public ReviewCodeBit(Snippet snippet, String comment){
		this.snippet = snippet;
		this.comment = comment;
	}
	
	public Snippet getSnippet() {
		return snippet;
	}
	
	public String getComment() {
		return comment;
	}
}
