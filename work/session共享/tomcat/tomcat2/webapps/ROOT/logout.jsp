<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
	<title>
	tomcat2
	</title>
	</head>
    <body>
        <%
			session.removeAttribute("session");
		%>
        <%
            out.println("this is remove SessionValue success");
        %>
    </body>
</html>