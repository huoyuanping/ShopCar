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
 * �������ﳵ����
 * @author Administrator
 *
 */
public class CarServlet extends HttpServlet {

	/**
	 * ��ֵ��
	 * id=����
	 * �����ĳ����Ʒ�� ���session�в����ڸ�id�ļ�¼ id=1
	 * ���ڸ�id�ļ�¼ id=id+1
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//String remove=request.getParameter("remove");
		String id=request.getParameter("id");
		HttpSession hs=request.getSession();
		//���ﳵ�ļӼ�
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
		//�б���ʾ
		out.print("<a href='QueryGoodServlet'>��������</a>");
		out.println("�������빺�ﳵ����Ʒ����:");
		out.print("<div style=\"margin:0 auto; width:1100px;\">");
		out.println("<table  border=\"1\">");
		out.print("<tr>");
		out.print("<th>��ƷͼƬ</th>");
		out.print("<th>��ƷID</th>");
		out.print("<th>��Ʒ����</th>");
		out.print("<th>��Ʒ����</th>");
		out.print("<th>��Ʒλ��</th>");	
		out.print("<th>��Ʒ����</th>");
		//out.print("<th>��Ʒ����</th>");
		out.print("<th>��Ʒ����</th>");
		out.println("</tr>");
		//����session
		Enumeration et=hs.getAttributeNames();
		while(et.hasMoreElements()){//�ҵ���һ��ֵ
			String ID=et.nextElement().toString();//�ҵ�Ԫ��
			String value=hs.getAttribute(ID).toString();
			String sql = "select * from goods where id="+ID;
			Connection con = QueryGoodServlet.con;
			try {
				//Ԥ�����sql���
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
