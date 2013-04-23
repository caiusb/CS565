package edu.illinois.reviewbrowser.models;

import edu.illinois.stackexchange.WebAPI;

public class User {
	private String email;
	private String pass;
	
	public User(String email, String pass){
		this.email = email;
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return pass;
	}
}
