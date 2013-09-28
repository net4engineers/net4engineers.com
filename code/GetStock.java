//Push Application
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
public class GetStock extends HttpServlet{
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
	public void doGet(HttpServletRequest request,HttpServletResponse response) 
	throws ServletException,IOException{
		Connection con=null;
		String temp_id="";
		int UserID=0;		
		temp_id = request.getParameter("UserID");
		UserID = Integer.parseInt(temp_id);
		/* Set Content-type Header */
		response.setContentType("text/vnd.wap.wml");
		PrintWriter out = new PrintWriter(response.getOutputStream());
		/* Write the Response */
		out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\"");
        out.println(" \"http://www.wapforum.org/DTD/wml_1.1.xml\">");
        out.println("<wml>");
		out.println("<template>");
		out.println("<do type='prev' label='Back'>");
		out.println("<prev/>");
		out.println("</do>");
		out.println("</template>");
		try{
			con = pool.getConnection();	
			Statement stmt = con.createStatement();
			/* Information on the Customer From the Database */
			ResultSet rs=stmt.executeQuery("select * from MobileUsers where UserID="+UserID);
			int c=0;			
			int Active=0;
			while(rs.next()){				
				c++;
				Active=rs.getInt("Active");
			}
			rs.close();
			if(c==0){ //invalid userid
				out.println("<card id='card1' title='Sorry'>");
				out.println("<p>");
				out.println("Your ID Is Not Valid.");
				out.println("</p>");
				out.println("</card>");
				out.println("</wml>");
				out.flush();
				out.close();
				return;
			}
			if(Active==0){ //Account not activated
				out.println("<card id='card1' title='Sorry'>");
				out.println("<p>");
				out.println("Your Account is not Activated Till Now.");
				out.println("</p>");
				out.println("</card>");
				out.println("</wml>");
				out.flush();
				out.close();
				return;
			}
			int Satyam=0,Infosys=0,Wipro=0,Reliance=0;
			rs = stmt.executeQuery("select Satyam,Infosys,Wipro,Reliance from MobileUsers where UserID="+UserID);
			if(rs.next()){
				Satyam = rs.getInt("Satyam");
				Infosys = rs.getInt("Infosys");
				Wipro = rs.getInt("Wipro");
				Reliance = rs.getInt("Reliance");
			}
			rs.close();
			if((Satyam==0)&&(Infosys==0)&&(Wipro==0)&&(Reliance==0)){
				out.println("<card id='card1' title='Sorry'>");
				out.println("<p>");
				out.println("You have not Subscribed for Any Stock Quote.");
				out.println("</p>");
				out.println("</card>");
				out.println("</wml>");
				out.flush();
				return;
			}
			out.println("<card id='card1' title='Stock Quotes'>");
			out.println("<p>");
			out.println("<table columns='4' align='LL'>");
			out.println("<tr><td><strong>Company</strong></td>");
			out.println("<td><strong>High</strong></td>");
			out.println("<td><strong>Low</strong></td>");
			out.println("<td><strong>Close</strong></td></tr>");
			float High=0,Low=0,Close=0;
			if(Satyam==1){
				rs = stmt.executeQuery("select High,Low,Close from StockPrice where Company='Satyam'");
				if(rs.next()){
					High=rs.getFloat("High");
					Low=rs.getFloat("Low");
					Close=rs.getFloat("Close");
				}
				rs.close();
				out.println("<tr><td>Satyam</td>");
				out.println("<td>"+High+"</td>");
				out.println("<td>"+Low+"</td>");
				out.println("<td>"+Close+"</td></tr>");
			}
			if(Infosys==1){
				rs = stmt.executeQuery("select High,Low,Close from StockPrice where Company='Infosys'");
				if(rs.next()){
					High=rs.getFloat("High");
					Low=rs.getFloat("Low");
					Close=rs.getFloat("Close");
				}
				rs.close();
				out.println("<tr><td>Infosys</td>");
				out.println("<td>"+High+"</td>");
				out.println("<td>"+Low+"</td>");
				out.println("<td>"+Close+"</td></tr>");
			}
			if(Wipro==1){
				rs = stmt.executeQuery("select High,Low,Close from StockPrice where Company='Wipro'");
				if(rs.next()){
					High=rs.getFloat("High");
					Low=rs.getFloat("Low");
					Close=rs.getFloat("Close");
				}
				rs.close();
				out.println("<tr><td>Wipro</td>");
				out.println("<td>"+High+"</td>");
				out.println("<td>"+Low+"</td>");
				out.println("<td>"+Close+"</td></tr>");
			}
			if(Reliance==1){
				rs = stmt.executeQuery("select High,Low,Close from StockPrice where Company='Reliance'");
				if(rs.next()){
					High=rs.getFloat("High");
					Low=rs.getFloat("Low");
					Close=rs.getFloat("Close");
				}
				rs.close();
				out.println("<tr><td>Reliance</td>");
				out.println("<td>"+High+"</td>");
				out.println("<td>"+Low+"</td>");
				out.println("<td>"+Close+"</td></tr>");
			}	
			out.println("</table>");
			out.println("</p>");
			out.println("</card>");
			out.println("</wml>");
			out.flush();
		}		
		catch(Exception ignored){						
		}
		finally{
			if(con!=null) pool.returnConnection(con);
			out.close();		
		}				
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{ 
		doGet(request,response);
	}	
}