import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;

public class ActivateMobileAcc extends HttpServlet
{
	private ConnectionPool pool;
	public void init(ServletConfig config)	throws ServletException
	{
		super.init(config);
		try
		{
			pool=new ConnectionPool(1,1);
		}
		catch(Exception e)
		{
			throw new UnavailableException(this,"could not create connection pool");
		}
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		Connection con=null;
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		res.setHeader("Cache-Control","no-store");
		int ValidateCode=0;
		String temp_ValidateCode,username; 
		HttpSession session=req.getSession(false); 
		username=(String)session.getValue("username");
		if(username==null){
			out.println("<H2>Your Session has expired </H2>");
		    out.println("<a href='../mobile/user.html'>Click Here</a> To Re-Login");
		    return;
		}	
		try
		{
			out.println("<html>");
			out.println("<head><title>N4E-Mobile  - Activate Your Account</title></head>");
			out.println("<body>");
			temp_ValidateCode=req.getParameter("ValidateCode");
			if(temp_ValidateCode.equals("")){
				out.println("<H2>The Code field was left blank</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a> to go back & Try Again");
				return;
			}		
			con=pool.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select ValidateCode from MobileUsers where UserName='"+username+"'");
			while(rs.next())
			{
				ValidateCode=rs.getInt("ValidateCode");
			}
			rs.close();
			if(ValidateCode!=Integer.parseInt(temp_ValidateCode)){
				out.println("<H2>The Code is Wrong</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a> to go back & Try Again");
				return;
			}
			else{
				stmt.executeUpdate("Update MobileUsers set Active=1 where UserName='"+username+"'");
				out.println("<HTML>");
				out.println("<HEAD>");
				out.println("<TITLE>N4E-Mobile - Activation Successfull</TITLE>");
				out.println("</HEAD>");
				out.println("</BODY>");
				out.println("<body bgcolor='#fofoff'>");
				out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>Account Activation successful</strong></big></big></font></p>");
				out.println("<hr>"); 
				out.println("<p align='center'><small><strong><font color='#000000' face='verdana'>Please Re-Login to Your Control Panel</strong></small></p>");			
				out.println("<p align='center'><font color='#000000' face='verdana'><br><a href='../mobile/user.html'>Click Here</a> to Login <br></p></BODY></HTML>");			
			}
			
		}
		catch(Exception e){
			try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
				out.println("<br><br><a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Try Again");
				con.rollback();				
			}
			catch (Exception ignored) { }			
		}
		finally{
			
				if(con!=null) pool.returnConnection(con);
				out.close();		
		}
    }
    public String getServletInfo(){
		return "This servlet Activates Mobile User's Account";
	}
}		
								
				