/**
 * Copyright (c) 2002 by Fiorano Software, Inc.,
 * Los Gatos, California, 95032, U.S.A.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Fiorano Software, Inc. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * enclosed with this product or entered into with Fiorano.
 *
 */
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.oreilly.servlet.MultipartRequest;

/**
 *  This sends mail to all email address in CSV file 
 *  by composing a html mail when given an html file & a CSV files as input from upload 
 *@author     Pankaj Batra
 *@created    August 6, 2003
 *@version    2.0
 */
public class MailBlasterServlet extends HttpServlet
{
	//String propFileName = "../config/mail.properties";
    Properties props = new Properties();
    private Message message;
  	private Session session;
    String HTMLFile;
    String CSVFile; 
    String subject;
    String from_email;
    String from_name;   
    public void init(ServletConfig config) throws ServletException
    {
    	super.init(config);
        boolean debug = false;
        try{
        	//FileInputStream is = new FileInputStream(propFileName);
        	//props.load(is);
        	props.put("mail.smtp.auth","true");
        	session = Session.getInstance(props, null);
        	session.setDebug(true);
        	// construct the message
        	message = new MimeMessage(session);
    	}    	
        catch(Exception e){			
				e.printStackTrace();
		}	
    }
    public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException{
    	res.setContentType("text/html");
		res.setHeader("Cache-Control","no-store"); 		
		PrintWriter out=res.getWriter();		
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>Mail Blaster</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		try{
		MultipartRequest multi = new MultipartRequest(req, "/home/virtual/site2/fst/var/www/html/temp");
		from_name="";
		from_email="";
		subject="";
		from_name=multi.getParameter("from_name");
		from_email=multi.getParameter("from_mail");
		subject=multi.getParameter("subject");
		if((from_name==null)||(from_email==null)||(subject==null)){
			out.println("<H2>Some of the Fields were left blank</H2>");
			out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
			out.println("</BODY></HTML>");
			return;
		}
		if(from_name.equals("")||from_email.equals("")||subject.equals("")){
			out.println("<H2>Some of the Fields were left blank</H2>");
			out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
			out.println("</BODY></HTML>");
			return;
		}
		if(checkmail(from_email));
		else{
			out.println("<H2>Invalid From Email Address</H2>");
			out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & try again");
			out.println("</BODY></HTML>");
			return;
		}
		Enumeration file = multi.getFileNames();
		if(file==null){
			out.println("<H2>The CSV or HTML File Field is left blank</H2>");
			out.println("<a href='javascript:history.go(-1)'>Click Here</a>to go back to previous page & Choose a Photo to Upload");
			out.println("</BODY></HTML>");
			return;
		}
		String file1= (String)file.nextElement();
		String file2=(String)file.nextElement();
		String temp_CSVFile = multi.getFilesystemName(file1);
		String temp_HTMLFile = multi.getFilesystemName(file2);
		int ex=temp_CSVFile.indexOf('.');
		String type = multi.getContentType(file1);
		String exten=temp_CSVFile.substring(ex).toLowerCase();			
		boolean goodfile;
		if(exten.equals(".csv")) goodfile=true;
		else{
			out.println("<H2>The 1st File you have selected to upload is not a CSV file</H2>");
			out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & Again Choose Photo to Upload");
			out.println("</BODY></HTML>");
			return;
		}
		ex=temp_HTMLFile.indexOf('.');
		type = multi.getContentType(file2);
		exten=temp_HTMLFile.substring(ex).toLowerCase();
		goodfile=false;
		if(exten.equals(".htm")||exten.equals(".html")) goodfile=true;
		else{
			out.println("<H2>The 2nd File you have selected to upload is not a HTML file</H2>");
			out.println("<a href='javascript:history.go(-1)'>Click Here</a>  to go back to previous page & Again Choose Photo to Upload");
			out.println("</BODY></HTML>");
			return;
		}
		File csv = multi.getFile(file1);	
		File htm = multi.getFile(file2);
		CSVFile=csv.getName();
		HTMLFile=htm.getName();
		out.println("Starting MailBlasterServlet...<br>");
		sendMail(out);
        out.println("<br>Mail Blasting Completed.");
        out.println("</BODY></HTML>");
        csv.delete();
        htm.delete();	
        }
        catch(Exception e){
			try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
				e.printStackTrace();
				out.println("<br><br><a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Try Again");
				out.println("</BODY></HTML>");
			}
			catch (Exception ignored) { }
			
		}	
		
    }
	public void sendMessage() throws Exception
    {
        message.saveChanges();
        Transport trans = session.getTransport("smtp");
        trans.connect("199.4.119.250","ankur","passwd");
        //trans.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.passwd"));
        trans.sendMessage(message, message.getAllRecipients());
        trans.close();
    }

    /**
     *  This method does the actual mailing
     *
     *@return                the out put text
     *@exception  Exception  Description of the Exception
     */
    public void sendMail(PrintWriter out) throws Exception
    {
        FileReader in = new FileReader(CSVFile);
        FileReader html=new FileReader(HTMLFile);
        //create the buffered Reader
        BufferedReader reader = new BufferedReader(in);
        BufferedReader htmlreader = new BufferedReader(html);
        // The first would be column names
        String columnNames = reader.readLine();
        int ptr=0;
        int nextLimit=0;
        String fieldName="";
        int nameLocation=0;
        int emailLocation=0;
        int colno=1;
        while(ptr!=-1){
        	nextLimit=columnNames.indexOf(",",ptr);
        	if(nextLimit!=-1){
        		fieldName=columnNames.substring(ptr,nextLimit);
        		if(fieldName.compareToIgnoreCase("First Name")==0)nameLocation=colno;
        		if(fieldName.compareToIgnoreCase("Email")==0)emailLocation=colno;
        		if(emailLocation!=0&&nameLocation!=0) break;
        		ptr=nextLimit+1;
        		colno++;
        	}
        	else{
        		fieldName=columnNames.substring(ptr);
        		if(fieldName.compareToIgnoreCase("First Name")==0)nameLocation=colno;
        		if(fieldName.compareToIgnoreCase("Email")==0)emailLocation=colno;
        		ptr=-1;        	
        	}
        } 
        if(emailLocation==0||nameLocation==0)throw new Exception("Invalid CSV File Format");       
        String line ="";
        //store all the lines in hashtable with key as email
        Hashtable htable = new Hashtable();
        while ((line = reader.readLine()) != null)
        {
            // ignore if the line is empty.
            if (line.trim().length() == 0)
                continue;
            int i=0;
            colno=1;
            ptr=0;
            nextLimit=0;
            while(colno<nameLocation&&ptr!=-1){
            	nextLimit=line.indexOf(",",ptr);
        		if(nextLimit==-1)ptr=-1;
        		else {
        			ptr=nextLimit+1;
        			colno++;
        		}
        	}  
        	i=line.indexOf(",",ptr);
        	String name="";
        	if(i!=-1) name= line.substring(ptr, i);
        	else name = line.substring(ptr);
        	i=0;
            colno=1;
            ptr=0;
            nextLimit=0;
            while(colno<emailLocation&&ptr!=-1){
            	nextLimit=line.indexOf(",",ptr);
        		if(nextLimit==-1)ptr=-1;
        		else {
        			ptr=nextLimit+1;
        			colno++;
        		}
        	}  
        	i=line.indexOf(",",ptr);
        	String email="";
        	if(i!=-1) email= line.substring(ptr, i);
        	else email = line.substring(ptr);
        	
            //check if htable already has this email id, if yes do nothing.
            if (htable.containsKey(email));
            else htable.put(email,name);
        }
        // close streams
        in.close();
        reader.close();
        Enumeration enum = htable.keys();
        StringBuffer sb = new StringBuffer();		
		while ((line = htmlreader.readLine()) != null) {
	   		sb.append(line);
	   		sb.append("\n");
		}
	    int nametag=0;
	    int mailtag=0;
        while (enum.hasMoreElements())
        {
        	try{
        		String email = (String) enum.nextElement();
            	String name="";
            	name = (String) htable.get(email);
            	//send mail one by one.
            	message.setFrom(new InternetAddress(from_email, from_name == null ? "" : from_name));
            	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email,false));
    	    	message.setSubject(subject);        
   				StringBuffer sbtemp=new StringBuffer(sb.toString());
   				nametag=0;
   				mailtag=0;
   				while(nametag!=-1){
	   				nametag=sbtemp.indexOf("%name%");
   					if(nametag!=-1)sbtemp.replace(nametag,nametag+6,name);
   				}
   				while(mailtag!=-1){
	   				mailtag=sbtemp.indexOf("%email%");
   					if(mailtag!=-1)sbtemp.replace(mailtag,mailtag+7,email);
   				}    	
   				message.setContent(sbtemp.toString(),"text/html");   
    			message.setSentDate(new Date());
        		sendMessage();
	        	out.println("Mail sent to: "+name+"("+email+")<br>");             
	        }
	        catch(Exception e){
	        	continue;
	        }	        
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
    		if(domain.equals("com")||domain.equals("net")||domain.equals("org")||domain.equals("edu")||domain.equals("int")||domain.equals("mil")||domain.equals("gov")||domain.equals("arpa")||domain.equals("biz")||domain.equals("aero")||domain.equals("name")||domain.equals("coop")||domain.equals("info")||domain.equals("pro")||domain.equals("museum"));
    		else return false;
    	}
    	atsign=value.indexOf(" ");    	
    	if(atsign!=-1)return false;
    	return true;
    }
}
