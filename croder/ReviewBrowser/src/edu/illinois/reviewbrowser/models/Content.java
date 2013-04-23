package edu.illinois.reviewbrowser.models;

import java.util.List;

public class Content {
	private List<String> snippets;
	private List<String> texts;
	
	
	
	public String toString()
	{
		StringBuilder result= new StringBuilder();
		for(int i=0; i<snippets.size(); i++)
		{
			result.append("<code><pre>");
			result.append(snippets.get(i));
			result.append("</code></pre> <p>");
			result.append(texts.get(i));
			result.append("</p>");
		}
		return result.toString();	
	}
}
