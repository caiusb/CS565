package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.codeselector.models.snippets.Snippet;

public class Review {
	private String url;
	private String title;

	private List<Reply> replies;
	private List<Comment> comments;
	
	private String mainComment;
	private List<Snippet> snippets;
	
	private StackOverflowPostFormatter formatter;

	public Review(String title, String mainComment, List<Snippet> snippets) {
		this.title = title;
		this.mainComment = mainComment;
		this.snippets = snippets;
		this.formatter = new StackOverflowPostFormatter(this);

		replies = new ArrayList<>();
		comments = new ArrayList<>();
	}

	public String formatForPost() {
		return formatter.toString();
	}

	public String getURL() {
		return url;
	}

	public void setURL(String url) {
		this.url = url;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	public List<Reply> getReplies() {
		return replies;
	}

	public String getTitle() {
		return title;
	}

	public String getMainComment() {
		return mainComment;
	}

	public List<Snippet> getSnippets() {
		return snippets;
	}

}
