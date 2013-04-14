package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CodeSelectionPage extends WizardPage {

	protected CodeSelectionPage() {
		super("Code Selection Page");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		Label label = new Label(composite, SWT.NONE);
		
		setControl(composite);
	}

}
