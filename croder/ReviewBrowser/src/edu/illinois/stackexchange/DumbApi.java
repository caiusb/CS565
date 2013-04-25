package edu.illinois.stackexchange;

public class DumbApi implements WebApiInterface {

	@Override
	public String[] parseQuestion(String url) {
		return new String[]{""};
	}

	@Override
	public String postQuestion(String title, String content) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void login(String email, String pass) {
		// TODO Auto-generated method stub

	}

}
