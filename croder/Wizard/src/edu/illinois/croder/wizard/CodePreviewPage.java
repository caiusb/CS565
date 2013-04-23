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

public class CodePreviewPage extends WizardPage {

	protected CodePreviewPage() {
		super("Code Selection Page");
		setTitle("Code preview");
		setDescription("Please preview the code you are about to submit");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		makeCodeArea(composite);
		
		setControl(composite);
	}

	private void makeCodeArea(Composite composite) {
		Composite codeHolderComposite = new Composite(composite, SWT.NONE);
		codeHolderComposite.setLayout(new GridLayout(2, true));
		codeHolderComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		TreeViewer treeViewer = makeTree(codeHolderComposite);
		
		Composite codeDetailsComposite = new Composite(codeHolderComposite, SWT.NONE);
		codeDetailsComposite.setLayout(new GridLayout(1, true));
		codeDetailsComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Text text = makeCommentTextField(treeViewer, codeDetailsComposite);
		Text code = makeCodeTextField(treeViewer, codeDetailsComposite);
		
		treeViewer.addSelectionChangedListener(new TreeClickSelectionListener(text,code));
	}

	private Text makeCodeTextField(TreeViewer treeViewer, Composite codeDetailsComposite) {
		Text code = new Text(codeDetailsComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		code.setLayoutData(new GridData(GridData.FILL_BOTH));
		code.addModifyListener(new CodeModifyListener(treeViewer));
		return code;
	}

	private Text makeCommentTextField(TreeViewer treeViewer, Composite codeDetailsComposite) {
		Text text = new Text(codeDetailsComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.addModifyListener(new CommentModifyListener(treeViewer));
		return text;
	}

	private TreeViewer makeTree(Composite codeHolderComposite) {
		Composite treeComposite = new Composite(codeHolderComposite, SWT.NONE);
		treeComposite.setLayout(new GridLayout(1,true));
		treeComposite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		TreeViewer treeViewer = new TreeViewer(treeComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider(new SnippetContentProvider());
		treeViewer.setLabelProvider(new SnippetLabelProvider());
		treeViewer.setInput(SnippetService.getInstance());
		treeViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		return treeViewer;
	}
}
