package com.gl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gl.bean.User;
import com.gl.dao.UserDao;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置相应内容类型
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			// 获得请求中传来的用户名和密码
			String userName = request.getParameter("UserName");
			String password = request.getParameter("Password");

			// 密码验证结果
			Boolean verifyResult = verifyLogin(userName, password);

			Map<String, String> params = new HashMap<String, String>();
			JSONObject jsonObject = new JSONObject();
			if (verifyResult) {
				params.put("Result", "success");
			} else {
				params.put("Result", "failed");
			}
			jsonObject.put("params", params);
			out.write(jsonObject.toString());
		}
	}

	private Boolean verifyLogin(String userName, String password) {
		User user = UserDao.queryUser(userName);

		return null != user && password.equals(user.getPassword());
	}
}
