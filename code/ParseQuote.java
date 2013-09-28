import java.net.*;
import java.io.*;
import java.util.Date;
import java.sql.*;
class ParseQuote{
	//public static void main(String args[]) throws Exception{
	public static void parse() throws Exception{
		ConnectionPool pool=null;
		Connection con=null;	
		int c,count,i,fgh;
		String Company[]=new String[4];
		Company[0]="Satyam";
		Company[1]="Infosys";
		Company[2]="Wipro";
		Company[3]="Reliance";		
		String co_name[]=new String[4];
		co_name[1]="Infosys+Technologies+Ltd";
		co_name[0]="Satyam+Computer+Services+Ltd";
		co_name[2]="Wipro+Ltd";
		co_name[3]="Reliance+Industries+Ltd";
		String exch="NSE";
		String code[]=new String[4];
		code[1]="INFOSYSTCH";
		code[0]="SATYAMCOMP";
		code[2]="WIPRO";
		code[3]="RELIANCE";
		pool=new ConnectionPool(1,1);			
		con=pool.getConnection();
		Statement stmt=con.createStatement();
		URL hp;
		URLConnection hpCon;
		InputStream input;
		StringBuffer sf;
		StringBuffer quote;
		String page1;
		int found;
		String temp="";
		float High=0,Low=0,Close=0;
		try{
			for(count=0;count<code.length;count++){
				//hp=new URL("http://localhost:8080/quote.html");		
				hp=new URL("http://www.timesofmoney.com/equity/co_price.jsp?co_name="+co_name[count]+"&exch="+exch+"&code="+code[count]);
				hpCon=hp.openConnection();
				i=hpCon.getContentLength();
				input=hpCon.getInputStream();
				sf=new StringBuffer(i);
				while(((c=input.read())!=-1)&&(--i>0)){
					sf.append((char) c);
				}
				quote=new StringBuffer(500);
				page1=new String(sf);
				found=0;		
				//quote.append("Stock Quotes for "+company+":");
				//quote.append("\nHigh:");
				found=page1.indexOf("<td width=\"10%\" align=\"center\">");		
				if(found==-1)return;
				while(page1.charAt(found)!='>')found++;
				found++;
				while(page1.charAt(found)!='<'){
					quote.append(page1.charAt(found));
					found++;
				}
				temp=quote.toString();
				while(true){
					fgh=temp.indexOf(",");
					if(fgh==-1)break;
					temp=temp.substring(0,fgh)+temp.substring(fgh+1);
				}
				High=Float.parseFloat(temp);
				//quote.append("\nLow:");
				quote=new StringBuffer();
				page1=page1.substring(found);
				found=page1.indexOf("<td width=\"10%\" align=\"center\">");		
				if(found==-1)return;
				while(page1.charAt(found)!='>')found++;
				found++;
				while(page1.charAt(found)!='<'){
					quote.append(page1.charAt(found));
					found++;
				}
				temp=quote.toString();
				while(true){
					fgh=temp.indexOf(",");
					if(fgh==-1)break;
					temp=temp.substring(0,fgh)+temp.substring(fgh+1);
				}
				Low=Float.parseFloat(temp);
				//quote.append("\nClose:");
				quote=new StringBuffer();
				page1=page1.substring(found);
				found=page1.indexOf("<td width=\"10%\" align=\"center\">");		
				if(found==-1)return;
				while(page1.charAt(found)!='>')found++;
				found++;
				while(page1.charAt(found)!='<'){
					quote.append(page1.charAt(found));
					found++;
				}
				temp=quote.toString();
				while(true){
					fgh=temp.indexOf(",");
					if(fgh==-1)break;
					temp=temp.substring(0,fgh)+temp.substring(fgh+1);
				}
				Close=Float.parseFloat(temp);
				//System.out.println(quote);			
				int res=stmt.executeUpdate("Update StockPrice Set High="+High+" ,Low="+Low+",Close="+Close+"  where Company='"+Company[count]+"'");
				//System.out.println(res);
				input.close();
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