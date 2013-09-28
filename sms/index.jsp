<%@ page import = "java.sql.*" %>
<%! int prefix=0; %>
<%
Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
Connection con=DriverManager.getConnection("jdbc:mysql://localhost/net4engineers_com?user=net4engineers&password=database");			
Statement stmt=con.createStatement();	
%>
<html>

<head>
<meta name="GENERATOR" content="Microsoft FrontPage 3.0">
<title>SMS@Net4Engineers.com</title>
<script language="javascript">

var fieldalias="Email address field";

function emailcheck(cur)
{
var string1=cur.value

if ((string1.indexOf("@")==-1)||(string1.indexOf(".")==-1))
{
 return false
}
else
return true
}


function verify(suf, nam,element1, msg,count){
var passed=false

if (suf.value=='')
 {
  alert("Please fill in Cell Number ")
  suf.focus()
 }
else
 if ( suf.value.length !=6 )
  {
  alert("ERROR: Please fill  remaining 6 digits in the Cell number. For example if 9810911750 is cell number then select 9810 from popdown menu then type 911750 in text box ")
  suf.focus()
  }
else
 if (nam.value=='')
 {
  alert("ERROR : Please fill in Your Name")
  nam.focus()
 }
 else
 if (element1.value=='')
 {
 alert("ERROR : Please fill out the "+fieldalias+"!")
 element1.focus()
 }
else
 if (!emailcheck(element1))
 {
 alert("ERROR : Please enter valid email Address Example: yourname@net4engineers.com")
 element1.focus()
 }
else
 if(msg.value=='')
    {
     alert("ERROR : Your Message can't be blank. Type a message")
     msg.focus()
    }
else
 if(count.value>80)
  {
  alert("ERROR : Total number of characters in your SMS can n't exceed 80. Please modify Message field")
  msg.focus()
  }
else
  passed=true
return passed

}

</script>
</head>

<body>
<!--webbot bot="HTMLMarkup" startspan TAG="XBOT" --><IFRAME SRC="../header.html" NAME="head"  WIDTH="100%" HEIGHT="175" MARGINWIDTH="0" MARGINHEIGHT="0" FRAMEBORDER="0" noresize SCROLLING=NO><!--webbot bot="HTMLMarkup" endspan
-->
<!--webbot bot="HTMLMarkup" startspan TAG="XBOT" --></IFRAME>
<!--webbot bot="HTMLMarkup"
endspan -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td width="100%"><p align="left"><font face="Verdana" color="#808080"><strong><small>SMS@Net4Engineers.com</small></strong></font></td>
  </tr>
</table>
<table width="100%" border="1"
cellpadding="0" cellspacing="0" bordercolor="#C0C0C0" bordercolorlight="#C0C0C0" bgcolor="#C0C0C0">
  <tr> 
    <td width="100%"><font face="Verdana" color="#FFFFFF"><small><strong>Send 
      SMS </strong></small></font></td>
  </tr>
</table>
<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#C0C0C0"
bordercolorlight="#C0C0C0" bordercolordark="#FFFFFF" bgcolor="#F0F0F0">
  <tr> 
    <td width="102%"><p align="center"><font color="#8080FF"><br>
        </font>
      <table width="40%" border="1" bgcolor="#EBEBEB" bordercolorlight="#CCCCCC"
bordercolordark="#666666">
        <tr> 
          <td><div align="center">
              <center>
                <table width="95%" border="0" cellspacing="0"
    cellpadding="0" align="center" bordercolorlight="#999999" bordercolordark="#666666"
    height="0%" bgcolor="#EbEbEb">
                  <tr> 
                    <td><form method="POST" action="/servlet/SendSMS" name="SendMsg"
        onSubmit="return verify(this.suffix, this.name, this.email,  this.message, this.char_count)">
                        <table width="367" border="1" cellspacing="0" cellpadding="0" align="center"
          bordercolorlight="#CCCCCC" bordercolordark="#EBEBEB" bgcolor="#EBEBEB" height="100%">
                          <tr> 
                            <td width="230"><font face="Arial" size="2"><b>Mobile 
                              Number</b></font></td>
                            <td width="133"><select size="1" name="prefix"
              style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 10pt; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF">
                                <%
ResultSet rs=stmt.executeQuery("select * from sms_mapping ORDER BY prefix");
while(rs.next()){
prefix=rs.getInt("prefix");
%>
                                <option value="<%= prefix %>"><%= prefix %></option>
                                <%}
%>
                              </select> <input type="text" name="suffix" size="7" maxlength="6"
              style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 4px; BORDER-TOP: 1px solid; PADDING-LEFT: 4px; FONT-SIZE: 10pt; PADDING-BOTTOM: 1px; BORDER-LEFT: 1px solid; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF"> 
                            </td>
                          </tr>
                          <tr> 
                            <td width="230"><font face="Arial" size="2"><b><font
              face="Verdana, Arial, Helvetica, sans-serif">Your Name :</font> 
                              </b></font></td>
                            <td width="133"><font face="Arial" size="2"><b>
                              <input type="text" name="name"
              onKeyUp="this.form.char_count.value=this.value.length + this.form.email.value.length + this.form.message.value.length"
              style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 4px; BORDER-TOP: 1px solid; PADDING-LEFT: 4px; FONT-SIZE: 10pt; PADDING-BOTTOM: 1px; BORDER-LEFT: 1px solid; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF"
              size="20">
                              </b></font></td>
                          </tr>
                          <tr> 
                            <td width="230"><font face="Arial" size="2"><b><font
              face="Verdana, Arial, Helvetica, sans-serif">Your Email id:</font> 
                              </b></font></td>
                            <td width="133"><input type="text" name="email"
              style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 4px; BORDER-TOP: 1px solid; PADDING-LEFT: 4px; FONT-SIZE: 10pt; PADDING-BOTTOM: 1px; BORDER-LEFT: 1px solid; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF"
              onKeyUp="this.form.char_count.value=this.value.length+this.form.name.value.length+this.form.message.value.length"
              size="20"> </td>
                          </tr>
                          <tr> 
                            <td colspan="2" width="361"><div align="center">
                                <center>
                                  <p><font face="Arial" size="2"><b><font
              face="Verdana, Arial, Helvetica, sans-serif">Message : </font><br>
                                    <textarea rows="3" name="message" cols="35"
              style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 4px; BORDER-TOP: 1px solid; PADDING-LEFT: 4px; FONT-SIZE: 10pt; PADDING-BOTTOM: 1px; BORDER-LEFT: 1px solid; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF"
              onKeyUp="this.form.char_count.value=this.value.length+this.form.name.value.length+this.form.email.value.length"></textarea>
                                    </b></font>
                                </center>
                              </div></td>
                          </tr>
                          <tr align="center"> 
                            <td colspan="2" width="361"><div align="center">
                                <center>
                                  <p><font
              face="Verdana, Arial, Helvetica, sans-serif" size="2">Send Confirmation 
                                    Mail 
                                    <select              name="confirmation"
              style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 4px; BORDER-TOP: 1px solid; PADDING-LEFT: 4px; FONT-SIZE: 10pt; PADDING-BOTTOM: 1px; BORDER-LEFT: 1px solid; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF"
              size="1">
                                      <option value="no" selected>No </option>
                                      <option value="yes">Yes</option>
                                    </select>
                                    </font>
                                </center>
                              </div></td>
                          </tr>
                          <tr align="center"> 
                            <td colspan="2" width="361"><div align="center">
                                <center>
                                  <p>
                                    <input type="text"
              name="char_count" size="3" maxlength="3"
              style="BORDER-RIGHT: 1px solid; PADDING-RIGHT: 4px; BORDER-TOP: 1px solid; PADDING-LEFT: 4px; FONT-SIZE: 10pt; PADDING-BOTTOM: 1px; BORDER-LEFT: 1px solid; PADDING-TOP: 1px; BORDER-BOTTOM: 1px solid; FONT-FAMILY: Verdana; BACKGROUND-COLOR: #FFFFFF">
                                    <font face="Verdana, Arial, Helvetica, sans-serif" size="2">Character 
                                    Count (Maximum <b>80</b> Chararcters)</font>
                                </center>
                              </div></td>
                          </tr>
                          <tr align="center"> 
                            <td colspan="2" align="center" width="361"><div align="center">
                                <center>
                                  <p>
                                    <input
              type="submit" value="Send SMS" name="sub">
                                </center>
                              </div></td>
                          </tr>
                        </table>
                      </form></td>
                  </tr>
                </table>
              </center>
            </div></td>
        </tr>
      </table>
      <p align="center"><font color="#8080FF"><br>
        </font><br>
    </td>
  </tr>
</table>
<div align="center"><center>
  </center></div>
</body>
</html>
<%
rs.close();
stmt.close();
con.close();
%>
<!--webbot bot="HTMLMarkup" startspan TAG="XBOT" --><IFRAME SRC="../footer.html" NAME="bol" WIDTH="100%" HEIGHT="97" MARGINWIDTH="0" MARGINHEIGHT="0" FRAMEBORDER="0" noresize SCROLLING=NO><!--webbot bot="HTMLMarkup" endspan
-->
<!--webbot bot="HTMLMarkup" startspan TAG="XBOT" --></IFRAME><!--webbot bot="HTMLMarkup"
endspan -->