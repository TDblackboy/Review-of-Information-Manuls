package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	/*
	 * 链接数据库：word_analysis
	 */
	public static Connection getConnection() {

		// 加载驱动
		String drive = "com.mysql.jdbc.Driver";
		try {
			Class.forName(drive).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("加载驱动失败");
		}

		// 获取连接对象
		String url = "jdbc:mysql://localhost:3306/word_analysis?characterEncoding=utf-8";
		String user = "root";
		String pass = "sunyu";

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("无法获取连接对象");
		}
		System.out.println("链接到MySQL：");
		return connection;
	}

	// 关闭资源
	public static void close(Connection connection) {
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("连接对象关闭失败");
		}
	}

	public static void close(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null)
				preparedStatement.close();
		} catch (SQLException e) {
			System.out.println("语句传输对象关闭失败");
		}
	}

	public static void close(ResultSet resultSet) {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			System.out.println("结果集对象关闭失败");
		}
	}
}
