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

import com.zy.util.TextUtility;
import com.zy.web.dao.CommentDAO;
import com.zy.web.model.Comment;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class AddCommentServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AddCommentServlet() {
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
		String newsID = request.getParameter("newsID");
		String userID = request.getParameter("userID");
		String content = request.getParameter("content");
		content = TextUtility.toUTF8(content);
		System.out.println("content:"+content);
		JSONObject jObject = new JSONObject();
		CommentDAO commentDAO;
		try
		{
			commentDAO = new CommentDAO();
			commentDAO.addComment(Integer.valueOf(userID), Integer.valueOf(newsID), content);

			jObject.put("ret", 0);
			jObject.put("msg", "ok");
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				jObject.put("ret", 1);
				jObject.put("msg", e.getMessage());
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
