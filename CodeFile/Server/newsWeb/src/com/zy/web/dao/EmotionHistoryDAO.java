package com.zy.web.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.zy.web.model.User;

public class EmotionHistoryDAO {
	SqlManager manager;
	String sql = "";
	ResultSet rs;
	
	public EmotionHistoryDAO() throws IOException, ClassNotFoundException
	{
		manager = SqlManager.createInstance();
	}
	
	public void insertValue(int userID, int emotionID) throws SQLException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowTime = df.format(new Date());// new Date()为获取当前系统时间
		//sql = "INSERT INTO t_comment (nid,ptime,region,content) VALUES (?,?,?,?)";
		//sql = "insert into t_emohistory (userID, emotionID, createTime) values(?,?,?)";
		sql = "update t_emohistory set emotionID = ? ,createTime = ? where userID = ?";
		
		Object[] params = new Object[]{emotionID,nowTime,userID};
		
		manager.connectDB();
		boolean result = manager.executeUpdate(sql, params);
		manager.closeDB();
		//System.out.println(result);
	}
}
