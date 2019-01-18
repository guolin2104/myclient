package com.gl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gl.bean.User;
import com.gl.dao.UserDao;
import com.gl.util.TokenUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends BaseServlet {
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
		super.doGet(request, response);
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		super.doPost(request, response);

		try (PrintWriter out = response.getWriter()) {
			// 获得请求中传来的用户名和密码
			String sn = request.getParameter("sn");
			String userName = request.getParameter("UserName");
			String password = request.getParameter("Password");

			// 密码验证结果
			Boolean verifyResult = verifyLogin(userName, password);

			Map<String, String> params = new HashMap<String, String>();
			JSONObject jsonObject = new JSONObject();
			if (verifyResult) {
				String token = doCreateToken(userName, sn);
				params.put("Result", "success");
				params.put("token", token);
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

	private String doCreateToken(String userName, String sn) {
		String token = "";
		try {
			token = TokenUtil.createToken(userName, sn);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return token;
	}
}
