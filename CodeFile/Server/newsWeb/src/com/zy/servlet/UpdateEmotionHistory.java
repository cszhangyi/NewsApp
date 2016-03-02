package com.zy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.zy.web.dao.CategoryDAO;
import com.zy.web.dao.EmotionHistoryDAO;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class UpdateEmotionHistory extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UpdateEmotionHistory() {
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
		String userID = request.getParameter("userID");
		String emotionValue = request.getParameter("emotionValue");
		JSONObject jObject = new JSONObject();
		CategoryDAO typeDAO;
		try {
			EmotionHistoryDAO emotionHistoryDAO = new EmotionHistoryDAO();
			emotionHistoryDAO.insertValue(Integer.valueOf(userID), Integer.valueOf(emotionValue));
			jObject.put("ret", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				jObject.put("ret", 1);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
