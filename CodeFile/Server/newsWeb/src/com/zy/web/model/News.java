package com.zy.web.model;

/**
 * 
 * @author Y.Zhang
 * @date: Feb 4, 2016
 */

public class News {
	private int newsID;
	private String newsTitle;
	private String newsUrl;
	private String body;
	private String createTime;
	private String categoryID;
	private String keywords;
	private String emotionID;
	
	public News(){
		
	}
	
	public News(int newsID, String newsTitle, String newsUrl, String body, String createTime, String categoryID, String keywords, String emotionID){
		this.newsID = newsID;
		this.newsTitle = newsTitle;
		this.newsUrl = newsUrl;
		this.body = body;
		this.createTime = createTime;
		this.categoryID = categoryID;
		this.keywords = keywords;
		this.emotionID = emotionID;
	}
	
	public int getNewsID() {
		return newsID;
	}
	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getEmotionID() {
		return emotionID;
	}
	public void setEmotionID(String emotionID) {
		this.emotionID = emotionID;
	}
	
	

}
