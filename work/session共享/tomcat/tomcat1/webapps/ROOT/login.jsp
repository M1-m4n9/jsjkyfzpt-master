<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	<title>
	tomcat1
	</title>
	</head>
    <body>
        <%
			session.setAttribute("session","libo");
		%>
        <%
            out.println("this is set SessionValue success");
        %>
    </body>
</html>