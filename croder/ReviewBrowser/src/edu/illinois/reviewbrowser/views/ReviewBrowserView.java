package edu.illinois.reviewbrowser.views;

import java.util.ArrayList;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import edu.illinois.reviewbrowser.models.Reply;
import edu.illinois.reviewbrowser.models.Review;
import edu.illinois.reviewbrowser.models.ReviewListener;
import edu.illinois.reviewbrowser.models.ReviewService;

public class ReviewBrowserView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "edu.illinois.ReviewBrowser.views.ReviewBrowserView";

	private ListViewer reviewViewer;

	private TreeViewer replyViewer;

	private StackLayout stackLayout;

	private SashForm sash;

	private Composite login;

	private Composite replyParent;

	public ReviewBrowserView() {
		/*ReviewService.getInstance().addReview("title", new Content());
		ReviewService.getInstance().addReview("title", new Content());
		ReviewService.getInstance().addReview("title", new Content());
		ReviewService.getInstance().addReview("title", new Content());*/
	}

	/**
	 * This is a callback that will allow us to create the reviewViewer and
	 * initialize it.
	 */
	public void createPartControl(final Composite parent) {

		parent.setLayoutData(new GridData(GridData.FILL_BOTH));
        stackLayout = new StackLayout();
        parent.setLayout(stackLayout);
		
        // the login
        login = new Composite(parent, SWT.NONE);
        login.setLayoutData(new GridData(GridData.FILL_BOTH));
        login.setLayout(new GridLayout());
        
        Label userNamelabel = new Label(login, SWT.NULL);
        userNamelabel.setText("User Name: ");
        
        final Text emailText = new Text(login, SWT.BORDER);
        emailText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Label passwordLavel = new Label(login, SWT.None);
        passwordLavel.setText("Password: ");
        
        final Text passWordText = new Text(login, SWT.PASSWORD | SWT.BORDER);
        passWordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        Button loginButton = new Button(login, SWT.PUSH);
        loginButton.setText("Login");
        
        loginButton.addListener(SWT.Selection, new Listener(){
        	
			@Override
			public void handleEvent(Event event) {
				ReviewService.getInstance().authenticate(emailText.getText().trim(), passWordText.getText().trim());
				stackLayout.topControl = sash;
				parent.layout();
				
				refreshReviewViewer();
			}
        });
        
        // the sash with reviews and replies
        addReviewCommentSash(parent);
        
		stackLayout.topControl = login;
		parent.layout();
	}

	private void addReviewCommentSash(Composite parent) {
		sash = new SashForm(parent, SWT.HORIZONTAL);
        sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        addReviewViewer(sash);
        addCommentViewer(sash);
        sash.setWeights(new int[] { 30, 70});
        sash.pack();
	}

	private void addCommentViewer(SashForm sash) {
		
		//this will hold the browsers for each comment
		replyParent = new Composite(sash, SWT.H_SCROLL | SWT.V_SCROLL);
		replyParent.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		GridLayout layout = new GridLayout();
		replyParent.setLayout(layout);
	}

	private void addReviewViewer(SashForm sash) {
		reviewViewer = new ListViewer(sash, SWT.BORDER | SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
		reviewViewer.setContentProvider(new ReviewViewerContentProvider());
		reviewViewer.setLabelProvider(new ReviewViewerLabelProvider());
		refreshReviewViewer();

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

	private void refreshReviewViewer() {
		reviewViewer.setInput(ReviewService.getInstance().getReviews());
	}

	private void hookDoubleClickAction() {
		reviewViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = reviewViewer.getSelection();
				Review review = (Review) ((IStructuredSelection) selection)
						.getFirstElement();
				
				ReviewService.getInstance().updateReplies();
				
				showRepliesForReview(review);
			}

			private void showRepliesForReview(Review review) {
				
				//kill previous kids
				for (Control child : replyParent.getChildren()) {
					child.dispose();
				}
				
				for (Reply reply : review.getReplies()) {
					Browser b = new Browser(replyParent, SWT.BORDER);
					GridData layoutData = new GridData(GridData.FILL_HORIZONTAL);
					layoutData.heightHint = 200;
					b.setLayoutData(layoutData);
					
					b.setJavascriptEnabled(true);

					b.setText(reply.getText());
				}
				
				replyParent.layout(true);
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