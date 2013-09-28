import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import SendMail;
public class SendSMS extends HttpServlet{
	private ConnectionPool pool;
	public void init(ServletConfig config)throws ServletException{
		super.init(config);
		try{
			pool = new ConnectionPool(1,1);
		}
		catch(Exception e){
			throw new UnavailableException(this,"could not create a connection");
	    }
	}
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		res.setHeader("Cache-Control","no-store");
		Connection con = null;
		long prefix=0,suffix=0;
		boolean confirm=false;
		int count=0;
		String name=null,email=null,message=null;
		String temp_prefix = req.getParameter("prefix");
		String temp_suffix = req.getParameter("suffix");
		name = req.getParameter("name");
		email = req.getParameter("email");
		message = req.getParameter("message");
		String temp_confirm = req.getParameter("confirmation");
		String temp_count = req.getParameter("char_count");
		if((temp_suffix==null)||(name==null)||(email==null)||(message==null))
		{
		  out.println("<html>");
		  out.println("<head><title>SMS</title></head>");
		  out.println("<body>");
		  out.println("<H2>Some field or fields may have been left empty</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;		
		}		
		if(temp_suffix.equals("")||name.equals("")||email.equals("")||message.equals(""))
		{
		  out.println("<html>");
		  out.println("<head><title>SMS</title></head>");
		  out.println("<body>");
		  out.println("<H2>Some field or fields may have been left empty</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;
		}
		if(checkmail(email));
		else
		{
		  out.println("<html>");
		  out.println("<head><title>SMS</title></head>");
		  out.println("<body>");
		  out.println("<H2>Invalid Email Address</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;
		}
		if(validate8(temp_suffix));
		else
		{
		  out.println("<html>");
		  out.println("<head><title>SMS</title></head>");
		  out.println("<body>");
		  out.println("<H2>Invalid Mobile Number</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;
		}
		prefix = Long.parseLong(temp_prefix);
		suffix = Long.parseLong(temp_suffix);
		if(temp_confirm.equals("yes"))confirm=true;
		else confirm=false;
		count = Integer.parseInt(temp_count);
		if(count>80)
		{
		  out.println("<html>");
		  out.println("<head><title>SMS</title></head>");
		  out.println("<body>");
		  out.println("<H2>Message can't be greater than 80 characters</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;
		}	
		try{
			con = pool.getConnection();
			Statement stmt = con.createStatement();
			String address=null;
			int country_code=0;
			ResultSet rs = stmt.executeQuery("Select address,country_code from sms_mapping where prefix="+prefix);			                     
			if(rs.next()){
				address=rs.getString("address");
				country_code=rs.getInt("country_code");
				if (rs.wasNull()) country_code=0; 
			}
			rs.close();
			String to;
			if(country_code==0)
			to=prefix+""+suffix+address;
			else
			to=country_code+""+prefix+""+suffix+address;
			String sub="SMS";
			String msg=message+"\nFrom:- "+name+"\n Sent with sms.Net4Engineers.com";
			if(msg.length()>120)
			{
				out.println("<html>");
		  		out.println("<head><title>SMS</title></head>");
		  		out.println("<body>");
		  		out.println("<H2>Message too bigger</H2>");
		  		out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  		out.println("</body>");
		  		out.println("</html>");
		  		return;
			}	
			SendMail sm=new SendMail(msg,email,to,sub);
			if(sm.send()) ;//success
			else ;//failure
			if(confirm){
				msg="Your SMS has been successfully sent to mobile number "+prefix+suffix+"";
				SendMail cnfrm=new SendMail(msg,"SMS@net4engineers.com",email,"SMS Sent Successfully");
				if(cnfrm.send()) ;//success
				else ;//failure
			}
			else;
			RequestDispatcher dispatcher = req.getRequestDispatcher("/sms/smssent.jsp");
			dispatcher.forward(req,res);
        }
        catch(Exception e){
        	try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
				e.printStackTrace(out);
				out.println("<br><br><a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Try Again");
				out.println("</BODY></HTML>");
				con.rollback();				
			}
			catch (Exception ignored) { }
		}
		finally{
			
				if(con!=null) pool.returnConnection(con);
				out.close();		
		}
	}
    public boolean checkmail(String value) {
    	int atsign,lastsign;
    	atsign=value.indexOf("@");
    	lastsign=value.lastIndexOf("@");    	
    	if(atsign==-1||lastsign!=atsign) return false;    	
    	atsign=value.lastIndexOf(".");
    	if(atsign==-1) return false;
    	int len=value.length();
    	if(atsign==len-2);
    	else{
    		String domain=value.substring(atsign+1);
    		if(domain.equals("com")||domain.equals("COM")||domain.equals("NET")||domain.equals("ORG")||domain.equals("net")||domain.equals("org")||domain.equals("edu")||domain.equals("EDU")||domain.equals("int")||domain.equals("INT")||domain.equals("MIL")||domain.equals("mil")||domain.equals("gov")||domain.equals("GOV")||domain.equals("arpa")||domain.equals("ARPA")||domain.equals("biz")||domain.equals("BIZ")||domain.equals("aero")||domain.equals("name")||domain.equals("coop")||domain.equals("info")||domain.equals("INFO")||domain.equals("pro")||domain.equals("museum"));
    		else return false;
    	}
    	atsign=value.indexOf(" ");    	
    	if(atsign!=-1)return false;
    	return true;
    }
    public boolean validate8(String value){
    	String valid="0123456789";
    	boolean ok = true;
    	String temp;
    	if (value.length()!=6){
    		return false;
    	}
    	for (int i=0; i<value.length(); i++) {
    		temp = "" + value.substring(i, i+1);
    		if (valid.indexOf(temp) == -1){
    			ok =false;
    			break;
    		}
    	}
    	return ok;
    }
	public String getServletInfo(){
	return "SMS sending Servlet";
    }
}
                
                

		
	