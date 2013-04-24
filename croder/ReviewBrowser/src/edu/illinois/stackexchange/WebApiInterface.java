package edu.illinois.stackexchange;

public interface WebApiInterface {

	public String[] parseQuestion(String url);

	public String postQuestion(String title, String content);

	public void login(String email, String pass);

}