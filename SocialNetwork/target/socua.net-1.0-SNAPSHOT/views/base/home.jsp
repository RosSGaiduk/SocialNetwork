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
    <link rel="stylesheet" href="/resources/css/mainStyle.css" media="screen" type="text/css" />
</head>
<body>
<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
<div style="width: 60%; height: 100%; margin-left: 20px; max-width: 60%; float: left; margin-top: 50px;">
    <sec:authorize access="isAuthenticated()">
        <div style="width: 300px; height: 300px; float: left; background-color: white; margin-top: 20px;">
        <div id = "photoOfUser" style="
        width: 226px; height: 226px; float: left;
        margin-top: 20px; margin-left: 30px;background-image: url(${user.newestImageSrc});
                background-repeat: no-repeat; background-size: cover;
                ">
        </div>
            <form:form action="upload/process.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
                       enctype="multipart/form-data" cssStyle="float: left;">
                <input type="file" name="file1"  style="float: left;"/>
                <input type="submit" value="Upload" style="float: left;">
            </form:form>
        </div>


        <%--<img src="/upload/1/img1.JPG" style="float: left">--%>
        <%--<img src="/resources/1/1.JPG" style="float: left">--%>

        <div id = "info" style="width: 50%; height: 300px; float: left; margin-left: 15px;
        margin-top: 20px; background-color: white;">
            <div style="width: 100%; height: 70%; float: left; float: left;">
            <h2 style="text-align: center">${user.lastName} ${user.firstName}</h2>
            <h3 style="text-align: center">День народження: ${user.birthDate}</h3>
            <h3 style="text-align: center">Ім'я: ${user.firstName}</h3>
            <h3 style="text-align: center">Прізвище: ${user.lastName}</h3>
            </div>
            <div style="width: 90%; height: 30%; float: left; border-top: 1px solid gainsboro; float: left; margin-left: 5%;">
            <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;"></div>

                <a href="/friends" style="text-decoration: none">
            <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;
             background-image: url(/resources/img/icons/followers.jpg); background-size: cover; background-repeat: no-repeat;
            "></div></a>
                <a href="/photosOf/${user.id}">
                <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro;
            background-image: url(/resources/img/icons/camera.png); background-size: cover; background-repeat: no-repeat;
            cursor: hand;"></div></a>
                <a href="/messagePage">
                <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro;
                background-image: url(/resources/img/icons/message.png); background-size: cover; background-repeat: no-repeat;
                cursor: hand;"></div>
                </a>
            </div>
        </div>
        <p style="text-align: right; margin-top: 10px; float: left; margin-left: 20px;">Online</p>

        <div style="width: 300px; height: 50px; float: left; background-color: white; margin-top: 20px;">
            <button style="width: 80%; height: 80%; margin-left: 10%; margin-top:5px;background-color: gainsboro;
            ">Edit</button>
        </div>

        <div style="width: 50%; height: 300px; float: left; margin-left: 15px;
        margin-top: 20px; background-color: white;"></div>


        <div style="width: 300px; height: 110px; float: left; background-color: white; margin-top: 20px;
        margin-top: -220px;
        ">
            <a href="/friends" style="margin-left: 10px;">Друзі</a>
            <p style="clear: left"></p>
            <c:forEach items="${friendsOfUser}" var="u">
            <a href="/user/${u.id}" style="text-decoration: none;">
                <div style="width: 25%; height: 68%; margin-left: 3%; margin-top: 10px;
                        background-image: url(${u.newestImageSrc}); background-size: cover; float:left;">
                    <%--<span>${u.firstName}</span>--%>
                </div>
                </a>
            </c:forEach>

            <p style="clear: left"></p>

            <c:forEach items="${friendsOfUser}" var="u">
                <div style="width: 25%; height: 68%; margin-left: 3%;float:left;">
                        <span>${u.firstName}</span>
                </div>
            </c:forEach>

        </div>


        <!--<button onclick="" style="float: left; width: 100px; height: 30px; margin-top: 250px; margin-left: -100px" >Add photo</button>-->
    </sec:authorize>


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
