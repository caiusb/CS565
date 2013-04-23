package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.Wizard;

public class SendCodeWizard extends Wizard {

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addPages() {
		addPage(new TaskSetupPage());
		addPage(new CodePreviewPage());
		addPage(new SelectServicePage());
	}
}
