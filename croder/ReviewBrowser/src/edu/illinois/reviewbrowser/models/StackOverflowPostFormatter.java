package edu.illinois.reviewbrowser.models;

import java.io.Serializable;

import edu.illinois.codeselector.models.snippets.Snippet;

public class StackOverflowPostFormatter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7037123627479395734L;
	private Review review;

	public StackOverflowPostFormatter(Review review) {
		this.review = review;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		writeParagraph(result, review.getMainComment());

		for (Snippet snippet : review.getSnippets()) {
			writeCodeSnippet(result, snippet.getCode());
			writeParagraph(result, snippet.getComment());
		}

		return result.toString();
	}

	private void writeCodeSnippet(StringBuilder result, String code) {
		//result.append("<code><pre>" + code + "</code></pre>");
		result.append(code);
		result.append("\n");
	}

	private void writeParagraph(StringBuilder result, String mainComment) {
		//result.append("<p>" + mainComment + "</p>\n");
		result.append(mainComment);
	}
}
