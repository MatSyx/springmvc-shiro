<%--
  Created by IntelliJ IDEA.
  User: shiyongxiang
  Date: 16/10/14
  Time: 下午9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    欢迎<b>${currentUser.username}</b>
    <a href="/needRoleAdmin"><button>访问需要admin角色的页面</button></a>
    <a href="/needPermissionUserVAC"><button>访问需要User:view和create权限的页面</button></a>
    <a href="/needPermissionUserVOC"><button>访问需要User:view或create权限的页面</button></a><br/>
    <a href="/logout"><button>退出登录</button></a><br/>

    <shiro:hasRole name="admin">
        admin role
    </shiro:hasRole>
    <shiro:hasRole name="user">
        user role
    </shiro:hasRole>
</body>
</html>
