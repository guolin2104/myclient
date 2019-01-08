package com.gl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gl.bean.User;
import com.gl.db.DBManager;

public class UserDao {

	public static User queryUser(String userName) {
		// 获得数据库的连接对象
		Connection conn = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		// 生成SQL代码
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM user WHERE UserName=?");

		// 设置数据库的字段值
		try {
			preparedStatement = conn.prepareStatement(sql.toString());
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();

			User user = new User();
			if (resultSet.next()) {
				user.setUserName(resultSet.getString("UserName"));
				user.setPassword(resultSet.getString("Password"));
				return user;
			} else {
				return null;
			}
		} catch (SQLException ex) {
			Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		} finally {
			DBManager.closeAll(conn, preparedStatement, resultSet);
		}
	}

	public static int createUser(User user) {
		if (user == null) {
			return 1;
		}
		int result = 0;// 1代表注册成功，0代表注册失败，-1代表用户已存在
		// 先查询该用户是否存在
		User user0 = queryUser(user.getUserName());
		if (user0 != null) {
			result = -1;
		} else {
			Connection conn = DBManager.getConnection();
			PreparedStatement preparedStatement = null;
			int resultSet = 0;

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO user (UserName, Password) VALUES (?, ?)");

			try {
				preparedStatement = conn.prepareStatement(sql.toString());
				preparedStatement.setString(1, user.getUserName());
				preparedStatement.setString(2, user.getPassword());
				resultSet = preparedStatement.executeUpdate();
				if (resultSet == 1) {
					result = 1;
				} else {
					result = 0;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = 1;
			} finally {
				DBManager.closeAll(conn, preparedStatement, null);
			}
		}
		return result;
	}
}
