package edu.illinois.reviewbrowser.models;

import edu.illinois.codeselector.models.snippets.Snippet;

public class StackOverflowPostFormatter {
	private Review review;

	public StackOverflowPostFormatter(Review review) {
		this.review = review;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();

		writeParagraph(result, review.getMainComment());

		for (Snippet snippet : review.getSnippets()) {
			writeParagraph(result, snippet.getComment());
			writeCodeSnippet(result, snippet.getCode());
		}

		return result.toString();
	}

	private void writeCodeSnippet(StringBuilder result, String code) {
		result.append("<code><pre>" + code + "</code></pre>");
	}

	private void writeParagraph(StringBuilder result, String mainComment) {
		result.append("<p>" + mainComment + "</p>");
	}
}
