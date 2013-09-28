import java.sql.*;
import java.io.*;
import java.util.*;
//import SendMail;
class SendAstro{
	//public static void main(String args[]) throws Exception{
	public static void send() throws Exception{
		ConnectionPool pool=null;
		Connection con=null;
		try{
			pool=new ConnectionPool(1,1);			
			con=pool.getConnection();
			Statement stmt=con.createStatement();			
			String sign[]=new String[2];
			sign[0]="Aquarius";
			sign[1]="Virgo";
			int c=0,count,i,j;
			String Prediction="";
			long MobileNo[];
			ResultSet rs=null;
			String to[];
			int prefix=0;
			int country_code=0;
			String address=""; 
			String sub="Astro";
			String msg;
			SendMail sm;
			String part[];
			int no_parts=0;
			String email="Mobile@Net4Engineers.com";
			for(count=0;count<sign.length;count++){
				rs=stmt.executeQuery("select COUNT(*) from MobileUsers where Astro='"+sign[count]+"'");
				if(rs.next())c=rs.getInt("COUNT(*)");
				rs.close();
				MobileNo=new long[c];
				to=new String[c];
				i=0;
				rs=stmt.executeQuery("select MobileNo from MobileUsers where Astro='"+sign[count]+"'");
				while(rs.next()){
					MobileNo[i]=rs.getLong("MobileNo");
					i++;
				}
				rs.close();
				rs=stmt.executeQuery("select Prediction from AstroPred where Sign='"+sign[count]+"'");
				if(rs.next())Prediction=rs.getString("Prediction");
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
				msg="Your Todays Prediction:"+Prediction;
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
