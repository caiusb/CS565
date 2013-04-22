package edu.illinois.croder.wizard;

import org.eclipse.jface.viewers.LabelProvider;

import edu.illinois.codeselector.models.snippets.Snippet;

public class SnippetLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (!(element instanceof Snippet))
			return "<error>";
		
		Snippet snippet = (Snippet) element;
		return snippet.getSignature();
	}
}
