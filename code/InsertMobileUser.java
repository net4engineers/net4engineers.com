import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import SendMail;

public class InsertMobileUser extends HttpServlet{
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
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
		Connection con=null;
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		res.setHeader("Cache-Control","no-store"); 
		String RealName,UserName,Password,Mail,temp_news,Astro,temp_Satyam,temp_Infosys,temp_Wipro,temp_mobile,temp_Reliance;
		int ValidateCode,Active=0,UserID=0,Satyam,Infosys,Wipro,Reliance,News;
		long MobileNo;		
		try{
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>N4E-Mobile - Registration Successfull</TITLE>");
			out.println("</HEAD>");
			out.println("</BODY>");
			RealName=req.getParameter("RealName");
			UserName=req.getParameter("UserName");
			Password=req.getParameter("Password");
			temp_mobile=req.getParameter("MobileNo");
			MobileNo=Long.parseLong(temp_mobile);
			Mail=req.getParameter("Mail");
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
			ResultSet rs=stmt.executeQuery("select MAX(UserID) from MobileUsers"); 
			if(rs.next()){
				UserID=rs.getInt("MAX(UserID)");
				if(rs.wasNull())UserID=0;
			}
			UserID++;
			Random r=new Random();
			ValidateCode=(int)((32000)*r.nextDouble());
			stmt.executeUpdate("insert into MobileUsers values("+UserID+",'"+RealName+"','"+UserName+"','"+Password+"',"+MobileNo+",'"+Mail+"',"+Satyam+","+Infosys+","+Wipro+","+Reliance+","+News+",'"+Astro+"',"+ValidateCode+","+Active+")");
			String msg="Your Validation Code for Mobile.Net4Engineeers.com is "+ValidateCode;
			int prefix=(int)(MobileNo/1000000);
			String address="";
			int country_code=0;
			rs = stmt.executeQuery("Select address,country_code from sms_mapping where prefix="+prefix);			                     
			if(rs.next()){
				address=rs.getString("address");
				country_code=rs.getInt("country_code");
				if (rs.wasNull()) country_code=0; 
			}
			rs.close();
			String to;
			if(country_code==0)
			to=MobileNo+""+address;
			else
			to=country_code+""+MobileNo+""+address;
			String email="Mobile@Net4Engineers.com";
			String sub="Validation Code";
			SendMail sm=new SendMail(msg,email,to,sub);
			if(sm.send()) ;//success
			else ;//failure
			out.println("<body bgcolor='#fofoff'>");
			out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>User Registration successful</strong></big></big></font></p>");
			out.println("<hr>"); 			
			out.println("<p align='center'><small><strong><font color='#000000' face='verdana'>Congrats, "+RealName+" you have Successfully Registered with us <br>with a Username:- "+UserName+"<br> You Will Recieve A SMS with a Validation-Code</strong></small></p>");			
			out.println("<p align='center'><small><strong><font color='#000000' face='verdana'>Please Login to Your Control Panel & Enter that code to activate Your account</strong></small></p>");			
			out.println("<p align='center'><font color='#000000' face='verdana'><br><a href='../mobile/user.html'>Click Here</a> to Login <br></p></BODY></HTML>");			
		    stmt.close();
		}
	
		catch(Exception e){
			try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
				e.printStackTrace(out);
				out.println("<a href='javascript:history.go(-2)'>Click Here</a> to go back & Try Again");
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
		return "This servlet insert new users into database";
	}
}