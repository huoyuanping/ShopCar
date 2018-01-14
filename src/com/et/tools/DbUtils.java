package com.et.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
	/**
	   * 读取资源文件
	   */
		static Properties p=new Properties();
		static{
			InputStream is=DbUtils.class.getResourceAsStream("/jdbc.properties");
			try {
				p.load(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * 获取连接
		 * @return
		 * @throws Exception 
		 */
		public static Connection getConnect() {
			//jdbc连接的数据库服务器的ip 端口 数据库名
			String url=p.getProperty("url");
			//告诉jdbc使用什么数据库，不同数据的数据类型不一样
			String driverClass=p.getProperty("driverClass");
			String uname=p.getProperty("username");
			String password=p.getProperty("password");
			System.out.println(url);
			
			try {
				//需要jvm加载该类
				Class.forName(driverClass);
			} catch (ClassNotFoundException e) {
			
			}
			//登录成功
			Connection con = null;
			try {
				con = DriverManager.getConnection(url, uname, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
			
		}
	
}
