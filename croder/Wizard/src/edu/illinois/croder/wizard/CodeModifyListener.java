package edu.illinois.croder.wizard;

import org.eclipse.jface.viewers.TreeViewer;

import edu.illinois.codeselector.models.snippets.Snippet;

public class CodeModifyListener extends PreviewModifyListener {

	public CodeModifyListener(TreeViewer treeViewer) {
		super(treeViewer);
	}

	@Override
	protected void setProperty(String content, Snippet selected) {
	}

}
