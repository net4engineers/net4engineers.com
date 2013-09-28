import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RunAlways extends HttpServlet implements Runnable {
	java.util.Date ustimeNow ;   
	java.util.Date timeNow;
  	Thread runner;                       
	long ustime,indtime;
	int hrs,min;
	boolean newsFlag=false,astroFlag=false,stockFlag=false;
	public void init() throws ServletException {
		System.out.println("AlwaysRun Started");
	    runner = new Thread(this);
	    runner.setPriority(Thread.MIN_PRIORITY);  // be a good citizen
	    runner.start();
  	}
  	public void run() { 
		try{			  	   
	    while (true) {    	        
    		timeNow=new java.util.Date();
    		ustime=timeNow.getTime();
			indtime=ustime+(13*60*60*1000)+(48*60*1000);
			timeNow = new java.util.Date(indtime);
			hrs=timeNow.getHours();
			min=timeNow.getMinutes();
			if(hrs==0&&min==0){
				newsFlag=true;
				astroFlag=true;
				stockFlag=true;
			}
			if(newsFlag&&hrs>=20){
				newsFlag=false;
				ParseHT ht=new ParseHT();
				ht.parse();
				ParseTOI toi=new ParseTOI();
				toi.parse();
				SendNews snd=new SendNews();
				snd.send();
				System.out.println("News Sent");
			}
			if(astroFlag&&hrs>=8){
				astroFlag=false;
				ParseAstro astro=new ParseAstro();
				astro.parse();
				SendAstro snd=new SendAstro();
				snd.send();
				System.out.println("Predictions Sent");
			}
			if(stockFlag&&hrs>=18){
				stockFlag=false;
				ParseQuote quote=new ParseQuote();
				quote.parse();
				SendQuote snd=new SendQuote();
				snd.send();
				System.out.println("Stocks Sent");
			}
      		try {
	        	runner.sleep(200);
      		}
      	catch (InterruptedException ignored) { }
    	}
    	}
    	catch (Exception ignored) { 
    		ignored.printStackTrace(System.out);
    	}	
  	}
  	public void doGet(HttpServletRequest req, HttpServletResponse res)
                               	throws ServletException, IOException {    
  	}
  	public void destroy() {
	    runner.stop();
  	}
}