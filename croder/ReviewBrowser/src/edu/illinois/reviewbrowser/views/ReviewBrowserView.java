package edu.illinois.reviewbrowser.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import edu.illinois.reviewbrowser.models.Review;
import edu.illinois.reviewbrowser.models.ReviewListener;
import edu.illinois.reviewbrowser.models.ReviewService;

public class ReviewBrowserView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "edu.illinois.ReviewBrowser.views.ReviewBrowserView";

	private ListViewer reviewViewer;

	private TreeViewer commentViewer;

	public ReviewBrowserView() {
		ReviewService.getInstance().addReview(new Review("url", "name"));
		ReviewService.getInstance().addReview(new Review("url", "name"));
		ReviewService.getInstance().addReview(new Review("url", "name"));
		ReviewService.getInstance().addReview(new Review("url", "name"));
	}

	/**
	 * This is a callback that will allow us to create the reviewViewer and
	 * initialize it.
	 */
	public void createPartControl(Composite parent) {

		SashForm sash = new SashForm(parent, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		// sash.setWeights(new int[] { 1 });

		addReviewViewer(sash);
		addCommentViewer(sash);

		sash.pack();
	}

	private void addCommentViewer(SashForm sash) {
		commentViewer = new TreeViewer(sash, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		commentViewer.setContentProvider(new CommentViewerContentProvider());
		commentViewer.setLabelProvider(new LabelProvider());
	}

	private void addReviewViewer(SashForm sash) {
		reviewViewer = new ListViewer(sash, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		reviewViewer.setContentProvider(new ViewContentProvider());
		reviewViewer.setLabelProvider(new ViewLabelProvider());
		reviewViewer.setInput(ReviewService.getInstance().getReviews());

		// Create the help context id for the reviewViewer's control
		hookDoubleClickAction();

		ReviewService.getInstance().registerReviewListener(
				new ReviewListener() {
					@Override
					public void update() {
						reviewViewer.setInput(ReviewService.getInstance()
								.getReviews());
					}
				});
	}

	private void hookDoubleClickAction() {
		reviewViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = reviewViewer.getSelection();
				Review review = (Review) ((IStructuredSelection) selection)
						.getFirstElement();

				commentViewer.setInput(review.getComments());
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(reviewViewer.getControl().getShell(),
				"CodeSelector", message);
	}

	/**
	 * Passing the focus request to the reviewViewer's control.
	 */
	public void setFocus() {
		reviewViewer.getControl().setFocus();
	}
}