package edu.illinois.croder.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import edu.illinois.codeselector.models.snippets.Snippet;

public abstract class AbstractPreviewModifyListener implements ModifyListener {

	private TreeViewer treeViewer;

	public AbstractPreviewModifyListener(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}

	@Override
	public void modifyText(ModifyEvent e) {
		if (!(e.getSource() instanceof Text))
			return;

		Text text = (Text) e.getSource();
		String content = text.getText();

		Snippet selected = ((Snippet) ((IStructuredSelection) treeViewer.getSelection())
				.getFirstElement());
		
		setProperty(content, selected);
	}

	protected abstract void setProperty(String content, Snippet selected);
}
