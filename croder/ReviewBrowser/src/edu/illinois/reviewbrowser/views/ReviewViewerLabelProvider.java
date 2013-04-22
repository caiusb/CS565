package edu.illinois.reviewbrowser.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import edu.illinois.reviewbrowser.models.Review;

public class ReviewViewerLabelProvider extends LabelProvider implements
		IBaseLabelProvider {

	@Override
	public String getText(Object element) {
		if(element instanceof Review){
			return ((Review)element).getTitle();
		}
		
		return super.getText(element);
	}
}
