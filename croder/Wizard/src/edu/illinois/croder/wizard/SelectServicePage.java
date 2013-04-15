package edu.illinois.croder.wizard;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class SelectServicePage extends WizardPage {

	protected SelectServicePage() {
		super("Select Service");
		setTitle("Select service");
		setDescription("Please select the service you would like to use for this task");
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new org.eclipse.swt.layout.GridLayout(1, true));
		composite.setLayoutData(new GridData());
		
		List<Button> servicesButtons = new ArrayList<Button>();
		
		Button button = new Button(composite, SWT.RADIO);
		button.setText("eDesk");
		servicesButtons.add(button);
		
		button = new Button(composite, SWT.RADIO);
		button.setText("Stack Overflow");
		servicesButtons.add(button);
		
		button = new Button(composite, SWT.RADIO);
		button.setText("Some other service");
		servicesButtons.add(button);
		
		setControl(composite);
	}

}
