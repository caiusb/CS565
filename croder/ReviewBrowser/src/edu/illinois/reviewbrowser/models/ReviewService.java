package edu.illinois.reviewbrowser.models;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.stackexchange.WebAPI;

public class ReviewService {
	private static class Instance {
		public static final ReviewService _instance = new ReviewService("semih.okur@gmail.com", "Ker32nel");
	}

	public static ReviewService getInstance() {
		return Instance._instance;
	}
	
	private WebAPI stackExchange = new WebAPI();
	
	private String email;
	private String pass;
	private List<ReviewListener> reviewListeners;
	private ArrayList<Review> reviews;

	private ReviewService(String email, String pass) {
		reviewListeners = new ArrayList<ReviewListener>();
		reviews = new ArrayList<Review>();
		
		this.email= email;
		this.pass =pass;
	}

	public void registerReviewListener(ReviewListener listener) {
		this.reviewListeners.add(listener);
	}

	private void notifyReviewListeners() {
		for (ReviewListener listener : reviewListeners) {
			listener.update();
		}
	}
	
	
	public void addReview(String title, Content content){
		String url = stackExchange.postQuestion(title, content.toString());
		reviews.add(new Review(url,title));
		notifyReviewListeners();

	}


	public List<Review> getReviews() {
		return reviews;
	}
	
	public void updateReplies(){
		for (Review r : reviews)
		{
			String [] repliesText= stackExchange.parseQuestion(r.getURL());
			
			List<Reply> replies= new ArrayList<Reply>();
			for(String s: repliesText)
			{
				replies.add(new Reply(s));
			}
			r.setReplies(replies);
		}
	}
}
