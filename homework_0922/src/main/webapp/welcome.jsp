<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎页面</title>
</head>
<body>
    <h2><%=request.getAttribute("msg")%></h2>
    <p>登录成功！</p>
    <a href="login.jsp">返回登录</a>
</body>
</html>
