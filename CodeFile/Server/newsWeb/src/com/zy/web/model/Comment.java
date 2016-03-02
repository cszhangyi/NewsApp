package com.zy.web.model;

import java.util.Date;

public class Comment {
	private int commentID;
	private String content;
	private String createTime;
	private int userID;
	private int newsID;
	
    public Comment(){
		
	}
	
	public Comment(int commentID, String content, String createTime, int userID,
			int newsID) {
		super();
		this.commentID = commentID;
		this.content = content;
		this.createTime = createTime;
		this.userID = userID;
		this.newsID = newsID;
	}
	public int getCommentID() {
		return commentID;
	}
	public void setCommentID(int commentID) {
		this.commentID = commentID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getNewsID() {
		return newsID;
	}
	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}

}
