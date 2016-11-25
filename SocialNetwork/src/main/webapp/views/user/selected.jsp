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

<div id = "mainDiv" style="width: 60%; height: 100%; margin-left: 20px; max-width: 60%; float: left;overflow: scroll;">
    <div style="width: 300px; height: 300px; float: left; background-color: white; margin-top: 20px;">
    <div id = "photoOfUser" style="
            width: 226px; height: 226px; float: left;
            margin-top: 20px; margin-left: 30px;background-image: url(${user.newestImageSrc});
            background-repeat: no-repeat; background-size: cover;">
    </div>
        <button style="float: left; margin-top: 10px; margin-left: 30px; height: 30px; width: 100px;">Messages</button>
        <button onclick="addUserToFriendZone()" id = "addFriend"
                style="float: left; margin-top: 10px; height: 30px; margin-left: 10px; width: 100px; visibility: ${friendOrNo}">Add to friends</button>
    </div>




    <div id = "info" style="width: 50%; height: 300px; float: left; margin-left: 15px;
        margin-top: 20px; background-color: white;">
        <div style="width: 100%; height: 70%; float: left; float: left;">
            <h2 style="text-align: center">${user.lastName} ${user.firstName}</h2>
            <h3 style="text-align: center">День народження: ${user.birthDate}</h3>
            <h3 style="text-align: center">Ім'я: ${user.firstName}</h3>
            <h3 style="text-align: center">Прізвище: ${user.lastName}</h3>
            <h3 id = "userId" style="visibility: hidden">${user.id}</h3>
        </div>
        <div style="width: 90%; height: 30%; float: left; border-top: 1px solid gainsboro; float: left; margin-left: 5%;">
            <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;"></div>
            <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;
             background-image: url(/resources/img/icons/followers.jpg); background-size: cover; background-repeat: no-repeat;
            "></div>
            <a href="/photosOf/${user.id}">
            <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro;
            background-image: url(/resources/img/icons/camera.png); background-size: cover; background-repeat: no-repeat;
            cursor: hand;"></div></a>
            <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro;
                background-image: url(/resources/img/icons/message.png); background-size: cover; background-repeat: no-repeat;
                cursor: hand;"></div>
        </div>
    </div>
    </div>

<script>

    function addUserToFriendZone(){
        $.ajax({
            url: "/addUserToFriendsZone",
            data: ({
                userId: document.getElementById("userId").innerHTML
            }),
            async: false,
            success: function(data){
                document.getElementById("addFriend").style = "visibility: hidden; disabled: true;";
               /* var button = document.createElement("button");
                button.style = "float: left; margin-top: 270px; height: 30px; margin-left: -110px; width: 100px;";
                button.setAttribute("id","removeFriend");
                button.innerHTML = "Remove friend";
                document.getElementById("mainDiv").appendChild(button);*/
            }
        })
    }

</script>
</body>
</html>
