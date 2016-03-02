package com.zy.web.model;

/**
 * @author Y.Zhang
 * @date Feb 3, 2016
 *
 */

public class Category {
	private int categoryID;
	private String categoryName;
	
	
	public Category(int categoryID, String categoryName){
		this.categoryID = categoryID;
		this.categoryName = categoryName;
	}
	
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	
	

}
