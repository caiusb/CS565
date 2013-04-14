package edu.illinois.codeselector.views;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.illinois.codeselector.models.Snippet;

public class ViewContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ArrayList<?>) {
			ArrayList<Snippet> snippets = ((ArrayList<Snippet>) inputElement);
			return snippets.toArray(new Snippet[snippets.size()]);
		}

		return new Snippet[0];
	}

}
