package edu.illinois.codeselector.models;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;

import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public abstract class Snippet {
	private IJavaElement javaElementForSnippet;
	
	protected Snippet(IJavaElement javaElementForSnippet){
		this.javaElementForSnippet = javaElementForSnippet;
	}
	
	//TODO transform getCode to return a list of code snippets. Packages for example return several classes
	public abstract String getCode();
	
	public IJavaElement getJavaElementForSnippet(){
		return javaElementForSnippet;
	}
	
	public static Snippet constructSnippetForTarget(Object snippetTarget, ICompilationUnit activeICU) throws UnknownSelectionException{
		if (snippetTarget instanceof IJavaElement){
			return new JavaElementSnippet((IJavaElement)snippetTarget);
		}
		else if (snippetTarget instanceof String){
			return new StringSnippet((String) snippetTarget, activeICU);
		}
		else throw new UnknownSelectionException("could not adapt snippet for: " + snippetTarget.getClass().getName());
	}
	
}
