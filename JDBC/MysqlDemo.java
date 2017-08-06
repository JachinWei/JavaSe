package com.wyg.mysqldemo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

public class UseDemo {
	private final static String URL = "jdbc:mysql://localhost:3306/wyg_test";
	private final static String USER = "root";
	private final static String PASSWORD = "root";

	public static void main(String[] args) {
			try {
				new UseDemo().insertText();
				new UseDemo().queryText();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
	// 关闭资源
	public static void closeSource(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		if (null != rs) {
			rs.close();
		}
		if (null != stmt) {
			stmt.close();
		}
		if (null != conn) {
			conn.close();
		}
	}

	//插入文本数据
	public void insertText() throws SQLException, FileNotFoundException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			String sql = "insert into tp_file(id, file_info) values(?,?)";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			stmt.setInt(1,1);
			File file = new File("C:\\Users\\Administrator\\Desktop\\1.txt");
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			stmt.setCharacterStream(2, buffer);
			stmt.execute();
		} finally {
			UseDemo.closeSource(conn, stmt, rs);
		}
	}
	
	//查询文本数据
	public void queryText() throws SQLException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			String sql = "select file_info from  tp_file where id=1";
			stmt = (PreparedStatement) conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				Reader r=rs.getCharacterStream(1);
				BufferedReader buffer = new BufferedReader(r);
				String temp = "";
				File f = new File("C:\\Users\\Administrator\\Desktop\\2.txt");
				FileWriter fw = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(fw);
				while((temp=buffer.readLine())!=null){
					System.out.println(temp);
					bw.write(temp+"\n");
					bw.flush();
				}
				bw.close();
				fw.close();
				buffer.close();
			}
		} finally {
			UseDemo.closeSource(conn, stmt, rs);
		}
	}



	// 执行sql语句
	public void execSQL(String sql) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			stmt = conn.createStatement();
			stmt.execute(sql);
		} finally {
			UseDemo.closeSource(conn, stmt, rs);
		}
	}

	// 删除数据
	public void delete() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			stmt = conn.createStatement();
			String sql = "delete from tp_admin where id=1";
			stmt.execute(sql);
		} finally {
			UseDemo.closeSource(conn, stmt, rs);
		}
	}

	// 更新数据
	public void update() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			stmt = conn.createStatement();
			String sql = "update tp_admin set password = '17511' where id = 1";
			stmt.execute(sql);
		} finally {
			UseDemo.closeSource(conn, stmt, rs);
		}
	}

	// 插入数据
	public void insert() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			stmt = conn.createStatement();
			String sql = "insert into tp_admin(id, username, password) values(3, 'Ceci', '19950228')";
			stmt.execute(sql);
		} finally {
			UseDemo.closeSource(conn, stmt, rs);
		}
	}

	public void useExample() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接
			conn = DriverManager.getConnection(UseDemo.URL, UseDemo.USER, UseDemo.PASSWORD);
			// 创建执行句柄
			stmt = conn.createStatement();
			// 执行sql语句
			rs = stmt.executeQuery("select * from tp_admin");
			// 处理执行结果
			while (rs.next()) {
				System.out.println(
						"id:" + rs.getInt(3) + "\tusername:" + rs.getString(2) + "\tpassword:" + rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			try {
				UseDemo.closeSource(conn, stmt, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	static {
		// 三种注册驱动, 注册只需要一次即可，重复注册没啥意义
		// System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		// Class.forName("com.mysql.jdbc.Driver");
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}