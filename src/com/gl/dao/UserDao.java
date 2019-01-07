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
}
