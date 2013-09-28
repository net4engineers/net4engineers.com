import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.*;
import java.util.*;
import SendMail;

public class MobilePassReminder extends HttpServlet
{
	private ConnectionPool pool;
	public void init(ServletConfig config)throws ServletException
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
		String username;
		try
		{
			out.println("<html>");
			out.println("<head><title>N4E-Mobile - Password Reminder</title></head>");
			out.println("<body>");
			username=req.getParameter("UserName");
			if(username.equals(""))
			{
				out.println("<H2>username field was left empty</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a>to go back to previous page and try again");
				return;
			}			
			con=pool.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select Mail,Password from MobileUsers where UserName='"+username+"'");
			int c=0;
			String Mail="",Password="";
			while(rs.next()){
				Mail=rs.getString("Mail");
				Password=rs.getString("Password");
				c++;
			}
			if(c==0)
			{
				out.println("<H2>Invalid Username</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Choose a Different User Name");
				return;
			}
			String email="Mobile@Net4Engineers.com";
			String sub="N4E-Mobile Password";
			String msg="Your N4E-Mobile Password is:"+Password;
			SendMail sm=new SendMail(msg,email,Mail,sub);
			if(sm.send()) ;//success
			else ;//failure
			out.println("<p align='center'><strong>&nbsp;</strong></p>");
			out.println("<p align='center'><strong>&nbsp;</strong></p>");
			out.println("<p align='center'><strong>Your password has been mailed to Your Email Address:"+Mail+"</strong></p>");
			out.println("<p align='center'><strong><a href='../mobile/user.html'>Click Here</a> to Go to Main Page!</strong></p></body></html>");
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
		return "This servlet mails Password to mail address of user";
	}
}		
								
				