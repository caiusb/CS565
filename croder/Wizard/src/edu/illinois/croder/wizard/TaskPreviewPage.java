package edu.illinois.croder.wizard;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.illinois.codeselector.models.SnippetService;

public class TaskPreviewPage extends WizardPage {

	protected TaskPreviewPage() {
		super("Code Selection Page");
		setTitle("Task preview");
		setDescription("Please preview the task");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label label = new Label(composite, SWT.NONE);
		label.setText("This is a preview of your task");
		
		Composite codeHolderComposite = new Composite(composite, SWT.NONE);
		codeHolderComposite.setLayout(new GridLayout(2, true));
		codeHolderComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Composite treeComposite = new Composite(codeHolderComposite, SWT.NONE);
		treeComposite.setLayout(new GridLayout(1,true));
		treeComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		TreeViewer treeViewer = new TreeViewer(treeComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new SnippetContentProvider());
		treeViewer.setLabelProvider(new SnippetLabelProvider());
		treeViewer.setInput(SnippetService.getInstance());
		
		Composite codeDetailsComposite = new Composite(codeHolderComposite, SWT.NONE);
		codeDetailsComposite.setLayout(new GridLayout(1, true));
		codeDetailsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Text text = new Text(codeDetailsComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.addModifyListener(new CommentModifyListener(treeViewer));
		
		Text code = new Text(codeDetailsComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		code.setLayoutData(new GridData(GridData.FILL_BOTH));
		code.addModifyListener(new CodeModifyListener(treeViewer));
		
		treeViewer.addSelectionChangedListener(new TreeClickSelectionListener(text,code));
		
		setControl(composite);
	}

}
