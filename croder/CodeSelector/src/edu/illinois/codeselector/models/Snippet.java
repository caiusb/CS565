package edu.illinois.codeselector.models;

import org.eclipse.jdt.core.IJavaElement;

import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public class Snippet {
	private Object snippet;
	
	protected Snippet(Object snippet){
		this.snippet = snippet;
	}
	
	public String getCode(){
		return snippet.toString();
	}
	
	public static Snippet constructSnippetForTarget(Object snippetTarget) throws UnknownSelectionException{
		if (snippetTarget instanceof IJavaElement){
			return new JavaElementSnippet((IJavaElement)snippetTarget);
		}
		else if (snippetTarget instanceof String){
			return new Snippet(snippetTarget);
		}
		else throw new UnknownSelectionException("could not adapt snippet for: " + snippetTarget.getClass().getName());
	}
	
}
