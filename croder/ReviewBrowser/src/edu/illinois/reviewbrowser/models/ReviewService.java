package edu.illinois.reviewbrowser.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.text.TextSelection;

import edu.illinois.codeselector.models.snippets.Snippet;
import edu.illinois.stackexchange.DumbApi;
import edu.illinois.stackexchange.WebAPI;
import edu.illinois.stackexchange.WebApiInterface;

public class ReviewService {
	private static final String PERSIST_FILE = "reviewPersistence";

	private static class Instance {
		public static final ReviewService _instance = new ReviewService();
	}

	public static ReviewService getInstance() {
		return Instance._instance;
	}

	private WebApiInterface stackExchange = new DumbApi();

	private User currentUser;
	private List<ReviewListener> reviewListeners;
	private ConcurrentHashMap<User, List<Review>> reviews;

	private ReviewService() {
		reviewListeners = new ArrayList<ReviewListener>();

		if (!readFromFile()) {
			reviews = new ConcurrentHashMap<>();
		}
	}

	public void authenticate(String email, String password) {
		this.currentUser = new User(email, password);
		reviews.putIfAbsent(currentUser, new ArrayList<Review>());

		stackExchange.login(email, password);
	}

	public void registerReviewListener(ReviewListener listener) {
		this.reviewListeners.add(listener);
	}

	private void notifyReviewListeners() {
		saveToFile();

		for (ReviewListener listener : reviewListeners) {
			listener.update();
		}
	}

	/**
	 * 
	 * @param title
	 *            - Review title
	 * @param mainComment
	 *            - Main title of the review. What it request. Review Checklists
	 * @param snippets
	 *            - Code snippets annotated with explanations
	 */
	public void addReview(String title, String mainComment,
			List<Snippet> snippets) {

		Review review = new Review(title, mainComment, snippets);
		String url = stackExchange.postQuestion(title, review.formatForPost());
		review.setURL(url);

		List<Review> userReviews = reviews.get(currentUser);
		userReviews.add(review);
		createMakers(review);

		notifyReviewListeners();
	}

	private void createMakers(Review review) {
		List<Snippet> snippets = review.getSnippets();
		for (Snippet snippet : snippets) {
			IJavaElement javaElement = snippet.getJavaElementForSnippet();
			try {
				IResource resource = javaElement.getCorrespondingResource();
				IMarker marker = resource.createMarker("edu.illinois.croder.snippetMarker");
				marker.setAttribute(IMarker.MESSAGE, review.getTitle());
				marker.setAttribute(IMarker.CHAR_START, snippet.getOffset());
				marker.setAttribute(IMarker.CHAR_END, snippet.getOffset()+snippet.getLength());
			} catch (CoreException e) {
			}
		}
	}

	public List<Review> getReviews() {

		if (currentUser == null)
			return new ArrayList<Review>();

		return reviews.get(currentUser);
	}

	public void updateReplies() {
		for (Review r : getReviews()) {
			String[] repliesText = stackExchange.parseQuestion(r.getURL());

			List<Reply> replies = new ArrayList<Reply>();

			for (String s : repliesText) {
				replies.add(new Reply(s));
			}

			r.setReplies(replies);
		}
	}

	private void saveToFile() {
		try (FileOutputStream fout = new FileOutputStream(PERSIST_FILE);
				ObjectOutputStream oos = new ObjectOutputStream(fout);) {

			oos.writeObject(reviews);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean readFromFile() {
		try (FileInputStream fin = new FileInputStream(PERSIST_FILE);
				ObjectInputStream ois = new ObjectInputStream(fin)) {
			reviews = (ConcurrentHashMap<User, List<Review>>) ois.readObject();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
