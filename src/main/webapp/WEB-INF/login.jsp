<%--
  Created by IntelliJ IDEA.
  User: shiyongxiang
  Date: 16/10/14
  Time: 下午11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1>login page</h1>
<form id="" action="/dologin" method="post">
    <label>User Name</label>
    <input tyep="text" name="username" maxLength="40"/>
    <label>Password</label>
    <input type="password" name="password"/>
    <input type="submit" value="login"/>
    <p>${message}</p>
</form>
</body>
</html>
