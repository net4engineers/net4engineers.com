import java.sql.*;
import java.io.*;
import java.util.*;
//import SendMail;
class SendQuote{
	//public static void main(String args[]) throws Exception{
	public static void send() throws Exception{
		ConnectionPool pool=null;
		Connection con=null;
		try{
			pool=new ConnectionPool(1,1);			
			con=pool.getConnection();
			Statement stmt=con.createStatement();			
			String Company[]=new String[4];
			Company[0]="Satyam";
			Company[1]="Infosys";
			Company[2]="Wipro";
			Company[3]="Reliance";	
			int c=0,count,i,j;
			float High=0,Low=0,Close=0;
			long MobileNo[];
			ResultSet rs=null;
			String to[];
			int prefix=0;
			int country_code=0;
			String address=""; 
			String sub="Stock Quotes";
			String msg;
			SendMail sm;
			String part[];
			int no_parts=0;
			String email="Mobile@Net4Engineers.com";
			for(count=0;count<Company.length;count++){
				rs=stmt.executeQuery("select COUNT(*) from MobileUsers where "+Company[count]+"=1");
				if(rs.next())c=rs.getInt("COUNT(*)");
				rs.close();
				MobileNo=new long[c];
				to=new String[c];
				i=0;
				rs=stmt.executeQuery("select MobileNo from MobileUsers where "+Company[count]+"=1");
				while(rs.next()){
					MobileNo[i]=rs.getLong("MobileNo");
					i++;
				}
				rs.close();
				rs=stmt.executeQuery("select High,Low,Close from StockPrice where Company='"+Company[count]+"'");
				if(rs.next()){
					High=rs.getFloat("High");
					Low=rs.getFloat("Low");
					Close=rs.getFloat("Close");
				}
				rs.close();
				for(j=0;j<c;j++){
					prefix=(int)(MobileNo[j]/1000000);
					rs=stmt.executeQuery("select address,country_code from sms_mapping where prefix="+prefix);
					if(rs.next()){
						address=rs.getString("address");
						country_code=rs.getInt("country_code");
						if (rs.wasNull()) country_code=0; 
					}
					rs.close();
					if(country_code==0)
					to[j]=MobileNo[j]+address;
					else
					to[j]=country_code+""+MobileNo[j]+address;
				}				
				msg="Your Stock Quotes for "+Company[count]+":\n High:"+High+"\nLow:"+Low+"\nClose:"+Close+"\n-Mobile.Net4Engineers.com";
				sm=new SendMail(msg,email,to,sub);
				if(sm.send()) ;//success
				else ;//failure
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
