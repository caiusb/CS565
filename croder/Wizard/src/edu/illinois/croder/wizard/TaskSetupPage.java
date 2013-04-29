package edu.illinois.croder.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TaskSetupPage extends WizardPage {

	private Text descriptionField;
	private Text titleField;
	private Combo tagCombo;

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
		makeTaskDescription(composite);
		
		makeTagField(composite);

		setControl(composite);
	}

	private void makeTagField(Composite composite) {
		Composite tagComposite = new Composite(composite, SWT.NONE);
		tagComposite.setLayout(new GridLayout(2, false));
		tagComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label label = new Label(tagComposite, SWT.NONE);
		label.setText("Please select your tag");
		tagCombo = new Combo(tagComposite, SWT.READ_ONLY);
		tagCombo.setItems(new String[]{"readability", "refactoring", "performance", "optimization", "code-smell", "design", "best-practice", "multithreading"});
	}

	private void makeTaskDescription(Composite composite) {
		Composite labelComposite = new Composite(composite, SWT.NONE);
		labelComposite.setLayout(new GridLayout(1, false));
		labelComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		Label label = new Label(labelComposite, SWT.NONE);
		label.setText("Please describe your task:");

		Composite descriptionTextComposite = new Composite(labelComposite,
				SWT.NONE);
		descriptionTextComposite.setLayout(new GridLayout(1, true));
		descriptionTextComposite
				.setLayoutData(new GridData(GridData.FILL_BOTH));
		descriptionField = new Text(descriptionTextComposite, SWT.BORDER
				| SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		descriptionField.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	private void makeTitle(Composite composite) {
		Composite titleComposite = new Composite(composite, SWT.NONE);
		titleComposite.setLayout(new GridLayout(2, false));
		titleComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label label = new Label(titleComposite, SWT.NONE);
		label.setText("Title of your task:");

		Composite titleTextComposite = new Composite(titleComposite, SWT.NONE);
		titleTextComposite.setLayout(new GridLayout(1, true));
		titleTextComposite
				.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		titleField = new Text(titleTextComposite, SWT.BORDER);
		titleField.setLayoutData(new GridData(GridData.FILL_BOTH));
	}

	public String getTitle() {
		return titleField.getText();
	}

	public String getDescription() {
		return descriptionField.getText();
	}
	
	public String getTag() {
		return tagCombo.getText();
	}

}
