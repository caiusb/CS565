package edu.illinois.codeselector.views;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

import edu.illinois.codeselector.models.Snippet;

public class ViewLabelProvider extends LabelProvider implements
		IBaseLabelProvider {

	
	@Override
	public String getText(Object element) {
		if (element instanceof Snippet){
			return ((Snippet)element).getCode();
		}
		
		return "";
	}
}
