import java.net.*;
import java.io.*;
import java.util.Date;
import java.sql.*;
class ParseHT{
	//public static void main(String args[]) throws Exception{
	public static void parse() throws Exception{	
		ConnectionPool pool=null;
		Connection con=null;
		int c;
		//URL hp=new URL("http://localhost:8080/ht.htm");
		URL hp=new URL("http://www.hindustantimes.com/");
		URLConnection hpCon=hp.openConnection();
		int i=hpCon.getContentLength();
		InputStream input=hpCon.getInputStream();
		StringBuffer sf=new StringBuffer(i);
		while(((c=input.read())!=-1)&&(--i>0)){
			sf.append((char) c);
		}
		StringBuffer news=new StringBuffer(500);
		String page1=new String(sf);
		String page=new String(sf);
		int found=0;
		int bck=0;
		int newfound=0;
		int count=0;
		while(true){
			found=page1.indexOf("class=\"mnst1\"");		
			if(found==-1)break;
			bck=found;
			while(page1.charAt(bck)!='<')bck--;
			bck++;
			if(page1.charAt(bck)!='a'){
				page1=page1.substring(found+1);
				continue;
			}
			else{
				while(page1.charAt(found)!='>')found++;
				found++;
				page1=page1.substring(found);						
				//copy till </a>
				newfound=page1.indexOf("</a>");
				for(count=0;count<newfound;count++){
					news.append(page1.charAt(count));
				}
				news.append(".");
			}
			page1=page1.substring(newfound);
		}
		while(true){
			found=page.indexOf("class=\"mnst2\"");
			if(found==-1)break;
			bck=found;
			while(page.charAt(bck)!='<')bck--;
			bck++;
			if(page.charAt(bck)!='a'){
				page=page.substring(found+1);
				continue;
			}
			else{
				while(page.charAt(found)!='>')found++;
				found++;
				page=page.substring(found);						
				//copy till </a>
				newfound=page.indexOf("</a>");
				for(count=0;count<newfound;count++){
					news.append(page.charAt(count));
				}
				news.append(".");
			}
			page=page.substring(newfound);
		}
		String finalnews=new String(news);
		finalnews=finalnews.replace('\'',' ');
		while(true){
			found=finalnews.indexOf("<i>");		
			if(found==-1)break;
			finalnews=finalnews.substring(0,found)+finalnews.substring(found+3);
		}
		while(true){
			found=finalnews.indexOf("</i>");		
			if(found==-1)break;
			finalnews=finalnews.substring(0,found)+finalnews.substring(found+4);
		}
		input.close();
		try{
			pool=new ConnectionPool(1,1);			
		 	con=pool.getConnection();
			Statement stmt=con.createStatement();
			//System.out.println(finalnews);	
			int res=stmt.executeUpdate("Update News Set TodayNews='"+finalnews+"' where Source='HindustanTimes.com'");
			//System.out.println(res);							
		}
		catch(Exception ignored){
			ignored.printStackTrace(System.out);			
		}
		finally{
			
				if(con!=null) pool.returnConnection(con);
		}
	}
}