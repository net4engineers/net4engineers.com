import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class MobileLogin extends HttpServlet{
	private ConnectionPool pool;
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
		try{
			pool=new ConnectionPool(1,1);			
		}
		catch(Exception e){
			throw new UnavailableException(this,"could not create connection pool");
		}
	}		 	
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
	throws ServletException,IOException
	{
		Connection con=null;
		res.setContentType("text/html");
		res.setHeader("Cache-Control","no-store");		 
		HttpSession session=req.getSession(false); 		
		PrintWriter out=res.getWriter();		
		String username,passwd;
		try{					
			username=req.getParameter("UserName");
			passwd=req.getParameter("Password");
			if(username==null||passwd==null){
				out.println("<H2>Some of the Fields were left blank</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
				return;
			}
			if(username.equals("")||passwd.equals("")){
				out.println("<H2>Some of the Fields were left blank</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
				return;
			}			
			con=pool.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("Select * from MobileUsers where UserName = '"+username+"' AND Password = '"+passwd+"'");
			int c=0;
			while(rs.next())
			   c++;
			if(c==0){
				out.println("<H2>Invalid Username Or Password</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Choose a Different User Name");
				return;
			}
			if(session!=null)
			  session.invalidate();
			session=req.getSession(true);
			session.putValue("username",username);
			out.println("<html>");
			out.println("<head>");
			out.println("<title>N4E-Mobile - User Control Panel</title>");
			out.println("</head>");  
			rs=stmt.executeQuery("select Active,RealName from MobileUsers where username='"+username+"'");
			int Active=0;
			String RealName="";
			while(rs.next()){
				Active=rs.getInt("Active");
				RealName=rs.getString("RealName");
			} 
			out.println("<body bgcolor='#B3B3D9'>");
			out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>N4E-Mobile User's Homepage</strong></big></big></font></p>");
			out.println("<hr>");			
			out.println("<p align='left'><small><strong><font face='Verdana' color='#FFFF00'><center>Hi,"+RealName+" Welcome to Your Homepage at ExamOnline</font></strong></small></p>");              
			//if Active =0 asks for validateCode 
			if(Active==0){
				out.println("<p align='left'><small><strong><font face='Verdana' color='#FFFF00'><center>Your Mobile Account is not Active. Enter Your Validation code recieved by SMS to activate your account</font></strong></small></p>");              
				out.println("<form method='POST' action='ActivateMobileAcc'>");
				out.println("<div align='center'><center><p><small><font face='Verdana'>Validation Code</font></small>:");
				out.println("<input type='text' name='ValidateCode' size='6'  style='border: 1px solid rgb(0,0,128)'><br><br>");
				out.println("<input type='submit' value='Activate Account' name='B1' style='background-color: rgb(0,0,128); color: rgb(255,255,255); font-size: smaller; border: thin solid rgb(255,255,255); background-position: center'></p></center></div></form>");							
			}
			//else shows his panel   
			else{
				out.println("<p align='center'><a href='/servlet/MobileSubscribe'>Change Subscription</a></p>");			
				out.println("<p align='center'><a href='/servlet/MobileChngPass'>Change Password</a></p>");				
			}
			out.println("<p align='center'><a href='/servlet/MobileLogout'>Logout</a></p>");			
		    out.println("</BODY>");
		    out.println("</html>");
		    rs.close();
		    stmt.close();
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
    public void doGet(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException
	{
		Connection con=null;
		res.setContentType("text/html");
		res.setHeader("Cache-Control","no-store");		 
		HttpSession session=req.getSession(false); 		
		PrintWriter out=res.getWriter();		
		String username,passwd;
		try{
			if(session==null){
			    out.println("<H2>Your Session has expired </H2>");
				out.println("<a href='../mobile/user.html'>Click Here</a> To Re-Login");
				return;
		    }
		    java.util.Date time_comp=new java.util.Date(System.currentTimeMillis()-20*60*1000);
		    java.util.Date accessed=new java.util.Date(session.getLastAccessedTime());
		    if(accessed.before(time_comp)){
		    	session.invalidate();
		     	out.println("<H2>Your Session has expired </H2>");
		     	out.println("<a href='../mobile/user.html'>Click Here</a> To Re-Login");
		     	return;
		    }  		
		    username=(String)session.getValue("username");
		    if(username==null){
		    	out.println("<H2>Your Session has expired </H2>");
		    	out.println("<a href='../mobile/user.html'>Click Here</a> To Re-Login");
		    	return;
		    }	
		    con=pool.getConnection();
			Statement stmt=con.createStatement();
		    out.println("<html>");
			out.println("<head>");
			out.println("<title>N4E-Mobile - User Control Panel</title>");
			out.println("</head>");  
			ResultSet rs=stmt.executeQuery("select Active,RealName from MobileUsers where username='"+username+"'");
			int Active=0;
			String RealName="";
			while(rs.next()){
				Active=rs.getInt("Active");
				RealName=rs.getString("RealName");
			} 
			out.println("<body bgcolor='#B3B3D9'>");
			out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>N4E-Mobile User's Homepage</strong></big></big></font></p>");
			out.println("<hr>");			
			out.println("<p align='left'><small><strong><font face='Verdana' color='#FFFF00'><center>Hi,"+RealName+" Welcome to Your Homepage at ExamOnline</font></strong></small></p>");              
			//if Active =0 asks for validateCode 
			if(Active==0){
				out.println("<p align='left'><small><strong><font face='Verdana' color='#FFFF00'><center>Your Mobile Account is not Active. Enter Your Validation code recieved by SMS to activate your account</font></strong></small></p>");              
				out.println("<form method='POST' action='ActivateMobileAcc'>");
				out.println("<div align='center'><center><p><small><font face='Verdana'>Validation Code</font></small>:");
				out.println("<input type='text' name='ValidateCode' size='6'  style='border: 1px solid rgb(0,0,128)'><br><br>");
				out.println("<input type='submit' value='Activate Account' name='B1' style='background-color: rgb(0,0,128); color: rgb(255,255,255); font-size: smaller; border: thin solid rgb(255,255,255); background-position: center'></p></center></div></form>");							
			}
			//else shows his panel   
			else{
				out.println("<p align='center'><a href='/servlet/MobileSubscribe'>Change Subscription</a></p>");			
				out.println("<p align='center'><a href='/servlet/UseChngPass1'>Change Password</a></p>");				
			}
			out.println("<p align='center'><a href='/servlet/MobileLogout'>Logout</a></p>");			
		    out.println("</BODY>");
		    out.println("</html>");
		    rs.close();
		    stmt.close();
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
		return "Mobile User's Login Page";
	}
}