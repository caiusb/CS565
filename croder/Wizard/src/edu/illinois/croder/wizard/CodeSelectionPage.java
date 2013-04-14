package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CodeSelectionPage extends WizardPage {

	protected CodeSelectionPage() {
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
		
		Composite textComposite = new Composite(composite, SWT.NONE);
		textComposite.setLayout(new GridLayout(1, true));
		textComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		Text text = new Text(textComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		text.setText("Here you will have the description and the code," +
				"hopefully in some nice format.");
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		setControl(composite);
	}

}
