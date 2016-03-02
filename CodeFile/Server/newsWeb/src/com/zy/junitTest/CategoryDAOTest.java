package com.zy.junitTest;

/*
 * @author:zy
 * @date: Feb 3, 2016
 */

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


import com.zy.web.dao.CategoryDAO;
import com.zy.web.model.Category;

public class CategoryDAOTest {
	
	CategoryDAO categoryDAO;
	
	@Before
	public void Init() throws IOException, ClassNotFoundException
	{
		categoryDAO = new CategoryDAO();
	}
	
	@Test
	public void testGetTypes() throws SQLException
	{
		ArrayList<Category> list = new ArrayList<Category>();
		list = categoryDAO.getTypes(0, 0);
		for (Category category : list)
		{
			System.out.println("catoryID="+category.getCategoryID()+";categoryName="+category.getCategoryName());
		}
	}
	
}
