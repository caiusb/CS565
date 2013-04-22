package edu.illinois.croder.wizard;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Text;

import edu.illinois.codeselector.models.snippets.Snippet;

public class TreeClickSelectionListener implements ISelectionChangedListener {

	private Text text;
	private Text code;

	public TreeClickSelectionListener(Text text, Text code) {
		this.text = text;
		this.code = code;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		Object eventSource = event.getSource();
		if (!(eventSource instanceof TreeViewer))
			return;
		
		Snippet selectedElement = (Snippet) ((IStructuredSelection) ((TreeViewer) eventSource)
				.getSelection()).getFirstElement();
		code.setText(selectedElement.getCode());
		text.setText(selectedElement.getComment());
	}
}
