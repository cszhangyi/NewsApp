package com.zy.web.dao;

/*
 * @author:zy
 * @date: Feb 3, 2016
 */

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.zy.web.model.Category;


public class CategoryDAO{
	SqlManager manager;
	String sql = "";
	ResultSet rs;
	
	public CategoryDAO() throws IOException, ClassNotFoundException
	{
		manager = SqlManager.createInstance();
	}
	
	/**
	 * ��ȡ��������
	 * @param startTid ��ʼ���ͱ��
	 * @param count ����
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getTypes(int startTid,int count) throws SQLException
	{
		ArrayList<Category> list = new ArrayList<Category>();
		sql = "select categoryID,categoryName from news_category order by categoryID";
		Object[] params = new Object[]{startTid,count};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while(rs.next())
		{
			Category category = new Category(rs.getInt("categoryID"), rs.getString("categoryName"));
			list.add(category);
		}
		manager.closeDB();
		return list;
	}
	
	public void add(Category category)
	{
		throw new NotImplementedException();
	}
	
	public void update(Category category)
	{
		throw new NotImplementedException();
	}
	
	public void delete(int tid)
	{
		throw new NotImplementedException();
	}
	
	public ArrayList<Category> getAllTypes()
	{
		throw new NotImplementedException();
	}
}

