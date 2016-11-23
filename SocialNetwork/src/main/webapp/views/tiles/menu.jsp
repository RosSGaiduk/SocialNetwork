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
    <link rel="stylesheet" href="/resources/css/forChat.css" media="screen" type="text/css" />
</head>
<body>
    <div style="width: 10%; height: 100%; float: left; overflow: auto; margin-left: 10%;">
        <sec:authorize access="isAuthenticated()">
        <div style="width: 100%; height: 10px; float: left; margin-top: 120px;">
            <a href="/" style="text-decoration: none"><h4 style="text-align: center">Моя сторінка</h4></a>
        </div>
        <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
            <a href="/friends" style="text-decoration: none"><h4 style="text-align: center">Друзі</h4></a>
        </div>
            <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
                <a href="/followers" style="text-decoration: none"><h4 style="text-align: center">Підписники</h4></a>
            </div>
        <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
            <a href="/messagePage" style="text-decoration: none"><h4 style="text-align: center">Повідомлення</h4></a>
        </div>
            <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
                <a href="/photos" style="text-decoration: none"><h4 style="text-align: center">Фотографії</h4></a>
            </div>

        </sec:authorize>
    </div>
</body>
</html>
