package edu.illinois.reviewbrowser.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import edu.illinois.reviewbrowser.models.Comment;

public class CommentViewerLabelProvider extends LabelProvider implements
		IBaseLabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof Comment){
			return ((Comment)element).getContents();
		}
		return super.getText(element);
	}
}
