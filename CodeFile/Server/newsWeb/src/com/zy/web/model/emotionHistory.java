package com.zy.web.model;

import java.sql.Date;

public class emotionHistory {
	private int userID;
	private int emotionID;
	private String createTime;
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getEmotionID() {
		return emotionID;
	}
	public void setEmotionID(int emotionID) {
		this.emotionID = emotionID;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
