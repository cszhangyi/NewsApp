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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.zy.web.dao.CategoryDAO;
import com.zy.web.model.Category;


/**
 * @date: 2016.02.06
 * @author Y.Zhang
 *
 */

public class GetCategoriesServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5187333130405895022L;

	/**
	 * Constructor of the object.
	 */
	public GetCategoriesServlet() {
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
		JSONObject jObject = new JSONObject();
		CategoryDAO typeDAO;
		try
		{
			typeDAO = new CategoryDAO();
			ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			ArrayList<Category> retList = typeDAO.getTypes(0, 0);
			HashMap<String, Object> map;
			for (Category category : retList)
			{
				map = new HashMap<String, Object>();
				map.put("cid", category.getCategoryID());
				map.put("ctitle", category.getCategoryName());
				list.add(map);
			}
			JSONObject jObject2 = new JSONObject();
			jObject2.put("totalnum", retList.size());
			jObject2.put("categoryList", list);

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
