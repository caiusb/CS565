package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TaskSetupPage extends WizardPage {

	protected TaskSetupPage() {
		super("Task setup page");
		setTitle("Task setup");
		setDescription("Please setup your task");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		makeTitle(composite);
		
		setControl(composite);
	}

	private void makeTitle(Composite composite) {
		Composite titleComposite = new Composite(composite,SWT.NONE);
		titleComposite.setLayout(new GridLayout(2, false));
		titleComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label label = new Label(titleComposite, SWT.NONE);
		label.setText("Title of your task:");
		
		Composite titleTextComposite = new Composite(titleComposite, SWT.NONE);
		titleTextComposite.setLayout(new GridLayout(1, true));
		titleTextComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Text titleField = new Text(titleTextComposite, SWT.BORDER);
		titleField.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

}
