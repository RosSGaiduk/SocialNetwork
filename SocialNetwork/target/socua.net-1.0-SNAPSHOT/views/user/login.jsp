<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 01.03.2017
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--Всі лінки підключені в template.jsp--%>
</head>
<body>
<div style="width: 60%; height: 100%; margin-left: 20px; max-width: 60%; float: left; margin-top: 50px;">
<div style="margin-top: 20%; float: left; width: 60%; height: auto">
    <form:form method="post" action="/loginprocessing">
        <br>
        <%--Тут обов'язково має бути username, не email, не name, навіть якщо такого поля немає у юзера--%>
        <input class="inputStyle" name="username" type="text" placeholder="Login"><br><br>
        <input class="inputStyle" id = "password" name="password" type="password" placeholder="Password">
        <br><br>
        <input type="submit" value="Sign in" style="float: left; margin-left: 40%;">
        <a href="/addUser"><input type="button" value="Registration" style="float: left; margin-left: 10px;"></a>
        <p id = "strengthValue"></p>
    </form:form>
</div>
    </div>
</body>
</html>
