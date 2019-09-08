package com.solidice.springbootrsocketserver.users;

/**
 * @author Kevin
 * @since 9/8/2019
 */
public class User {
	private String email;
	private String username;
	private int id;

	public User() {}

	public User(String email, String username, int id) {
		this.email = email;
		this.username = username;
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
