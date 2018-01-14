package com.et.shop;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 描述购物车的类
 * @author Administrator
 *
 */
public class CarServlet extends HttpServlet {

	/**
	 * 键值对
	 * id=次数
	 * 点击了某个商品后 如果session中不存在该id的记录 id=1
	 * 存在该id的记录 id=id+1
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//String remove=request.getParameter("remove");
		String id=request.getParameter("id");
		HttpSession hs=request.getSession();
		//购物车的加减
	/*	if(remove!=null){
			int i=(Integer) hs.getAttribute(remove)-1;
			hs.setAttribute(remove,i);
			if(i<=0){
				hs.removeAttribute(remove);
			}
		}else {
			String id=request.getParameter("id");
			if(hs.getAttribute(id)==null){
				hs.setAttribute(id, 1);
			}else{
				int i=(Integer) hs.getAttribute(id)+1;
				hs.setAttribute(id,i);
			}
		}*/
		
		if(hs.getAttribute(id)==null){
			hs.setAttribute(id, 1);
		}else{
			// flag=add  minus   enter
			String flag=request.getParameter("flag");
			if("add".equals(flag)){
				hs.setAttribute(id,(Integer)hs.getAttribute(id)+1);
			}else if("remove".equals(flag)){
				int i=(Integer) hs.getAttribute(id)-1;
				hs.setAttribute(id,i);
				if(i<=0){
					hs.removeAttribute(id);
				}
			}else if("enter".equals(flag)){
				String num=request.getParameter("num");
				hs.setAttribute(id,Integer.parseInt(num));
			}else{
				hs.setAttribute(id,(Integer)hs.getAttribute(id)+1);
			}
		
		}
		//列表显示
		out.print("<a href='QueryGoodServlet'>继续购物</a>");
		out.println("您所加入购物车的商品如下:");
		out.print("<div style=\"margin:0 auto; width:1100px;\">");
		out.println("<table  border=\"1\">");
		out.print("<tr>");
		out.print("<th>商品图片</th>");
		out.print("<th>商品ID</th>");
		out.print("<th>商品名称</th>");
		out.print("<th>商品分类</th>");
		out.print("<th>商品位置</th>");	
		out.print("<th>商品单价</th>");
		//out.print("<th>商品描述</th>");
		out.print("<th>商品数量</th>");
		out.println("</tr>");
		//遍历session
		Enumeration et=hs.getAttributeNames();
		while(et.hasMoreElements()){//找到下一个值
			String ID=et.nextElement().toString();//找到元素
			String value=hs.getAttribute(ID).toString();
			String sql = "select * from goods where id="+ID;
			Connection con = QueryGoodServlet.con;
			try {
				//预编译的sql语句
				PreparedStatement ps = con.prepareStatement(sql);
				
				ResultSet rs = ps.executeQuery();
				rs.next();
				out.print("<tr>");
				out.print("<td><img src='"+request.getContextPath()+rs.getString("IMAGEPATH")+"'></td>");
				out.print("<td>"+ID+"</td>");
				out.print("<td>"+rs.getString("NAME")+"</td>");
				out.print("<td>"+rs.getString("MODEL")+"</td>");
				out.print("<td>"+rs.getString("STOCK")+"</td>");
				out.print("<td>"+rs.getDouble("PRICE")+"</td>");
				//out.print("<td>"+rs.getString("DESCP")+"</td>");
				out.print("<td><input type='button' value='+' onclick=\"window.location='CarServlet?id="+ID+"&flag=add'\">" +
						"<input id='"+ID+"' style=width:50px; type='text' value='"+value+"' onkeydown=\"if(event.keyCode == 13){window.location='CarServlet?id="+ID+"&flag=enter&num='+document.getElementById('"+ID+"').value}\">"+
						"<input type='button' value='-' onclick=\"window.location='CarServlet?id="+ID+"&flag=remove'\">");
				out.print("</td>");	
				out.print("<td>");
				out.print("</td>");
				out.println("</tr>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		out.println("</table>");
		out.println("</div>");
		out.flush();
		out.close();
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}
