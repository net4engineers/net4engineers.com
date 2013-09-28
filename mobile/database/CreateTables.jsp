<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" %>
<%
Class.forName("org.gjt.mm.mysql.Driver").newInstance(); 
Connection con=DriverManager.getConnection("jdbc:mysql://localhost/net4engineers_com?user=net4engineers&password=database");			
Statement stmt=con.createStatement();	
stmt.executeUpdate("drop table if exists MobileUsers");
stmt.executeUpdate("drop table if exists StockPrice");
stmt.executeUpdate("drop table if exists News");
stmt.executeUpdate("drop table if exists AstroPred");

stmt.executeUpdate("Create table MobileUsers (UserID int NOT NULL,RealName char(30) NOT NULL,UserName char(20) NOT NULL,Password char(10),MobileNo BIGINT PRIMARY KEY,Mail char(40) NOT NULL,Satyam TINYINT NOT NULL,Infosys TINYINT NOT NULL, Wipro TINYINT NOT NULL,Reliance TINYINT NOT NULL,News TINYINT NOT NULL,Astro char(10) NOT NULL,ValidateCode int NOT NULL,Active TINYINT NOT NULL)");

stmt.executeUpdate("Create table StockPrice (Company char(20) PRIMARY KEY,High decimal(6,2) NOT NULL,Low decimal(6,2) NOT NULL, Close decimal(6,2) NOT NULL)");

stmt.executeUpdate("Insert into StockPrice values('Satyam',2980.00,2820.00,2956.30)");

stmt.executeUpdate("Insert into StockPrice values('Infosys',2980.00,2820.00,2956.30)");

stmt.executeUpdate("Insert into StockPrice values('Wipro',2980.00,2820.00,2956.30)");

stmt.executeUpdate("Insert into StockPrice values('Reliance',2980.00,2820.00,2956.30)");

stmt.executeUpdate("Create table News (Source char(20) PRIMARY KEY,TodayNews char(255) NOT NULL)");

stmt.executeUpdate("Insert into News values('HindustanTimes.com','India quarantines seventy foreigners</p><p>Rahul Dravid weds Vijeta</p><p>No change in Kashmir policy, says Pakistan</p><p>Terrorists moving freely in Pakistan, says daily')");

stmt.executeUpdate("Insert into News values('TimesOfIndia.com','India reports another SARS case in Kolkata</p><p>Vajpayee names LCA as Tejas</p><p>I was asked to topple Mufti govt: Farooq</p><p>Rahul Dravid enters into wedlock')");

stmt.executeUpdate("Create table AstroPred (Sign char(10) PRIMARY KEY,Prediction char(255) NOT NULL)");

stmt.executeUpdate("Insert into AstroPred values('Aquarius','As your career prospects improve you feel better grounded and confident. This has a positive effect on your personal life. You begin to get more assertive and do things for your highest good. Keep the faith and all will fall into place on its own.')");

stmt.executeUpdate("Insert into AstroPred values('Virgo','As your career prospects improve you feel better grounded and confident. This has a positive effect on your personal life. You begin to get more assertive and do things for your highest good. Keep the faith and all will fall into place on its own.')");

out.println("tables created are:- MobileUsers,StockPrice,AstroPred,News");

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
