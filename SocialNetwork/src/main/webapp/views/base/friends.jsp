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
    <!--Ajax-->
    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
    <!--/Ajax-->
</head>
<body>
<div style="width: 50%; height: 50px; background-color: white; float: left; margin-top: 70px;">
<input class = "inputStyle" id = "inputFriend" style="margin-top: 20px; margin-left: 10%; height: 30px;float: left; font-size: 10px;" placeholder="Enter name of last name:" onkeyup="findFriends()">
</div>
<p id = "userThis">${userThis}</p>
<div id = "allfriends" style="width: 50%; height: auto; background-color: white; float: left;">
    <h3 style="float: left; margin-left: 10px;">Друзі (знайдено ${friendsOfUser.size()})</h3>
    <p style="clear: left"/>
    <c:forEach items="${friendsOfUser}" var="f">
    <a href="/user/${f.id}" style="text-decoration: none;" >
        <img src="${f.newestImageSrc}" style="width:120px;height:120px;background-size:contain;float:left;margin-left:10%;margin-top:20px; border-radius: 50%;">
        <div style="width: 30%; height: 50px; margin-top: 12px;float:left;">
            <h3 style="margin-left: 10px;">${f.firstName} ${f.lastName}</h3>
            <h3 style="margin-left: 10px;">${f.birthDate}</h3>
        </div>
        <p style="clear: left"></p>
    </a>
    </c:forEach>
    <div style="width: 100%; height: 1px; background-color: #999999; margin-top: 10px;"></div>
    <h3 style="float: left; margin-left: 10px;">Інші люди</h3>
    <p style="clear: left;"/>
    <c:forEach items="${anotherPeople}" var="a">
        <a href="/user/${a.id}" style="text-decoration: none;" >
            <img src="${a.newestImageSrc}" style="width:120px;height:120px;background-size:contain;float:left;margin-left:10%;margin-top:20px; border-radius: 50%;">
            <div style="width: 30%; height: 50px; margin-top: 12px;float:left;">
                <h3 style="margin-left: 10px;">${a.firstName} ${a.lastName}</h3>
                <h3 style="margin-left: 10px;">${a.birthDate}</h3>
            </div>
            <p style="clear: left"></p>
        </a>
    </c:forEach>
</div>

<div style="width: 20%; height: 300px; background-color: white; float: left; margin-left: 20px;"></div>


<script>
    function findFriends(){
        var element = document.getElementById("allfriends");
        while(element.firstChild) element.removeChild(element.firstChild);

        //alert(document.getElementById("userThis").innerHTML);

        $.ajax({
            url: "/findFriends",
            dataType: "json",
        data:(
        {
            friend:$("#inputFriend").val(),
            user: document.getElementById("userThis").innerHTML
        }),
         async: false,
         success: function(data){
             $.each(data,function(k,v) {
                 var elemA = document.createElement("a");
                 elemA.href = "/user/"+ v.id;

                 var image = document.createElement("img");

                 image.style ="width:120px;height:120px;background-size:contain;float:left;margin-left:10%;margin-top:20px;border-radius:50%;";
                 image.src = v.image;
                 document.getElementById("allfriends").appendChild(image);

                 var elem = document.createElement("h3");
                 elem.style = "float:left; margin-left:10px; margin-top:20px;";
                 elem.innerHTML = v.name+" "+ v.lastName;
                 elemA.appendChild(elem);

                 document.getElementById("allfriends").appendChild(elemA);
                 var elem1 = document.createElement("p");
                 elem1.style = "clear:left";
                 document.getElementById("allfriends").appendChild(elem1);

             });
         }
        });
    }
</script>
</body>
</html>
