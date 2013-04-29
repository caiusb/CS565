package edu.illinois.stackexchange;

import java.util.List;

public interface WebApiInterface {

	public String[] parseQuestion(String url);

	public String postQuestion(String title, String content, List<String> tags);

	public void login(String email, String pass);

}