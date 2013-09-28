import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class NewMobileUser extends HttpServlet{
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
		res.setHeader("Cache-Control","no-store"); 		
		PrintWriter out=res.getWriter();		
		String RealName,UserName,Password,vpasswd,Mail,temp_news,Astro,temp_Satyam,temp_Infosys,temp_Wipro,temp_prefix,temp_suffix,temp_Reliance;
		//int ValidateCode,Active=0,prefix,suffix,UserID,Satyam,Infosys,Wipro,Reliance,News;
		//Long MobileNo;
		RealName=req.getParameter("RealName");
		UserName=req.getParameter("UserName");
		Password=req.getParameter("Password");
		vpasswd=req.getParameter("vpasswd");
		temp_prefix=req.getParameter("prefix");
		temp_suffix=req.getParameter("suffix");
		Mail=req.getParameter("Mail");
		temp_news=req.getParameter("News");
		Astro=req.getParameter("Astro");
		temp_Satyam=req.getParameter("Satyam");
		temp_Infosys=req.getParameter("Infosys");
		temp_Wipro=req.getParameter("Wipro");
		temp_Reliance=req.getParameter("Reliance");
		if((RealName==null)||(UserName==null)||(Password==null)||(vpasswd==null)||(temp_suffix==null)||(Mail==null)||RealName.equals("")||UserName.equals("")||Password.equals("")||vpasswd.equals("")||temp_suffix.equals("")||Mail.equals("")||(!(validate1(RealName)&&validate2(UserName)&&validate3(Password)&&validate8(temp_suffix)))||(!checkmail(Mail))){
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>ExamOnline - Fill User Registration Form</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("<!--webbot bot='HTMLMarkup' startspan TAG='XBOT' -->");
			out.println("<IFRAME SRC='../header.html' NAME='head' WIDTH='100%' HEIGHT='175' MARGINWIDTH='0' MARGINHEIGHT='0' FRAMEBORDER='0' noresize SCROLLING=NO>");
			out.println("<!--webbot bot='HTMLMarkup' endspan -->");
			out.println("<!--webbot bot='HTMLMarkup' startspan TAG='XBOT' -->");
			out.println("</IFRAME><!--webbot bot='HTMLMarkup' endspan -->");
			out.println("<table border='1' width='100%' bgcolor='#f0f0ff' bordercolor='#000000' cellspacing='0' cellpadding='0'>");
			out.println("<tr><td width='100%'>&nbsp;<p align='left'><font face='Verdana'color='#000000'>");
			out.println("<big>&nbsp;&nbsp;&nbsp;&nbsp;New User Registration</big></font></p>");
			out.println("<hr size='1' color='#000000' width='90%'>");
			out.println("<p align='left'><small><strong><font face='Verdana'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Some of the fields were left blank or not according to rules<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			out.println("Please fill the item shown in RED COLOR, again");
			out.println("</font></strong></small><br></p>");
			out.println("<form action='/servlet/NewMobileUser' method='POST'>");
			out.println("<div align='center'><center><table border='0' width='104%' cellspacing='0' cellpadding='0'>");
			if(RealName!=null&&validate1(RealName)&&(!RealName.equals(""))){
				out.println("<tr>");
				out.println("<td width='33%' height='35' align='right' valign='middle'><small><small><strong><font face='verdana'>Name:</font></strong></small></td>");
				out.println("<td width='33%' height='35' align='left' valign='middle'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+RealName+"</font></small></td>");
				out.println("<input type=hidden name='RealName' value='"+RealName+"'>");
				out.println("</tr>");
			}
			else{
				out.println("<tr>");
				out.println("<td width='42%' valign='top' align='right'>");
				out.println("<div align='right'><p><small><strong>");
				out.println("<fontface='Verdana' color='#FF0000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><font color='#FF0000'>Name:</font></strong></small></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'><blockquote>");
				out.println("<div align='left'><p><input type='text' name='RealName' size='20' tabindex='1'  style='border: 1px solid rgb(0,0,128)'></p></div></blockquote></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'></td>");
				out.println("</tr>");
			}
			if(UserName!=null&&validate2(UserName)&&(!UserName.equals(""))){
				out.println("<tr>");
				out.println("<td width='33%' height='35' align='right' valign='middle'><small><small><strong><font face='verdana'>User Name:</font></strong></small></td>");
				out.println("<td width='33%' height='35' align='left' valign='middle'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+UserName+"</font></small></td>");
				out.println("<input type=hidden name='UserName' value='"+UserName+"'>");
				out.println("</tr>");
			}
			else{
				out.println("<tr>");
				out.println("<td width='42%' valign='top' align='right'>");
				out.println("<div align='right'><p><small><strong>");
				out.println("<fontface='Verdana' color='#FF0000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><font color='#FF0000'>User Name:</font></strong></small></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'><blockquote>");
				out.println("<div align='left'><p><input type='text' name='UserName' size='20'  style='border: 1px solid rgb(0,0,128)'></p></div></blockquote></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'></td>");
				out.println("</tr>");
			}			
			if(Password!=null&&validate3(Password)&&Password.equals(vpasswd)&&(!Password.equals(""))){
				out.println("<tr>");				
				out.println("<input type=hidden name='passwd' value='"+Password+"'>");
				out.println("<input type=hidden name='vpasswd' value='"+Password+"'>");
				out.println("</tr>");
			}
			else{
				out.println("<tr>");
				out.println("<td width='42%' valign='top' align='right'>");
				out.println("<div align='rightt'><p><small><strong>");
				out.println("<fontface='Verdana' color='#FF0000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><font color='#FF0000'>Password:</font></strong></small></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'><blockquote>");
				out.println("<div align='left'><p><input type='password' name='Password' size='20'  style='border: 1px solid rgb(0,0,128)'></p></div></blockquote></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td width='42%' valign='top' align='right'>");
				out.println("<div align='right'><p><small><strong>");
				out.println("<fontface='Verdana' color='#FF0000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><font color='#FF0000'>Confirm Password:</font></strong></small></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'><blockquote>");
				out.println("<div align='left'><p><input type='password' name='vpasswd' size='20' style='border: 1px solid rgb(0,0,128)'></p></div></blockquote></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'></td>");
				out.println("</tr>");
			}
			if(temp_suffix!=null&&validate8(temp_suffix)&&(!temp_suffix.equals(""))){
				out.println("<tr>");
				out.println("<td width='33%' height='35' align='right' valign='middle'><small><small><strong><font face='verdana'>Mobile No:</font></strong></small></td>");
				out.println("<td width='33%' height='35' align='left' valign='middle'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+temp_prefix+temp_suffix+"</font></small></td>");
				out.println("<input type=hidden name='prefix' value='"+temp_prefix+"'>");
				out.println("<input type=hidden name='suffix' value='"+temp_suffix+"'>");
				out.println("</tr>");
			}
			else{
				out.println("<tr>");
				out.println("<td width='42%' valign='top' align='right'>");
				out.println("<div align='right'><p><small><strong>");
				out.println("<fontface='Verdana' color='#FF0000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><font color='#FF0000'>Mobile No:</font></strong></small></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'><blockquote>");
				out.println("<div align='left'><p><select name='prefix' size='1'><option value='9811'>9811</option></select>");
				out.println("<input name='suffix' style='BORDER-BOTTOM: rgb(0,0,128) 1px solid; BORDER-LEFT: rgb(0,0,128) 1px solid; BORDER-RIGHT: rgb(0,0,128) 1px solid; BORDER-TOP: rgb(0,0,128) 1px solid' size='6'></p></div></blockquote></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'></td>");
				out.println("</tr>");
			}
			if(Mail!=null&&checkmail(Mail)&&(!Mail.equals(""))){
				out.println("<tr>");
				out.println("<td width='33%' height='35' align='right' valign='middle'><small><small><strong><font face='verdana'>Email ID:</font></strong></small></td>");
				out.println("<td width='33%' height='35' align='left' valign='middle'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+Mail+"</font></small></td>");
				out.println("<input type=hidden name='Mail' value='"+Mail+"'>");
				out.println("</tr>");
			}
			else{
				out.println("<tr>");
				out.println("<td width='42%' valign='top' align='right'>");
				out.println("<div align='right'><p><small><strong>");
				out.println("<fontface='Verdana' color='#FF0000'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font><font color='#FF0000'>Email ID:</font></strong></small></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'><blockquote>");
				out.println("<div align='left'><p><input type='text' name='Mail' size='20'  style='border: 1px solid rgb(0,0,128)'></p></div></blockquote></td>");
				out.println("<td width='33%' height='30' align='left' valign='top'></td>");
				out.println("</tr>");
			}
			out.println("<tr>");			
			out.println("<input type=hidden name='News' value='"+temp_news+"'>");
			out.println("<input type=hidden name='Astro' value='"+Astro+"'>");
			out.println("<input type=hidden name='Satyam' value='"+temp_Satyam+"'>");
			out.println("<input type=hidden name='Infosys' value='"+temp_Infosys+"'>");
			out.println("<input type=hidden name='Wipro' value='"+temp_Wipro+"'>");
			out.println("<input type=hidden name='Reliance' value='"+temp_Reliance+"'>");
			out.println("</tr>");			
			out.println("<tr align='center'><td width='100%' align='right' valign='top' colspan='3'><div align='center'><center><p><input type='submit' value='Submit' name='B1' style='background-color: rgb(0,0,128); color: rgb(255,255,255); font-size: smaller; border: thin solid rgb(255,255,255); background-position: center'></td></tr>");			
			out.println("</table></center></div></form></BODY></HTML>");
			out.println("<!--webbot bot='HTMLMarkup' startspan TAG='XBOT' -->");
			out.println("<IFRAME SRC='../footer.html' NAME='bol'WIDTH='100%' HEIGHT='97' MARGINWIDTH='0' MARGINHEIGHT='0' FRAMEBORDER='0' noresize SCROLLING=NO>");
			out.println("<!--webbot bot='HTMLMarkup' endspan -->");
			out.println("<!--webbot bot='HTMLMarkup' startspan TAG='XBOT' --></IFRAME>");
			out.println("<!--webbot bot='HTMLMarkup'endspan -->");
			return;
		}
		try{
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>ExamOnline - Confirm User Registration</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");			
			con=pool.getConnection();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("Select * from MobileUsers where UserName='"+UserName+"'");
			int c=0;
			while(rs.next()) c++;
			if(c!=0){
				out.println("<H2>SomeBody has already chosen this username</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Choose a Different User Name");
				out.println("</BODY></HTML>");
				return;
			}
			rs=stmt.executeQuery("Select * from MobileUsers where MobileNo="+temp_prefix+temp_suffix+" ");
			c=0;
			while(rs.next()) c++;
			if(c!=0){
				out.println("<H2>A user with this Mobile Number already Exists</H2>");
				out.println("<a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Try Again");
				out.println("</BODY></HTML>");
				return;
			}
			out.println("<body bgcolor='#B3B3D9'>");
			out.println("<p align='center'><font color='#000000' face='verdana'><big><big><strong>Confirm User Registration</strong></big></big></font></p>");    
			out.println("<hr>");  
			out.println("<p align='left'><small><strong><font face='Verdana'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FFFF00'>Hi, "+RealName+" please check the information below and confirm your registration:-</font></strong></small></p>");                   
			out.println("<div align='center'><center><table border='0' width='73%' cellspacing='0' cellpadding='0'>");
			out.println("<tr>");
			out.println("<td width='33%' height='35' align='right' valign='middle'><small><small><strong><font face='verdana'>Name:-</font></strong></small></td>");
			out.println("<td width='33%' height='35' align='left' valign='middle'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+RealName+"</font></small></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=50%' height='35' align='right' valign='baseline'><small><small><strong><font face='verdana'>User Name:-    </font></strong></small></td>");
			out.println("<td width='50%' height='35' align='left' valign='top'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  "+UserName+"</font></small></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=50%' height='35' align='right' valign='baseline'><small><small><strong><font face='verdana'>Mobile No.:-    </font></strong></small></td>");
			out.println("<td width='50%' height='35' align='left' valign='top'><small><font face='Arial'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "+temp_prefix+temp_suffix+"</font></small></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td width=50%' height='35' align='right' valign='baseline'><small><small><strong><font face='verdana'>E-mail Address:-</font></strong></small></td>");
			out.println("<td width='50%' height='35' align='left' valign='top'><small><font face='Arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+Mail+"</font></small></td>");
			out.println("</tr>");
			out.println("<tr>");			
			out.println("</tr>");
			out.println("</table></center></div>");
			out.println("<p align='center'><small><strong><font color='#FFFF00'>If the information displayed above is Correct, Click on confirm to complete your Registration.<br>Or<br> <a href='javascript:history.go(-1)'>Click Here</a> to go back &amp; fill the information again.</strong></p>");
			out.println("<form method='POST' action='/servlet/InsertMobileUser'>");
			out.println("<input type=hidden name='RealName' value='"+RealName+"'>");			
			out.println("<input type=hidden name='UserName' value='"+UserName+"'>");
			out.println("<input type=hidden name='Password' value='"+Password+"'>");
			out.println("<input type=hidden name='MobileNo' value='"+temp_prefix+temp_suffix+"'>");
			out.println("<input type=hidden name='Mail' value='"+Mail+"'>");
			out.println("<input type=hidden name='News' value='"+temp_news+"'>");
			out.println("<input type=hidden name='Astro' value='"+Astro+"'>");
			out.println("<input type=hidden name='Satyam' value='"+temp_Satyam+"'>");
			out.println("<input type=hidden name='Infosys' value='"+temp_Infosys+"'>");
			out.println("<input type=hidden name='Wipro' value='"+temp_Wipro+"'>");
			out.println("<input type=hidden name='Reliance' value='"+temp_Reliance+"'>");					
			out.println("<p><center><input type='submit' value='Confirm' name='B1'></center></p></form></BODY></HTML>");
			stmt.close();
		}
		catch(Exception e){
			try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
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
    public boolean validate1(String value){
    	String valid=" abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	boolean ok = true;
    	String temp;
    	for (int i=0; i<value.length(); i++) {
    		temp = "" + value.substring(i, i+1);
    		if (valid.indexOf(temp) == -1){
    			ok =false;
    			break;
    		}
    	}
    	return ok;
    }
    public boolean validate2(String value){
    	String valid="0123456789_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	String start="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    	boolean ok = true;
    	String temp;
    	if (value.length()>20||value.length()<5){
    		return false;
    	}
    	char st = value.charAt(0);
    	if (start.indexOf(st) == -1){
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
    public boolean validate3(String value){    	    	
    	if (value.length()>10||value.length()<5) return false;    	
    	return true;
    }
    public boolean validate8(String value){
    	String valid="0123456789";
    	boolean ok = true;
    	String temp;
    	if (value.length()>6){
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
    public String getServletInfo(){
		return "This servlet validates all information sent for new user registration & asks user for confirmation";
	}
}