package com.zy.web.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zy.web.model.News;

/**
 * @date Feb 4, 2016
 * @author Y.Zhang
 *
 */

public class NewsDAO {
	SqlManager manager;
	String sql = "";
	ResultSet rs;

	public NewsDAO() throws IOException, ClassNotFoundException
	{
		manager = SqlManager.createInstance();
	}

	/**
	 * 获取新闻内容
	 * @param nid 新闻编号
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<News> getNews(int nid) throws SQLException
	{
		ArrayList<News> list = new ArrayList<News>();
		sql = "SELECT * FROM t_news";
		Object[] params = new Object[]{ nid };
		manager.connectDB();
		rs = manager.executeQuery(sql, params);
		if (rs.next())
		{
			News news = new News();
			news.setNewsID(rs.getInt("newsID"));
			news.setNewsTitle(rs.getString("newsTitle"));
			news.setNewsUrl(rs.getString("newsUrl"));
			news.setBody(rs.getString("body"));
			news.setCreateTime(rs.getString("createTime"));
			news.setCategoryID(rs.getString("categoryID"));
			news.setKeywords(rs.getString("keywords"));
			news.setEmotionID(rs.getString("emotionID"));
			list.add(news);
		}
		manager.closeDB();
		return list;
	}
	
	public ArrayList<News> getNewsByNewsID(int nid) throws SQLException
	{
		ArrayList<News> list = new ArrayList<News>();
		sql = "SELECT * FROM t_news where newsID = ?";
		Object[] params = new Object[]{ nid };
		manager.connectDB();
		rs = manager.executeQueryHaveParameter(sql, params);
		if (rs.next())
		{
			News news = new News();
			news.setNewsID(rs.getInt("newsID"));
			news.setNewsTitle(rs.getString("newsTitle"));
			news.setNewsUrl(rs.getString("newsUrl"));
			news.setBody(rs.getString("body"));
			news.setCreateTime(rs.getString("createTime"));
			news.setCategoryID(rs.getString("categoryID"));
			news.setKeywords(rs.getString("keywords"));
			news.setEmotionID(rs.getString("emotionID"));
			list.add(news);
		}
		manager.closeDB();
		return list;
	}

	/**
	 * 获取指定类别的新闻列表
	 * @param tid 新闻类型
	 * @param startNid 起始编号
	 * @param count 返回数量
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<News> getSpecifyCategoryNews(int tid, int startNid, int count, int emotionValue)
			throws SQLException
	{
		ArrayList<News> list = new ArrayList<News>();
		Object[] params = null;
		//sql = "SELECT nid,cid,title,digest,source,ptime,imgsrc FROM t_news WHERE cid=? AND deleted=false ORDER BY ptime desc LIMIT ?,?";
		if(emotionValue == 2){
			sql = "select newsID,newsTitle,newsUrl,createTime,keywords from t_news where categoryID=? and emotionID=? order by createTime desc LIMIT ?,?";
			params = new Object[]{ tid, 1, startNid, count };
		}else{
			sql = "select newsID,newsTitle,newsUrl,createTime,keywords from t_news where categoryID=? order by createTime desc LIMIT ?,?";
			params = new Object[]{ tid, startNid, count };
		}
		manager.connectDB();
		rs = manager.executeQueryHaveParameter(sql, params);
		while (rs.next())
		{
			News news = new News();
			news.setNewsID(rs.getInt("newsID"));
			news.setNewsTitle(rs.getString("newsTitle"));
			news.setNewsUrl(rs.getString("newsUrl"));
			news.setCreateTime(rs.getString("createTime"));
			news.setKeywords(rs.getString("keywords"));
			System.out.println(rs.getInt("newsID"));
			list.add(news);
		}
		manager.closeDB();
		return list;
	}
}
