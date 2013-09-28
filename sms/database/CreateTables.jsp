<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" %>
<%
Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
Connection con=DriverManager.getConnection("jdbc:mysql://localhost/net4engineers_com?user=net4engineers&password=database");			
Statement stmt=con.createStatement();	
stmt.executeUpdate("drop table if exists sms_mapping");

stmt.executeUpdate("CREATE TABLE sms_mapping (prefix smallint PRIMARY KEY,country_code smallint default NULL,address char(30) NOT NULL)");

stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9810, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9811, NULL, '@delhi.hutch.co.in')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9815, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9816, 91, '@airtelmail.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9818, 91, '@airtelmail.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9830, NULL, '@command.co.in')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9831, 91, '@airtelkol.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9841, NULL, '@rpgmail.net')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9842, NULL, '@airsms.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9890, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9891, NULL, '@ideacellular.net')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9892, 91, '@airtelmail.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9893, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9896, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9897, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9898, 91, '@airtelindia.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9812, NULL, '@escotelmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9821, NULL, '@bplmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9822, NULL, '@ideacellular.net')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9823, NULL, '@bplmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9824, NULL, '@ideacellular.net')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9825, NULL, '@celforce.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9837, NULL, '@escotelmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9840, 91, '@airtelchennai.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9843, NULL, '@bplmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9845, 91, '@airtelmail.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9846, NULL, '@bplmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9847, NULL, '@escotelmobile.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9848, NULL, '@ideacellular.net')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9849, 91, '@airtelap.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9894, 91, '@airteltn.com')");
stmt.executeUpdate("INSERT INTO sms_mapping VALUES (9895, 91, '@airtelmail.com')");

out.println("tables created are:- sms_mapping");

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
