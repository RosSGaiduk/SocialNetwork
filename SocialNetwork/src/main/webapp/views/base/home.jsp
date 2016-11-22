<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 17.11.2016
  Time: 23:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Open+Sans'>
    <link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.css'>
    <link rel="stylesheet" href="/resources/css/formsStyle.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="/resources/css/style.css" media="screen" type="text/css" />
</head>
<body>
<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
<div style="width: 60%; height: 100%; background-color: white; margin-left: 20px;">
    <%--Якщо ніхто не залогінований, тоді форма для заповнення чи реєстрації--%>
    <sec:authorize access="isAnonymous()">
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
    </sec:authorize>
</div>

</body>
</html>
