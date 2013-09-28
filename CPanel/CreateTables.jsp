<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" %>
<%
Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
Connection con=DriverManager.getConnection("jdbc:mysql://localhost/net4engineers_com?user=net4engineers&password=database");			
Statement stmt=con.createStatement();	
stmt.executeUpdate("drop table if exists CPanel_mail");
stmt.executeUpdate("drop table if exists CPanel_news");
stmt.executeUpdate("CREATE TABLE CPanel_mail (email char(50) PRIMARY KEY,name char(50) NOT NULL)");
stmt.executeUpdate("CREATE TABLE CPanel_news (news_id SMALLINT PRIMARY KEY,news_data char(200) NOT NULL)");
out.println("tables created are:- CPanel_mail,CPanel_news");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

</body>
</html>
<%
con.close();
stmt.close();
%>
