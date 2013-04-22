package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

public class ReviewService {
	private static class Instance {
		public static final ReviewService _instance = new ReviewService();
	}

	public static ReviewService getInstance() {
		return Instance._instance;
	}

	private List<ReviewListener> reviewListeners;
	private ArrayList<Review> reviews;

	private ReviewService() {
		reviewListeners = new ArrayList<ReviewListener>();
		reviews = new ArrayList<Review>();
	}

	public void registerReviewListener(ReviewListener listener) {
		this.reviewListeners.add(listener);
	}

	private void notifyReviewListeners() {
		for (ReviewListener listener : reviewListeners) {
			listener.update();
		}
	}

	public void addReview(Review r) {
		reviews.add(r);
		notifyReviewListeners();
	}

	public List<Review> getReviews() {
		return reviews;
	}
}
