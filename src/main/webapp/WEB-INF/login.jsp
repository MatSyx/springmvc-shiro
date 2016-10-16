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
<form id="" action="/login" method="post">
    <div>
        <label>User Name</label>
        <input tyep="text" name="username" maxLength="40"/>
    </div>
    <div><label>Password</label>
        <input type="password" name="password"/>
    </div>
    <div>
        <label>验证码</label><input type="text" name="captcha"/>
        <img id="verifyCodeImage" onclick="reloadVerifyCode()" name="captcha" src="/captcha"/>
    </div>
    <input type="submit" value="login"/>
    <p>${message}</p>
</form>
</body>
<script>
    function reloadVerifyCode(){
        document.getElementById("verifyCodeImage").setAttribute("src","/captcha");
    }
</script>
</html>
