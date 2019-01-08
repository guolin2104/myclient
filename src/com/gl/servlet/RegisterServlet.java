package com.gl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gl.bean.User;
import com.gl.dao.UserDao;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");

		try (PrintWriter out = response.getWriter()) {
			String userName = "bbb";// request.getParameter("UserName");
			String password = "aaa";// request.getParameter("Password");

			int result = doRegister(userName, password);

			Map<String, String> params = new HashMap<>();
			JSONObject jsonObject = new JSONObject();
			if (result == 0) {// 注册成功
				params.put("Result", "success");
			} else if (result == -1) {// 用户已存在
				params.put("Result", "exists");
			} else {// 注册失败
				params.put("Result", "failed");
			}
			jsonObject.put("params", params);
			out.write(jsonObject.toString());
		}
	}

	private int doRegister(String userName, String password) {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);

		return UserDao.createUser(user);
	}
}
