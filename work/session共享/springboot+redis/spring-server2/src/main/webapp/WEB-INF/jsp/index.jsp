<%--
  Created by IntelliJ IDEA.
  User: 25654
  Date: 2022/9/18
  Time: 22:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
server2
<br>
<br>
SessionID:<%=session.getId()%>
<br>
SessionValue:<%=session.getAttribute("session")%>
<br>
SessionIP:<%=request.getServerName()%>
<br>
SessionPort:<%=request.getServerPort()%>
<br>
</body>
</html>
