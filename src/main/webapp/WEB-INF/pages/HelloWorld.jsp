<%--
  Created by IntelliJ IDEA.
  User: LiJinZhong
  Date: 2017/10/3
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>登录接口：</h2>
<form method="post" action="LoginController/codeForSessionKey">
    <label>code: </label>
    <input name="code"  type="text">
    <button type="submit">提交</button>
</form>
<hr/>
<h2>我要表白：</h2>
<form method="post" action="MessageController/writeMessage">
    <label>请输入:</label>
    <textarea name="message" id="" cols="30" rows="5"></textarea>
    匿名<input name="anonymous" type="checkbox" value="1" checked="true"/>
    <button type="submit">提交</button>
</form>
<hr/>
<>

</body>
</html>
