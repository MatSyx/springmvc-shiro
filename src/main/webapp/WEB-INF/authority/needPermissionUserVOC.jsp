<%--
  Created by IntelliJ IDEA.
  User: shiyongxiang
  Date: 16/10/15
  Time: 下午8:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>needPermissionUserVOC</title>
</head>
<body>
<shiro:hasPermission name="user:create">
    <a href="#">创建用户</a>
</shiro:hasPermission>
<shiro:hasPermission name="user:view">
    <a href="#">查看用户</a>
</shiro:hasPermission>
</body>
</html>
