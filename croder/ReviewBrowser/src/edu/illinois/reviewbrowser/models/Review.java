package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.codeselector.models.snippets.Snippet;

public class Review {
	private String url;
	private String title;

	private List<Reply> replies;
	private List<Comment> comments;
	private List<Snippet> snippets;
	private StackOverflowPostFormatter formatter;
	private String mainComment;

	public Review(String title, String mainComment, List<Snippet> snippets) {
		this.title = title;
		this.mainComment = mainComment;
		this.snippets = snippets;
		this.formatter = new StackOverflowPostFormatter(this);

		replies = new ArrayList<Reply>();
		comments = new ArrayList<Comment>();
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
