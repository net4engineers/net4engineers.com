<%@ page import="java.util.*,java.io.*,java.sql.*,ConnectionPool"%>
<%
		ConnectionPool pool;
		try{
			pool = new ConnectionPool(1,1);
		}
		catch(Exception e){
			throw new UnavailableException(this,"could not create a connection");
	    }
		Connection con = null;
		try{
			con = pool.getConnection();
			Statement stmt = con.createStatement();
%>
<script language="JavaScript1.2">
/*
message scroller- 
*/
//
var scrollerwidth=170
var scrollerheight=150
var scrollerbgcolor='#Ffffff'
//
var scrollerbackground='scrollerback.gif'
//
var messages=new Array()
<%
			ResultSet rs = stmt.executeQuery("Select * from CPanel_news");
			String news_data="";
			int i=0;
			while(rs.next())
			{
				news_data=rs.getString("news_data");
				out.println("messages["+i+"]=\"<font size='2' color='#008080'><center><b><br><br>"+news_data+"</b></center></font>\"");
				i++;
			}			
			rs.close();
%>
if (messages.length>1)
i=2
else
i=0
function move(whichlayer){
tlayer=eval(whichlayer)
if (tlayer.top>0&&tlayer.top<=5){
tlayer.top=0
setTimeout("move(tlayer)",3000)
setTimeout("move(document.main.document.second)",3000)
return
}
if (tlayer.top>=tlayer.document.height*-1){
tlayer.top-=5
setTimeout("move(tlayer)",100)
}
else
{
tlayer.top=scrollerheight
tlayer.document.write(messages[i])
tlayer.document.close()
if (i==messages.length-1)
i=0
else
i++
}
}

function move2(whichlayer){
tlayer2=eval(whichlayer)
if (tlayer2.top>0&&tlayer2.top<=5){
tlayer2.top=0
setTimeout("move2(tlayer2)",3000)
setTimeout("move1(document.main.document.first)",3000)
return
}
if (tlayer2.top>=tlayer2.document.height*-1){
tlayer2.top-=5
setTimeout("move2(tlayer2)",100)
}
else{
tlayer2.top=scrollerheight
tlayer2.document.write(messages[i])
tlayer2.document.close()
if (i==messages.length-1)
i=0
else
i++
}
}

function move3(whichdiv){
tdiv=eval(whichdiv)
if (tdiv.style.pixelTop>0&&tdiv.style.pixelTop<=5){
tdiv.style.pixelTop=0
setTimeout("move3(tdiv)",3000)
setTimeout("move4(second2)",3000)
return
}
if (tdiv.style.pixelTop>=tdiv.offsetHeight*-1){
tdiv.style.pixelTop-=5
setTimeout("move3(tdiv)",100)
}
else{
tdiv.style.pixelTop=scrollerheight
tdiv.innerHTML=messages[i]
if (i==messages.length-1)
i=0
else
i++
}
}

function move4(whichdiv){
tdiv2=eval(whichdiv)
if (tdiv2.style.pixelTop>0&&tdiv2.style.pixelTop<=5){
tdiv2.style.pixelTop=0
setTimeout("move4(tdiv2)",3000)
setTimeout("move3(first2)",3000)
return
}
if (tdiv2.style.pixelTop>=tdiv2.offsetHeight*-1){
tdiv2.style.pixelTop-=5
setTimeout("move4(second2)",100)
}
else{
tdiv2.style.pixelTop=scrollerheight
tdiv2.innerHTML=messages[i]
if (i==messages.length-1)
i=0
else
i++
}
}

function startscroll(){
if (document.all){
move3(first2)
second2.style.top=scrollerheight
second2.style.visibility='visible'
}
else if (document.layers){
document.main.visibility='show'
move1(document.main.document.first)
document.main.document.second.top=scrollerheight+5
document.main.document.second.visibility='show'
}
}

window.onload=startscroll

                        </script> <ILAYER id="main" visibility="hide" background="&amp;{scrollerbackground};" bgcolor="&amp;{scrollerbgcolor};" height="&amp;{scrollerheight};" width="&amp;{scrollerwidth};"><LAYER id="first" width="&amp;{scrollerwidth};" top="0" left="0"> <script
                language="JavaScript1.2">
if (document.layers)
document.write(messages[0])
                        </script> </LAYER><LAYER id="second" visibility="hide" width="&amp;{scrollerwidth};" top="0" left="0"> <script language="JavaScript1.2">
if (document.layers)
document.write(messages[1])
                        </script> </LAYER></ILAYER> <script
                language="JavaScript1.2">
if (document.all){
document.writeln('<span id="main2" style="position:relative;width:'+scrollerwidth+';height:'+scrollerheight+';overflow:hiden;background-color:'+scrollerbgcolor+' ;background-image:url('+scrollerbackground+')">')
document.writeln('<div style="position:absolute;width:'+scrollerwidth+';height:'+scrollerheight+';clip:rect(0 '+scrollerwidth+' '+scrollerheight+' 0);left:0;top:0">')
document.writeln('<div id="first2" style="position:absolute;width:'+scrollerwidth+';left:0;top:1;">')
document.write(messages[0])
document.writeln('</div>')
document.writeln('<div id="second2" style="position:absolute;width:'+scrollerwidth+';left:0;top:0;visibility:hidden">')
document.write(messages[1])
document.writeln('</div>')
document.writeln('</div>')
document.writeln('</span>')
}
</script> 
<%
		}
		catch(Exception e){
        	try{
				out.println("<H2>An Error has occured: "+e.getMessage()+"</H2>");
				out.println("<br><br><a href='javascript:history.go(-1)'>Click Here</a> to go back to previous page & Try Again");
				con.rollback();				
			}
			catch (Exception ignored) { }
		}
		finally{
			if(con!=null) pool.returnConnection(con);
			out.close();		
		}
%>		