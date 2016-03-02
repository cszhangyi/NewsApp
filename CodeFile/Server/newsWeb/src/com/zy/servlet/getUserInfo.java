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
import com.zy.web.dao.UserDAO;
import com.zy.web.model.News;
import com.zy.web.model.User;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class getUserInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -759833827651502272L;

	/**
	 * Constructor of the object.
	 */
	public getUserInfo() {
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("登陆请求！");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		JSONObject jObject = new JSONObject();
		UserDAO typeDAO;
		

		try {
			typeDAO = new UserDAO();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			ArrayList<User> retList = typeDAO.getTypes(Integer.valueOf(userID));
			HashMap<String, Object> map;
			for (User user : retList) {
				map = new HashMap<String, Object>();
				if (user.getUserPassword().equals(userPassword)) {
					jObject.put("ret", 0);
					break;
				} else {
					jObject.put("ret", 1);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				jObject.put("ret", 1);
			} catch (JSONException ex) {
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
