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
<div style="width: 60%; height: 50px; background-color: white; float: left;">
<input class = "inputStyle" id = "inputFriend" style="margin-top: 20px; margin-left: 10%;" placeholder="Enter name of last name:" onkeyup="findFriends()">
</div>

<div id = "myFriends" style="width: 30%; height: 200px; background-color: white; float: left;overflow: scroll;">
    <h3>Друзі</h3>
    <c:forEach items="${friendsOfUser}" var="f">
        <a href="/user/${f.id}" style="text-decoration: none;" >
            <div style="width: 30%; height: 50px; border: 1px solid black; margin-left: 5%; margin-top: 10px;">
            <p>${f.firstName} ${f.lastName}</p>
            <p>${f.birthDate}</p>
            </div>
        </a>
    </c:forEach>
</div>
    <div style="width: 30%; height: 200px; background-color: white; float: left; overflow: scroll;">
        <h3>Підписники</h3>
    <c:forEach items="${subscribersOfUser}" var="s">
        <a href="/user/${s.id}" style="text-decoration: none;" >
            <div style="width: 30%; height: 50px; border: 1px solid black; margin-left: 5%; margin-top: 10px;">
                <p>${s.firstName} ${s.lastName}</p>
                <p>${s.birthDate}</p>
            </div>
        </a>
    </c:forEach>
</div>

<div id = "allfriends" style="width: 40%; height: 500px; background-color: white; float: left; overflow: scroll;"></div>


<script>
    function findFriends(){
        var element = document.getElementById("allfriends");
        while(element.firstChild) element.removeChild(element.firstChild);

        $.ajax({
            url: "/findFriends",
            dataType: "json",
        data:(
        {
            friend:$("#inputFriend").val()
        }),
         async: false,
         success: function(data){
             $.each(data,function(k,v) {
                 var elemA = document.createElement("a");
                 elemA.href = "/user/"+ v.id;
                 var elem = document.createElement("p");
                 elem.innerHTML = v.name+" "+ v.lastName;
                 elemA.appendChild(elem);
                 document.getElementById("allfriends").appendChild(elemA);
             });
         }
        });
    }

</script>


</body>
</html>
