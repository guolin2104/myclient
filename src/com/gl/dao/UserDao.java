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
		// ������ݿ�����Ӷ���
		Connection conn = DBManager.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		// ����SQL����
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM user WHERE UserName=?");

		// �������ݿ���ֶ�ֵ
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
		int result = 0;// 1����ע��ɹ���0����ע��ʧ�ܣ�-1�����û��Ѵ���
		// �Ȳ�ѯ���û��Ƿ����
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
