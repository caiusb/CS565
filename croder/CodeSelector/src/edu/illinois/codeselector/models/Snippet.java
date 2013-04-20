package edu.illinois.codeselector.models;

public class Snippet {
	private Object snippet;
	
	public Snippet(Object snippet){
		this.snippet = snippet;
	}
	
	@Override
	public String toString() {
		return snippet.toString();
	}
	
	public String getCode(){
		return snippet.toString();
	}
	
}
