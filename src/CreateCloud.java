package mypack;

import java.io.*;
import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

import db.MyConnection;

public class CreateCloud extends HttpServlet
{
	 PreparedStatement ps= null;
     Connection con= null;
	 Statement st=null;
	 ResultSet rs=null;
	 String path=null;
	
	
   public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
   {
	   String ip = request.getParameter("cip").trim();

	   String status = request.getParameter("cstatus").trim();

	   String name = request.getParameter("cuser").trim();

       String password = request.getParameter("cpassword").trim();

	   try
		 {
		   Class.forName("com.mysql.jdbc.Driver");
		   con=DriverManager.getConnection("jdbc:mysql://localhost:3306/privacy","root","");
		   st=con.createStatement();

		   rs=st.executeQuery("select * from clouddetails where USERNAME='"+name+"'");

			if(rs.next())
			{
				 response.sendRedirect("createcloud.jsp?msg1=Cloud already exist");
			}
			else
			{

				java.util.Date ud1 =new java.util.Date();
				
				java.sql.Date sd1 = new java.sql.Date(ud1.getTime());

				String sql ="insert into clouddetails values(?,?,?,?,?)";
				
				ps=con.prepareStatement(sql);

				ps.setString(1,ip);
				ps.setString(2,name);
				ps.setString(3,password);
				ps.setString(4,status);
				ps.setDate(5,sd1);

				int a = ps.executeUpdate();
                
				if (a==1)
				{
                  response.sendRedirect("createcloud.jsp?msg1=Cloud Registration Successfully Done!");
				}
				else
				{
                  response.sendRedirect("ecreatecloud.jsp");
				}
			}
		 }
		 catch (Exception e)
		 {
			 e.printStackTrace();
		 }
  }
   
  public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
  {
        doGet(request, response);
  }
}
