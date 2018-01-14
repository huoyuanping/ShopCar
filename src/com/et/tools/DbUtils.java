package com.et.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtils {
	/**
	   * ��ȡ��Դ�ļ�
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
		 * ��ȡ����
		 * @return
		 * @throws Exception 
		 */
		public static Connection getConnect() {
			//jdbc���ӵ����ݿ��������ip �˿� ���ݿ���
			String url=p.getProperty("url");
			//����jdbcʹ��ʲô���ݿ⣬��ͬ���ݵ��������Ͳ�һ��
			String driverClass=p.getProperty("driverClass");
			String uname=p.getProperty("username");
			String password=p.getProperty("password");
			System.out.println(url);
			
			try {
				//��Ҫjvm���ظ���
				Class.forName(driverClass);
			} catch (ClassNotFoundException e) {
			
			}
			//��¼�ɹ�
			Connection con = null;
			try {
				con = DriverManager.getConnection(url, uname, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
			
		}
	
}
