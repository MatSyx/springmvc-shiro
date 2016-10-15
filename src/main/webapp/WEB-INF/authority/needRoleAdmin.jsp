<%--
  Created by IntelliJ IDEA.
  User: shiyongxiang
  Date: 16/10/15
  Time: 下午8:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>needRoleAdmin</title>
</head>
<body>
<shiro:hasRole name="admin">
    只有管理员可以访问
</shiro:hasRole>
</body>
</html>
