package com.zy.web.model;

public class User {
	private int userID;
	private String userName;
	private String userPassword;
	private String userEmail;
	
	public User(int userID, String userName, String userPassword, String userEmail){
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
