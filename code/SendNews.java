import java.sql.*;
import java.io.*;
import java.util.*;
//import SendMail;
class SendNews{
	//public static void main(String args[]) throws Exception{
	public static void send() throws Exception{
		ConnectionPool pool=null;
		Connection con=null;
		try{
			pool=new ConnectionPool(1,1);			
			con=pool.getConnection();
			Statement stmt=con.createStatement();			
			String Source[]=new String[2];
			Source[0]="HindustanTimes.com";
			Source[1]="TimesOfIndia.com";
			int c=0,count,i,j;
			String TodayNews="";
			long MobileNo[];
			ResultSet rs=null;
			String to[];
			int prefix=0;
			int country_code=0;
			String address=""; 
			String sub="News";
			String msg;
			SendMail sm;
			String part[];
			int no_parts=0;
			String email="Mobile@Net4Engineers.com";
			for(count=0;count<Source.length;count++){
				rs=stmt.executeQuery("select COUNT(*) from MobileUsers where News="+(count+1));
				if(rs.next())c=rs.getInt("COUNT(*)");
				rs.close();
				MobileNo=new long[c];
				to=new String[c];
				i=0;
				rs=stmt.executeQuery("select MobileNo from MobileUsers where News="+(count+1));
				while(rs.next()){
					MobileNo[i]=rs.getLong("MobileNo");
					i++;
				}
				rs.close();
				rs=stmt.executeQuery("select TodayNews from News where Source='"+Source[count]+"'");
				if(rs.next())TodayNews=rs.getString("TodayNews");
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
				msg="Todays News:"+TodayNews;
				/*if(msg.length()>140){
					no_parts=(int)(msg.length()/140);
					if((msg.length()/140)!=0)no_parts++;
					part=new String[no_parts];
					part[0]=msg.substring(0,140)+"\nContd. in Next SMS";
					for(j=1;j<no_parts-1;j++){
						part[j]=msg.substring((140*j),(140*(j+1)))+"\nContd. in Next SMS";
					}					
					part[j]=msg.substring((140*j));
					for(j=0;j<no_parts;j++){
						sm=new SendMail(part[j],email,to,sub);
						if(sm.send()) ;//success
						else ;//failure
					}
				}
				else{
					sm=new SendMail(msg,email,to,sub);
					if(sm.send()) ;//success
					else ;//failure
				}*/
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
