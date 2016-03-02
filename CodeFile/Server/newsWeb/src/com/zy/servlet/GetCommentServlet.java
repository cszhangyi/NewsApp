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

import com.zy.web.dao.CommentDAO;
import com.zy.web.dao.NewsDAO;
import com.zy.web.model.Comment;
import com.zy.web.model.News;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GetCommentServlet extends HttpServlet {

	public GetCommentServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("请求评论");
		response.setContentType("text/html;charset=UTF-8");
		String newsID = request.getParameter("newsID");
		JSONObject jObject = new JSONObject();
		CommentDAO commentDAO;
		try
		{
			commentDAO = new CommentDAO();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			ArrayList<Comment> retList = commentDAO.getTypes(Integer.valueOf(newsID));
			HashMap<String, Object> map;
			for (Comment comment : retList)
			{
				map = new HashMap<String, Object>();
				map.put("commentID", comment.getCommentID());
				map.put("content", comment.getContent());
				map.put("createTime", comment.getCreateTime());
				map.put("userID", comment.getUserID());
				map.put("newsID", comment.getNewsID());
				
				list.add(map);
			}
			JSONObject jObject2 = new JSONObject();
			jObject2.put("totalnum", retList.size());
			jObject2.put("commentList", list);

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

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
