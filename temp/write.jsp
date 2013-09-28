<%@page import="java.io.*"%>
<%
String textFromTextArea = request.getParameter("message");
if(textFromTextArea!=null){
	String path = "/home/virtual/site2/fst/var/www/html/temp/file.txt";
	File file = new File(path);
	Writer writer = new BufferedWriter(new FileWriter(file));
	writer.write(textFromTextArea);
	writer.flush();
	writer.close();
}
%>

<%
String textToTextArea = "";
String path = "/home/virtual/site2/fst/var/www/html/temp/file.txt";
File file = new File(path);
FileReader in = new FileReader(file);
int c;
char ch;
while ((c = in.read()) != -1){
ch=(char)c;
textToTextArea += ch;
}
in.close();
%>


<HTML>
<BODY>
hi
<Form name="frmtest" method="post">
<Textarea name="message"><%=textToTextArea%></Textarea>
<INPUT TYPE="submit">
</Form>
</BODY>
</HTML>
<!--Code ends here-->
