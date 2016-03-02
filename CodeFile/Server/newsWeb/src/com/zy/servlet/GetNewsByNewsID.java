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

import com.zy.web.dao.NewsDAO;
import com.zy.web.model.News;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GetNewsByNewsID extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetNewsByNewsID() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		String newsID = request.getParameter("newsID");
		JSONObject jObject = new JSONObject();
		NewsDAO typeDAO;
		
		try
		{
			typeDAO = new NewsDAO();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			ArrayList<News> retList = typeDAO.getNewsByNewsID(Integer.valueOf(newsID));
			HashMap<String, Object> map;
			for (News news : retList)
			{
				map = new HashMap<String, Object>();
				map.put("nid", news.getNewsID());
				map.put("body", news.getBody());
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

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		throw new NotImplementedException();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
