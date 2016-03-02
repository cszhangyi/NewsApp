package com.zy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.zy.web.dao.CategoryDAO;
import com.zy.web.dao.NewsDAO;
import com.zy.web.model.Category;
import com.zy.web.model.News;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class getSpecifyCategoryNews extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3480138947534952476L;

	/**
	 * Constructor of the object.
	 */
	public getSpecifyCategoryNews() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String categoryID = request.getParameter("categoryID");
		String startnid = request.getParameter("startnid");
		String count = request.getParameter("count");
		String emotionValue = request.getParameter("emotionValue");
		JSONObject jObject = new JSONObject();
		NewsDAO typeDAO;
		
		try
		{
			typeDAO = new NewsDAO();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			ArrayList<News> retList = typeDAO.getSpecifyCategoryNews(Integer.parseInt(categoryID), Integer.parseInt(startnid), Integer.parseInt(count), Integer.parseInt(emotionValue));
			HashMap<String, Object> map;
			for (News news : retList)
			{
				map = new HashMap<String, Object>();
				map.put("nid", news.getNewsID());
				map.put("ntitle", news.getNewsTitle());
				map.put("nurl", news.getNewsUrl());
				map.put("ncreateTime", news.getCreateTime());
				map.put("nkeywords", news.getKeywords());
				list.add(map);
			}
			JSONObject jObject2 = new JSONObject();
			jObject2.put("totalnum", retList.size());
			jObject2.put("newsList", list);

			jObject.put("ret", 0);
			jObject.put("msg", "ok");
			jObject.put("data", jObject2);
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				jObject.put("ret", 1);
				jObject.put("msg", e.getMessage());
				jObject.put("data", "");
			} catch (JSONException ex)
			{
				ex.printStackTrace();
			}
		}
		PrintWriter out = response.getWriter();
		out.println(jObject);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		throw new NotImplementedException();
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
