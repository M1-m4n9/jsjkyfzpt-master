<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	<title>
	tomcat2
	</title>
	</head>
    <body>
        SessionID:<%=session.getId()%>
		<br>
        SessionValue:<%=session.getAttribute("session")%>
        <br>
		SessionIP:<%=request.getServerName()%>
        <br>
		SessionPort:<%=request.getServerPort()%>
		<br>
        <%
            out.println("this is tomcat 2");
        %>
    </body>
</html>