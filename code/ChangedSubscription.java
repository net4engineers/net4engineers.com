import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

public class ChangedSubscription extends HttpServlet
{
	private ConnectionPool pool;
	public void init(ServletConfig config) throws ServletException
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
	throws ServletException,IOException{
		Connection con=null;
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		res.setHeader("Cache-Control","no-store"); 
		String username;
		HttpSession session=req.getSession(false);
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
		try{
			String temp_news,Astro,temp_Satyam,temp_Infosys,temp_Wipro,temp_Reliance;
			int Satyam,Infosys,Wipro,Reliance,News;
			temp_news=req.getParameter("News");
			if(temp_news.equals("0")) News=0;			
			else if(temp_news.equals("1")) News=1;
			else if (temp_news.equals("2")) News=2;
			else News=0;
			Astro=req.getParameter("Astro");
			if(Astro.equals("Not Needed")) Astro=" ";
			temp_Satyam=req.getParameter("Satyam");
			if(temp_Satyam==null||temp_Satyam.equals("null"))Satyam=0;
			else Satyam=1;
			temp_Infosys=req.getParameter("Infosys");
			if(temp_Infosys==null||temp_Infosys.equals("null"))Infosys=0;
			else Infosys=1;
			temp_Wipro=req.getParameter("Wipro");
			if(temp_Wipro==null||temp_Wipro.equals("null"))Wipro=0;
			else Wipro=1;
			temp_Reliance=req.getParameter("Reliance");
			if(temp_Reliance==null||temp_Reliance.equals("null"))Reliance=0;
			else Reliance=1;
			con=pool.getConnection();
			Statement stmt=con.createStatement();
			stmt.executeUpdate("Update MobileUsers set Satyam="+Satyam+" ,Infosys="+Infosys+",Wipro="+Wipro+",Reliance="+Reliance+",News="+News+",Astro='"+Astro+"' where UserName='"+username+"'");
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>N4E-Mobile - Change in Subscription Successfull</TITLE>");
			out.println("</HEAD>");
			out.println("</BODY>");
			out.println("<body bgcolor='#fofoff'>");
			out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>Change in Subscription Successfull</strong></big></big></font></p>");
			out.println("<hr>"); 
			out.println("<p align='center'><font color='#000000' face='verdana'><br><a href='MobileLogin'>Click Here</a> to go to Main Page <br></p></BODY></HTML>");				
			out.close();			
		}
		catch(Exception e)
		{
			try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
				out.println("<br><br><a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Try Again");
				con.rollback();				
			   }
			catch (Exception ignored) { }
			
		}
		finally
		{
			
				if(con!=null) pool.returnConnection(con);
				out.close();		
		}
    }
    
    public String getServletInfo(){
		return "This servlet validates all information sent for changing user information & asks user for confirmation";
	}
}
		
		