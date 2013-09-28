import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.*;
import java.sql.*;

public class MobileSubscribe extends HttpServlet
{
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
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException{
		Connection con=null;
		res.setContentType("text/html");		
		res.setHeader("Cache-Control","no-store");
		HttpSession session=req.getSession(false); 
		PrintWriter out = res.getWriter();		
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
		String username=(String)session.getValue("username");
		if(username==null){
			out.println("<H2>Your Session has expired </H2>");
			out.println("<a href='../mobile/user.html'>Click Here</a> To Re-Login");
			return;
		}
		try{
			con=pool.getConnection();
			Statement stmt=con.createStatement();
			int Satyam=0,Infosys=0,Wipro=0,Reliance=0,News=0;
			String Astro="";
			ResultSet rs=stmt.executeQuery("Select * from MobileUsers where UserName='"+username+"'");
			while(rs.next()){
				Satyam=rs.getInt("Satyam");
				Infosys=rs.getInt("Infosys");
				Wipro=rs.getInt("Wipro");
				Reliance=rs.getInt("Reliance");
				News=rs.getInt("News");
				Astro=rs.getString("Astro");
			}
			rs.close();
			out.println("<html>");
			out.println("<head><title>N4E-Mobile  - Change Subscription</title></head>");
			out.println("<body>");
			out.println("<body bgcolor='#fofoff'>");
			out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>Change Subscription:</strong></big></big></font></p>");
			out.println("<hr>"); 
			out.println("<form action='/servlet/ChangedSubscription' method='post'>");
			out.println("<table border='0' cellPadding='0' cellSpacing='0' height='161' width='79%'><TBODY>");
			out.println("<tr>");
			out.println("<td align='right' height='27' vAlign='center' width='50%'><small><font color='#000080' face='Verdana'>News:</font></small></td>");
			out.println("<td align='left' height='30' vAlign='center' width='50%'><font face='Verdana'><small><input type='radio' value='0' name='News'"); 
			if(News==0)out.println("checked");
			out.println("> Not Needed </small><br>");
			out.println("</font><input type='radio' value='1' name='News'");
			if(News==1)out.println("checked");
			out.println("> <font face='Verdana'><small>");
			out.println("Hindustan Times </small><br>");
			out.println("<small><input type='radio' value='2' name='News'");
			if(News==2)out.println("checked");
			out.println("> Times Of India</small></font></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td align='right' height='27' vAlign='center' width='50%'><small><font face='Verdana'");
			out.println("color='#FF0000'>*</font><font color='#000080' face='Verdana'>Astrological Prediction:</font></small></td>");
			out.println("<td align='left' height='30' vAlign='center' width='50%'>&nbsp; <select name='Astro' size='1'>");
			out.println("<option selected value='"+Astro+"'>"+Astro+"</option>");
			out.println("<option value='Aquarius'>Aquarius</option>");
			out.println("<option value='Virgo'>Virgo</option>");
			out.println("<option value='Not Needed'>Not Needed</option>");
			out.println("</select></td>");
			out.println("</tr>");
			out.println("<tr align='middle'>");
			out.println("<td align='right' height='27' vAlign='center' width='50%'><small><font face='Verdana'");
			out.println("color='#FF0000'>*</font><font color='#000080' face='Verdana'>Stock Quotes:</font></small></td>");
			out.println("<td align='left' height='30' vAlign='center' width='50%'>&nbsp; <input type='checkbox'");
			out.println("name='Infosys' value='Infosys'");
			if(Infosys==1)out.println("checked");
			out.println("><small><font face='Verdana'>Infosys</font></small><br>");
			out.println("&nbsp; <input type='checkbox' name='Wipro' value='Wipro'");
			if(Wipro==1)out.println("checked");
			out.println("><small><font face='Verdana'>Wipro</font></small><br>");
			out.println("&nbsp; <input type='checkbox' name='Satyam' value='Satyam'");
			if(Satyam==1)out.println("checked");
			out.println("><small><font face='Verdana'>Satyam</font></small><br>");
			out.println("&nbsp; <input type='checkbox' name='Reliance' value='Reliance'");
			if(Reliance==1)out.println("checked");
			out.println("><small><font face='Verdana'>Reliance</font></small></td>");
			out.println("</tr>");
			out.println("<tr align='middle'>");
			out.println("<td align='middle' colSpan='2' height='30' vAlign='center' width='100%'><input name='B1'");
			out.println("type='submit' value='Submit'>&nbsp; <input name='B2'");			
			out.println("type='reset' value='Reset'></td>");
			out.println("</tr>");
			out.println("</TBODY>");
			out.println("</table>");
			out.println("</form>");
			out.println("<html>");
			out.close();
		}
		catch(Exception e){
			try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
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
		return "Servlet for Changing User's Account Information";
	}
}		
								
				