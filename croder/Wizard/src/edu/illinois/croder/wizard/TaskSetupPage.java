package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class TaskSetupPage extends WizardPage {

	protected TaskSetupPage() {
		super("Task setup page");
		setTitle("Task setup");
		setDescription("Please setup your task");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		
		setControl(composite);
	}

}
