package com.zy.junitTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.zy.web.dao.UserDAO;
import com.zy.web.model.User;

public class UserDAOTest {
	
	UserDAO userDAO;
	@Before
	public void Init() throws IOException, ClassNotFoundException
	{
		userDAO = new UserDAO();
	}
	
	@Test
	public void testGetTypes() throws SQLException
	{
		ArrayList<User> list = new ArrayList<User>();
		list = userDAO.getTypes(9001);
		for (User user : list)
		{
			System.out.println("userID="+user.getUserID()+";userName="+user.getUserName()+";userPassword="+user.getUserPassword()+";userEmail="+user.getUserEmail());
		}
	}

}
