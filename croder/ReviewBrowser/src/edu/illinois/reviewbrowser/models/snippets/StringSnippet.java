package edu.illinois.reviewbrowser.models.snippets;

import org.eclipse.jdt.core.ICompilationUnit;

public class StringSnippet extends AbstractSnippet {

	private String codeSnippet;

	protected StringSnippet(String codeSnippet, ICompilationUnit activeICU) {
		super(activeICU);
		this.codeSnippet = codeSnippet;
	}
	
	@Override
	public String getCode() {
		return codeSnippet;
	}
}
