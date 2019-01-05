package com.gl.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * ���ݿ�����࣬�ṩ�������ݿ�Ͳ�����ӹ���
 *
 * @author Implementist
 */
public class DBManager extends HttpServlet {

    ServletConfig config;                             //����һ��ServletConfig����
    private static String username;                   //��������ݿ��û���
    private static String password;                   //��������ݿ���������
    private static String url;                        //�������ݿ�����URL
    private static Connection connection;             //��������

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);                                  //�̳и����init()����
        this.config = config;                                //��ȡ������Ϣ
        username = config.getInitParameter("DBUsername");    //��ȡ���ݿ��û���
        password = config.getInitParameter("DBPassword");    //��ȡ���ݿ���������
        url = config.getInitParameter("ConnectionURL");      //��ȡ���ݿ�����URL
    }

    /**
     * ������ݿ����Ӷ���
     *
     * @return ���ݿ����Ӷ���
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    /**
     * �ر����е����ݿ�������Դ
     *
     * @param connection Connection ����
     * @param statement Statement ��Դ
     * @param resultSet ResultSet �������
     */
    public static void closeAll(Connection connection, Statement statement,
            ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
