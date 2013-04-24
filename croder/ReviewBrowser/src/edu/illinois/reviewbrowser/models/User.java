package edu.illinois.reviewbrowser.models;


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
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User){
			User other = (User) obj;
			
			return other.email.equals(email) && other.pass.equals(pass);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		
        hash = hash * 17 + email.hashCode();
        hash = hash * 31 + pass.hashCode();
        
        return hash;
	}
	
	public static void main(String[] args) {
		User u1 = new User("unu", "doi");
		User u2 = new User("unu", "doi");
		User u3 = new User("doi", "unu");
		
		assert u1.equals(u2);
		assert u1.hashCode() == u2.hashCode();
		
		assert !u1.equals(u3);
		assert u1.hashCode() != u3.hashCode();
	}
}

