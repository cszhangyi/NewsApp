package com.zy.web.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zy.web.model.User;

public class UserDAO {
	SqlManager manager;
	String sql = "";
	ResultSet rs;
	
	public UserDAO() throws IOException, ClassNotFoundException
	{
		manager = SqlManager.createInstance();
	}
	
	public ArrayList<User> getTypes(int userID) throws SQLException
	{
		ArrayList<User> list = new ArrayList<User>();
		sql = "select * from t_user where userID = ?";
		Object[] params = new Object[]{userID};
		manager.connectDB();
		rs = manager.executeQueryHaveParameter(sql, params);
		while(rs.next())
		{
			User user = new User(rs.getInt("userID"), rs.getString("userName"), rs.getString("userPassword"), rs.getString("userEmail"));
			list.add(user);
		}
		manager.closeDB();
		return list;
	}
}
