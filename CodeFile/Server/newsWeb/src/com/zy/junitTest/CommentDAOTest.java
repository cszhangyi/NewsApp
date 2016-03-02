package com.zy.junitTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.zy.web.dao.CategoryDAO;
import com.zy.web.dao.CommentDAO;
import com.zy.web.model.Category;
import com.zy.web.model.Comment;

public class CommentDAOTest {
	CommentDAO commentDAO;
	
	@Before
	public void Init() throws IOException, ClassNotFoundException
	{
		commentDAO = new CommentDAO();
	}

	@Test
	public void testSetTypes() throws SQLException
	{
		commentDAO.addComment(9001, 1, "Hello World");
	}
	
	@Test
	public void testGetTypes() throws SQLException
	{
		ArrayList<Comment> list = new ArrayList<Comment>();
		list = commentDAO.getTypes(1);
		for (Comment comment : list)
		{
			System.out.println("commentID="+comment.getCommentID()+";content="+comment.getContent()
					+";CreateTime="+comment.getCreateTime()+";NewsID="+comment.getNewsID()+";UserID:"+comment.getUserID());
		}
	}
}
