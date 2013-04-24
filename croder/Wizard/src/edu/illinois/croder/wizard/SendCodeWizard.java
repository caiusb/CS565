package edu.illinois.croder.wizard;

import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import edu.illinois.codeselector.models.SnippetService;
import edu.illinois.codeselector.models.snippets.Snippet;
import edu.illinois.reviewbrowser.models.ReviewService;

public class SendCodeWizard extends Wizard {

	private TaskSetupPage taskSetupPage;

	@Override
	public boolean performFinish() {
		
		String title = taskSetupPage.getTitle();
		String description = taskSetupPage.getDescription();
		List<Snippet> snippets = SnippetService.getInstance().getSnippets();
		
		ReviewService.getInstance().addReview(title, description, snippets);
		
		return true;
	}

	@Override
	public void addPages() {
		taskSetupPage = new TaskSetupPage();
		addPage(taskSetupPage);
		addPage(new CodePreviewPage());
		addPage(new SelectServicePage());
	}
}
