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
		//设置字符集
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//获取参数
		String goodName=request.getParameter("goodName");
		//跳回当前查询页面
		out.print("<center>");
		out.print("<form action=\"QueryGoodServlet\" method=get>");
		out.print("<input type=\"text\" name=\"goodName\" value=\"\"+goodName+\"\"/>");
		out.print("<input type=\"submit\" value=\"搜索\"/>");
		out.println("</form>");
		out.print("</center>");
		out.print("<div style=\"margin:0 auto; width:1100px;\">");
		out.println("<table border=\"1\" >");
		out.print("<tr>");
		out.print("<th>商品图片</th>");
		out.print("<th>商品名称</th>");
		out.print("<th width:500px>商品分类</th>");
		out.print("<th>商品单价</th>");
		out.print("<th>商品位置</th>");
		//out.print("<th>商品描述</th>");
		out.print("<th>操作</th>");
		out.println("</tr>");
		
	
		if(goodName==null){//当goodName=null时替换成""
			goodName="";
		}
		//查询的逻辑
		String sql="select * from goods where name like '%"+goodName+"%'";
		try {
			PreparedStatement pst=con.prepareStatement(sql);
			ResultSet rt=pst.executeQuery();
			while(rt.next()){
				//通过列名抓取
				String id=rt.getString("ID");
				String imagePath=rt.getString("IMAGEPATH");//商品图片
				String name=rt.getString("NAME");//商品名称
				String model=rt.getString("MODEL");//商品分类
				double price=rt.getDouble("PRICE");//商品单价
				String stock=rt.getString("STOCK");//商品位置
				//String descp=rt.getString("DESCP");//商品描述
				out.print("<tr>");
				out.println("<td>");
				out.println("<img src=\""+request.getContextPath()+imagePath+"\">");
				out.println("</td>");
				out.println("<td>"+name+"</td>");
				out.println("<td>"+model+"</td>");
				out.println("<td>"+price+"</td>");
				out.println("<td>"+stock+"</td>");
				//out.println("<td>"+descp+"</td>");
				out.println("<td><input type='button' value='加入购物车' onclick=\"window.location='CarServlet?id="+id+"';\"></td>");
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
	 * 获取连接
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
