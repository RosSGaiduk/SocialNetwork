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


<div style="width: 60%; height: 100%; background-color: white; margin-left: 20px; max-width: 60%; float: left">
    <div id = "photoOfUser" style="
        width: 226px; height: 226px; float: left; border:1px solid black;
        margin-top: 20px; margin-left: 10px;">
    </div>
    <button
            style="float: left; margin-top: 270px; margin-left: -220px; height: 30px; width: 100px;">Messages</button>


    <button onclick="addUserToFriendZone()" id = "addFriend"
            style="float: left; margin-top: 270px; height: 30px; margin-left: -110px; width: 100px; visibility: ${friendOrNo}">Add to friends</button>


    <div id = "info" style="width: 50%; height: 300px; float: left; margin-left: 10px; border: 1px solid black; margin-top: 20px;">
        <h2 style="text-align: center; visibility: hidden" id = "userId" >${user.id}</h2>
        <h2 style="text-align: center">${user.firstName} ${user.lastName}</h2>
        <h3 style="text-align: center">День народження: ${user.birthDate}</h3>
        <h3 style="text-align: center">Ім'я: ${user.lastName}</h3>
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
                document.getElementById("addFriend").style = "visibility: hidden";
            }
        })
    }

</script>
</body>
</html>
