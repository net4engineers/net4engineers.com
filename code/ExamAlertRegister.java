import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.text.*;
import SendMail;
public class ExamAlertRegister extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res)
	throws ServletException,IOException{
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		res.setHeader("Cache-Control","no-store");
		String email=null;
		email = req.getParameter("email");
		if(email==null||email.equals(""))
		{
		  out.println("<html>");
		  out.println("<head><title>ExamAlert</title></head>");
		  out.println("<body>");
		  out.println("<H2>Email field may have been left empty</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;		
		}
		if(checkmail(email));
		else
		{
		  out.println("<html>");
		  out.println("<head><title>ExamAlert</title></head>");
		  out.println("<body>");
		  out.println("<H2>Invalid Email Address</H2>");
		  out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
		  out.println("</body>");
		  out.println("</html>");
		  return;
		}
		String to="eiteam@stanfordplus.com";
		String sub="Exam Alert Registeration";
		String msg="Please Subscribe me to Exam Alerts";
		SendMail sm=new SendMail(msg,email,to,sub);
		try{
			if(sm.send()) out.println("<script language='javascript'>document.location='../examregistered.html'</script>");//success
			else ;//failure
		}
		catch(Exception e){
			e.printStackTrace(out);
		}
		finally{			
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
	public String getServletInfo(){
	return "Exam Alert Registeration Servlet";
    }
}
                
                

		
	