import java.net.*;
import java.io.*;
import java.util.Date;
import java.sql.*;
class ParseAstro{
	//public static void main(String args[]) throws Exception{
	public static void parse() throws Exception{
		ConnectionPool pool=null;
		Connection con=null;
		int c,count,i;
		String sign[]=new String[2];
		sign[0]="Aquarius";
		sign[1]="Virgo";
		URL hp;
		URLConnection hpCon;
		InputStream input;
		StringBuffer sf;
		StringBuffer astro;
		String page1;
		int found;
		pool=new ConnectionPool(1,1);			
		con=pool.getConnection();
		Statement stmt=con.createStatement();
		try{
			for(count=0;count<sign.length;count++){
				//hp=new URL("http://localhost:8080/astro.htm");
				hp=new URL("http://astrology.rediff.com/astrology/sections/daily/daily.asp?sign="+sign[count]);
				hpCon=hp.openConnection();
				i=hpCon.getContentLength();
				input=hpCon.getInputStream();
				sf=new StringBuffer(i);
				while(((c=input.read())!=-1)&&(--i>0)){
					sf.append((char) c);
				}
				astro=new StringBuffer(500);
				page1=new String(sf);
				found=0;		
				//astro.append("Today's "+sign+"'s Predictions:\n");
				found=page1.indexOf("<font face=arial size=2>");		
				if(found==-1)return;
				page1=page1.substring(found);
				found=page1.indexOf("<p>");		
				if(found==-1)return;
				found+=3;
				while(page1.charAt(found)!='<'){
					astro.append(page1.charAt(found));
					found++;
				}			
				input.close();
				String finalastro=new String(astro);
				finalastro=finalastro.replace('\'',' ');				
				//System.out.println(finalnews);	
				int res=stmt.executeUpdate("Update AstroPred Set Prediction='"+finalastro+"' where Sign='"+sign[count]+"'");
				//System.out.println(res);
			}
		}
		catch(Exception ignored){
			ignored.printStackTrace(System.out);			
		}
		finally{			
				if(con!=null) pool.returnConnection(con);
		}
	}
}