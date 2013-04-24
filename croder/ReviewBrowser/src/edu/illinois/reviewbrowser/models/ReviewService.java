package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import edu.illinois.codeselector.models.snippets.Snippet;
import edu.illinois.stackexchange.WebAPI;

public class ReviewService {
	private static class Instance {
		public static final ReviewService _instance = new ReviewService();
	}

	public static ReviewService getInstance() {
		return Instance._instance;
	}

	private WebAPI stackExchange = new WebAPI();

	private User currentUser;
	private List<ReviewListener> reviewListeners;
	private ConcurrentHashMap<User, List<Review>> reviews;

	private ReviewService() {
		reviewListeners = new ArrayList<ReviewListener>();
		reviews = new ConcurrentHashMap<>();
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
		for (ReviewListener listener : reviewListeners) {
			listener.update();
		}
	}

	/**
	 * 
	 * @param title - Review title
	 * @param mainComment - Main title of the review. What it request. Review Checklists
	 * @param snippets - Code snippets annotated with explanations
	 */
	public void addReview(String title, String mainComment, List<Snippet> snippets) {
		
		Review review = new Review(title, mainComment, snippets);
		String url = stackExchange.postQuestion(title, review.formatForPost());
		review.setURL(url);

		List<Review> userReviews = reviews.get(currentUser);
		userReviews.add(review);

		notifyReviewListeners();
	}

	public List<Review> getReviews() {
		
		if(currentUser == null)
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
}
