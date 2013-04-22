package edu.illinois.reviewbrowser.views;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.illinois.reviewbrowser.models.Review;

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
		if (inputElement instanceof List<?>) {
			List<Review> reviews = ((List<Review>) inputElement);
			return reviews.toArray(new Review[reviews.size()]);
		}

		return new Review[0];
	}
}
