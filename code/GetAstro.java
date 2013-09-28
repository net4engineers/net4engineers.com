//Push Application
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
public class GetAstro extends HttpServlet{
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
			String Astro="";
			rs = stmt.executeQuery("select Astro from MobileUsers where UserID="+UserID);
			if(rs.next()){
				Astro = rs.getString("Astro");
			}
			rs.close();
			if(Astro.equals("")){
				out.println("<card id='card1' title='Sorry'>");
				out.println("<p>");
				out.println("You have not Subscribed this service.");
				out.println("</p>");
				out.println("</card>");
				out.println("</wml>");
				out.flush();
				return;
			}
			String Prediction="";
			rs = stmt.executeQuery("select Prediction from AstroPred where Sign='"+Astro+"'");
			if(rs.next()){
				Prediction = rs.getString("Prediction");
			}
			rs.close();
			if(Prediction.equals("")){
				out.println("<card id='card1' title='Sorry'>");
				out.println("<p>");
				out.println("You have not Subscribed this service.");
				out.println("</p>");
				out.println("</card>");
				out.println("</wml>");
				out.flush();
				return;
			}
			out.println("<card id='card1' title='Your Todays Predictions'>");
			out.println("<p>");
			out.println(Prediction);
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