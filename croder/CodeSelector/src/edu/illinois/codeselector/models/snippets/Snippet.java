package edu.illinois.codeselector.models.snippets;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

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
	
	public String getSignature() {
		IJavaElement javaElement = getJavaElementForSnippet();
		int elementType = javaElement.getElementType();
		switch (elementType) {
		case IJavaElement.METHOD:
			IMethod method = (IMethod) javaElement;
			try {
				return method.getSignature();
			} catch (JavaModelException e) {
			}
		case IJavaElement.TYPE:
			IType type = (IType) javaElement;
			return type.getFullyQualifiedName();
		}
		return javaElement.getElementName();
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
