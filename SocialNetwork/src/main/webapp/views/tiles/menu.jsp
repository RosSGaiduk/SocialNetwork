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
    <%--Всі лінки підключені в template.jsp--%>
</head>
<body>
    <div class="menuDivClass">
        <sec:authorize access="isAuthenticated()">

        <div class="menuOption" style="margin-top: 120px;">
            <a href="/" style="text-decoration: none;">
                <h4 style="text-align: left; ">Моя сторінка</h4>
            </a>
        </div>
            <div class="menuOption">
                <a href="/messages" style="text-decoration: none"><h4 style="text-align: left;">Повідомлення</h4></a>
            </div>
        <div class="menuOption">
            <a href="/friendsOf/<sec:authentication property="name"/>" style="text-decoration: none"><h4 style="text-align: left;">Друзі</h4></a>
        </div>
            <div class="menuOption">
                <a href="/photosOf/<sec:authentication property="name"/>/MY_PAGE_PHOTOS" style="text-decoration: none"><h4 style="text-align: left">Фотографії</h4></a>
            </div>

            <div class="menuOption">
                <a href="/music" style="text-decoration: none"><h4 style="text-align: left">Музика</h4></a>
            </div>

            <div class="menuOption">
                <a href="/communities" style="text-decoration: none"><h4 style="text-align: left">Спільноти</h4></a>
            </div>

            <div class="menuOption">
                <a href="/news" style="text-decoration: none"><h4 style="text-align: left">Новини</h4></a>
            </div>

            <div style="width: 100%; height: 10px; float: left; margin-top: 40px;">
            <form:form method="post" action="/logout">
                <p style="text-align: left"><button type="submit" style="
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
