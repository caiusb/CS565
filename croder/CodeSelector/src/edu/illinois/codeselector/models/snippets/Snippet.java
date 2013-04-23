package edu.illinois.codeselector.models.snippets;

import java.io.Serializable;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;

import edu.illinois.codeselector.views.exceptions.UnknownSelectionException;

public abstract class Snippet implements Serializable{
	private static final long serialVersionUID = -1932192898282339156L;

	private transient IJavaElement javaElementForSnippet;
	private String userComment;
	private String code;
	private int offset;
	private int length;
	
	protected Snippet(IJavaElement javaElementForSnippet){
		this(javaElementForSnippet, "");
	}

	protected Snippet(IJavaElement javaElementForSnippet, String userComment, int offset, int length){
		this(javaElementForSnippet, userComment);
	}

	protected Snippet(IJavaElement javaElementForSnippet, String userComment){
		this.javaElementForSnippet = javaElementForSnippet;
		this.userComment = userComment;
	}
	
	
	//TODO transform getCode to return a list of code snippets. Packages for example return several classes
	public String getCode(){
		if (code == null){
			code = computeCode();
		}
		
		return code;
	}
	
	public void setCode(String newCode){
		this.code = newCode;
	}
	
	protected abstract String computeCode();
	
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

	public static Snippet constructSnippetForTarget(Object snippetTarget, ICompilationUnit activeICU, ISelection selection) throws UnknownSelectionException{
		if (snippetTarget instanceof IJavaElement){
			return new JavaElementSnippet((IJavaElement)snippetTarget);
		}
		else if (snippetTarget instanceof String){
			StringSnippet snippet = new StringSnippet((String) snippetTarget, activeICU);
			TextSelection textSelection = (TextSelection)selection;
			snippet.setOffset(textSelection.getOffset());
			snippet.setLength(textSelection.getLength());
			return snippet;
		}
		else throw new UnknownSelectionException("could not adapt snippet for: " + snippetTarget.getClass().getName());
	}
	
	public void setComment(String comment) {
		this.userComment = comment;
	}
	
	public String getComment() {
		return userComment;
	}
	
	protected void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getOffset() {
		return offset;
	}
	
	protected void setLength(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
}
