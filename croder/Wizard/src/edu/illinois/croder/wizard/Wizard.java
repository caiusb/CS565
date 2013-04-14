package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

public class Wizard extends WizardDialog {

	public Wizard(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}

}
