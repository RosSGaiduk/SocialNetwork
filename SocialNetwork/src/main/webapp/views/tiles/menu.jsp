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
    <div class="menuDivClass">
        <sec:authorize access="isAuthenticated()">

        <div style="width: 100%; height: 10px; float: left; margin-top: 120px;">
            <a href="/" style="text-decoration: none"><h4 style="text-align: center">Моя сторінка</h4></a>
        </div>
            <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
                <a href="/messagePage" style="text-decoration: none"><h4 style="text-align: center">Повідомлення</h4></a>
            </div>
        <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
            <a href="/friendsOf/<sec:authentication property="name"/>" style="text-decoration: none"><h4 style="text-align: center">Друзі</h4></a>
        </div>
            <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
                <a href="/photosOf/<sec:authentication property="name"/>/*" style="text-decoration: none"><h4 style="text-align: center">Фотографії</h4></a>
            </div>
            <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
                <a href="/music" style="text-decoration: none"><h4 style="text-align: center">Музика</h4></a>
            </div>

            <div style="width: 100%; height: 10px; float: left; margin-top: 20px;">
                <a href="/news" style="text-decoration: none"><h4 style="text-align: center">Новини</h4></a>
            </div>

            <div style="width: 100%; height: 10px; float: left; margin-top: 40px;">
            <form:form method="post" action="/logout">
                <p style="text-align: center"><button type="submit" style="
                background-color: darkslateblue;
                color:white;
                cursor: hand;
                " onclick="doExit()">Вийти</button></p>
            </form:form>
                </div>
        </sec:authorize>
    </div>

<script>
    function doExit(){
        $.ajax({
            url: "/exitUser",
            async: false,
            success: function(data){
                console.log(data);
            }
        });
    }
</script>


</body>
</html>
