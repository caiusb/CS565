package edu.illinois.codeselector.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import edu.illinois.codeselector.models.snippets.AbstractSnippet;

public class ViewLabelProvider extends LabelProvider implements
		IBaseLabelProvider {

	
	@Override
	public String getText(Object element) {
		if (element instanceof AbstractSnippet){
			return ((AbstractSnippet)element).getCode();
		}
		
		return "";
	}
}
