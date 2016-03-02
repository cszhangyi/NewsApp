package com.zy.junitTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.zy.web.dao.NewsDAO;
import com.zy.web.model.News;

/**
 * @date Feb 4, 2016
 * @author Y.Zhang
 *
 */

public class NewsDAOTest
{
	NewsDAO newsDAO;
	
	@Before
	public void init() throws IOException, ClassNotFoundException
	{
		newsDAO = new NewsDAO();
	}
	
	@Test
	public void testGetNews() throws SQLException
	{
		ArrayList<News> list = new ArrayList<News>();
		list = newsDAO.getNewsByNewsID(2);
		for (News newsDAO : list)
		{
			System.out.println("newsID="+newsDAO.getNewsID()+";newsTitle="+newsDAO.getNewsTitle()+";newsCreateTime="+newsDAO.getCreateTime());
			System.out.println("newsBody = " + newsDAO.getBody());
		}
	}
	
	/*@Test
	public void testGetSpecifyNews() throws SQLException
	{
		ArrayList<News> list = new ArrayList<News>();
		list = newsDAO.getSpecifyCategoryNews(1, 0, 3, 2);
		for (News newsDAO : list)
		{
			System.out.println("newsID="+newsDAO.getNewsID()+";newsTitle="+newsDAO.getNewsTitle()+";getNewsUrl="+newsDAO.getNewsUrl()+";newsCreateTime="+newsDAO.getCreateTime()+";newsKeywords="+newsDAO.getKeywords());
		}
	}*/

}
