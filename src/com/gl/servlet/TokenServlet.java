package com.gl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.Claim;
import com.gl.util.TokenUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class TokenServlet
 */
@WebServlet("/TokenServlet")
public class TokenServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TokenServlet() {
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
		// TODO Auto-generated method stub

		super.doPost(request, response);

		try (PrintWriter out = response.getWriter()) {
			String token = request.getParameter("token");

			int result = doVerifyToken(token);

			Map<String, String> params = new HashMap<String, String>();
			JSONObject jsonObject = new JSONObject();
			if (result == 0) {// 成功
				params.put("Result", "success");
			} else if (result == -1) {// 过期
				params.put("Result", "expires");
			} else {
				params.put("Result", "failed");
			}
			jsonObject.put("params", params);
			out.write(jsonObject.toString());
		}
	}

	/**
	 * @param token
	 *            用户token
	 * @return 0——成功，-1——过期，1——不存在
	 */
	private int doVerifyToken(String token) {
		int result = 0;
		try {
			Map<String, Claim> map = TokenUtil.verifyToken(token);
			if (map == null || map.size() == 0) {
				result = -1;
			}
			// System.out.println(map.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = -1;
		}

		return result;
	}
}
