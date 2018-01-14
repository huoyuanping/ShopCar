package com.et.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.et.tools.DbUtils;
import com.sun.java.swing.plaf.windows.resources.windows;


public class QueryGoodServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//�����ַ���
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//��ȡ����
		String goodName=request.getParameter("goodName");
		//���ص�ǰ��ѯҳ��
		out.print("<center>");
		out.print("<form action=\"QueryGoodServlet\" method=get>");
		out.print("<input type=\"text\" name=\"goodName\" value=\"\"+goodName+\"\"/>");
		out.print("<input type=\"submit\" value=\"����\"/>");
		out.println("</form>");
		out.print("</center>");
		out.print("<div style=\"margin:0 auto; width:1100px;\">");
		out.println("<table border=\"1\" >");
		out.print("<tr>");
		out.print("<th>��ƷͼƬ</th>");
		out.print("<th>��Ʒ����</th>");
		out.print("<th width:500px>��Ʒ����</th>");
		out.print("<th>��Ʒ����</th>");
		out.print("<th>��Ʒλ��</th>");
		//out.print("<th>��Ʒ����</th>");
		out.print("<th>����</th>");
		out.println("</tr>");
		
	
		if(goodName==null){//��goodName=nullʱ�滻��""
			goodName="";
		}
		//��ѯ���߼�
		String sql="select * from goods where name like '%"+goodName+"%'";
		try {
			PreparedStatement pst=con.prepareStatement(sql);
			ResultSet rt=pst.executeQuery();
			while(rt.next()){
				//ͨ������ץȡ
				String id=rt.getString("ID");
				String imagePath=rt.getString("IMAGEPATH");//��ƷͼƬ
				String name=rt.getString("NAME");//��Ʒ����
				String model=rt.getString("MODEL");//��Ʒ����
				double price=rt.getDouble("PRICE");//��Ʒ����
				String stock=rt.getString("STOCK");//��Ʒλ��
				//String descp=rt.getString("DESCP");//��Ʒ����
				out.print("<tr>");
				out.println("<td>");
				out.println("<img src=\""+request.getContextPath()+imagePath+"\">");
				out.println("</td>");
				out.println("<td>"+name+"</td>");
				out.println("<td>"+model+"</td>");
				out.println("<td>"+price+"</td>");
				out.println("<td>"+stock+"</td>");
				//out.println("<td>"+descp+"</td>");
				out.println("<td><input type='button' value='���빺�ﳵ' onclick=\"window.location='CarServlet?id="+id+"';\"></td>");
				out.println("</tr>");
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.println("</table>");
		out.print("</div>");
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}
	
	/**
	 * ��ȡ����
	 */
	public static Connection con=null;
	@Override
	public void init() throws ServletException {
		con=DbUtils.getConnect();
	}
	
	@Override
	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
