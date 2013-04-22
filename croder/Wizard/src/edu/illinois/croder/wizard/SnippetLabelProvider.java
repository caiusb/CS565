package edu.illinois.croder.wizard;

import org.eclipse.jface.viewers.LabelProvider;

import edu.illinois.codeselector.models.snippets.AbstractSnippet;

public class SnippetLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (!(element instanceof AbstractSnippet))
			return "<error>";
		
		AbstractSnippet snippet = (AbstractSnippet) element;
		return snippet.getSignature();
	}
}
