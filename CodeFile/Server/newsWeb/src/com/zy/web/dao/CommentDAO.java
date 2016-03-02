package com.zy.web.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.zy.web.model.Category;
import com.zy.web.model.Comment;

public class CommentDAO {
	SqlManager manager;
	String sql = "";
	ResultSet rs;
	
	public CommentDAO() throws IOException, ClassNotFoundException
	{
		manager = SqlManager.createInstance();
	}
	
	public ArrayList<Comment> getNumber(int newsID) throws SQLException
	{
		ArrayList<Comment> list = new ArrayList<Comment>();
		sql = "select * from t_comment";
		Object[] params = new Object[]{newsID};
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		while(rs.next())
		{
			Comment comment = new Comment();
			comment.setCommentID(rs.getInt("commentID"));
			comment.setContent(rs.getString("content"));
			comment.setCreateTime(rs.getString("createTime"));
			comment.setNewsID(rs.getInt("newsID"));
			comment.setUserID(rs.getInt("userID"));
			list.add(comment);
		}
		manager.closeDB();
		return list;
	}

	public ArrayList<Comment> getTypes(int newsID) throws SQLException
	{
		ArrayList<Comment> list = new ArrayList<Comment>();
		sql = "select * from t_comment where newsID = ?";
		Object[] params = new Object[]{newsID};
		manager.connectDB();
		rs = manager.executeQueryHaveParameter(sql, params);
		while(rs.next())
		{
			Comment comment = new Comment();
			comment.setCommentID(rs.getInt("commentID"));
			comment.setContent(rs.getString("content"));
			comment.setCreateTime(rs.getString("createTime"));
			comment.setNewsID(rs.getInt("newsID"));
			comment.setUserID(rs.getInt("userID"));
			list.add(comment);
		}
		manager.closeDB();
		return list;
	}
	
	public void addComment(int uid,int nid,String content) throws SQLException
	{
		int commentNum = getNumber(nid).size();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String nowTime = df.format(new Date());// new Date()为获取当前系统时间
		sql = "INSERT INTO t_comment (commentID,content,createTime,userID,newsID) VALUES (?,?,?,?,?)";
		Object[] params = new Object[] { 3000 + commentNum + 1, content, nowTime, uid, nid };
		manager.connectDB();
		manager.executeUpdate(sql, params);
		manager.closeDB();
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
