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
        <div style="width: 300px; height: 350px; float: left; background-color: white; margin-top: 20px;">
            <div id = "photoOfUser" style="
                    width: 226px; height: 226px; float: left;
                    margin-top: 20px; margin-left: 30px;background-image: url(${user.newestImageSrc});
                    background-repeat: no-repeat; background-size: cover;
                    ">
            </div>


            <a href="/messagesWithUser/${user.id}" id = "messageA" style="text-decoration: none; color: black;">
                    <button style="float: left; margin-top: 10px; margin-left: 30px; height: 30px; width: 100px;cursor: hand;
                    color: white;background-color: darkslateblue;">
                Messages</button></a>
            <button onclick="addUserToFriendZone()" id = "addFriend"
                    style="float: left; height: 30px; margin-left: 10px; width: 100px; visibility: ${friendOrNo}">Add to friends</button>
            <p style="clear: left"></p>
            <div style="float: left; height: 30px; margin-top:10px; margin-left: 30px; width: 100px;">
                <form:form id = "formForLoadingPictures" action="upload/process.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
                           enctype="multipart/form-data" cssStyle="float: left;">
                    <input type="file" name="file1" style="float: left;"/>
                    <input type="submit" value="Upload" style="float: left;">
                </form:form>
            </div>
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
                <h3 id = "userId" style="text-align: center;visibility: hidden;">${user.id}</h3>
                <h3 id = "userAuthId" style="text-align: center;visibility: hidden;">${userAuth.id}</h3>
            </div>

            <div style="width: 90%; height: 30%; float: left; border-top: 1px solid gainsboro; float: left; margin-left: 5%;">
                <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;"></div>

                <a href="/friendsOf/${user.id}" style="text-decoration: none">
                    <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;
             background-image: url(/resources/img/icons/followers.jpg); background-size: cover; background-repeat: no-repeat;
            "></div></a>
                <a href="/photosOf/${user.id}">
                    <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro;
            background-image: url(/resources/img/icons/camera.png); background-size: cover; background-repeat: no-repeat;
            cursor: hand;"></div></a>
                <a href="/messagesWithUser/${user.id}">
                    <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro;
                background-image: url(/resources/img/icons/message.png); background-size: cover; background-repeat: no-repeat;
                cursor: hand;"></div>
                </a>
            </div>
        </div>


        <p style="text-align: right; margin-top: 10px; float: left; margin-left: 20px;">Online</p>


        <%--<div style="width: 50%; height: 100px; float: left; background-color: white; margin-top: 20px; margin-left: 15px;">
            </div>--%>

        <p style="clear: left"></p>


        <div style="width: 300px; height: 50px; float: left; background-color: white; margin-top: 20px;">
            <button style="width: 80%; height: 80%; margin-left: 10%; margin-top:5px;background-color: gainsboro;
            ">Edit</button>
        </div>
        <p style="clear: left"></p>
        <div style="width: 300px; height: 110px; float: left; background-color: white; margin-top: 20px;
        ">
            <a href="/friendsOf/${user.id}" style="margin-left: 10px;">Друзі</a>
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

        <div style="width: 50%; height: auto; float: left; margin-left: 15px;
        margin-top: -100px; background-color: white;">

            <div style="width: 70px; height: 50px; float: left; background-image: url(${userAuth.newestImageSrc});
                    background-size: cover; background-repeat: no-repeat; margin-left: 10px; margin-top: 3px;
                    ">
            </div>
             <textarea id = "newRecord" placeholder="Do you have something new?"
                       style="width: 200px; height: 50px; float: left; margin-left: 10px; margin-top: 3px;">
             </textarea>

            <button onclick="updateRecords()" style="height: 25px; margin-top: 30px; margin-left: 10px;
            background-color: #6ea0ff; color: white;
            ">Send</button>

           <%-- <div style="margin-left: 0%; width: 40%; height: 20px; ">
                <form:form id = "formForLoadingPicturesToWall" action="upload/process1.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
                           enctype="multipart/form-data" cssStyle="float: left;">
                <input id = "imageToWall" type="file" name="file2" style="float: left;"/>
                <input type="submit" value="Upload2" style="float: left; margin-left: 85%; margin-top: -20px;">
                </form:form>
            </div>--%>
            <%--<form action="./upload?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">--%>
            <form:form action="/process1.htm?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" cssStyle="float: left;">
            <input id = "imageToWall" type="file" name="file2" style="float: left;"/>
            <input onclick="updateRecords()" type="submit" value="Upload">
            </form:form>
        </div>

        <div id = "records" style="width: 50%; height: auto; float: left; margin-left: 15px;
         background-color: white; margin-top: 0px;">
            <c:forEach var="rec" items="${records}">
                <div id = "${rec.id} div" style="width:80%; height:auto; background-color:white; float:left; margin-top:20px; border-bottom:1px solid grey;">
                    <p style="float:left; margin-left:10px;">${rec.dateOfRecord}</p>
                    <p style="clear: left"/>
                    <div style="width:70px; height:50px;
                            background-image: url(${rec.userFrom.newestImageSrc}); background-size:cover;
                            float:left; margin-left:10px; margin-top:10px;"></div>
                    <div style = "width:50%; height:auto; background-color:white; float:left;margin-top:20px;">
                        <p style="float:left; margin-left:10px; margin-top:10px;">${rec.text}</p>
                        <div style="width: 50%; float: left; height: 50px;"></div>
                        <c:if test="${rec.hasImage == true}">
                        <img src="${rec.urlImage}" style=" margin-left: 0px; margin-left: -20px; width: 100%; height: auto;
                        background-size: cover;
                        "></c:if>
                        <p id = "${rec.id}" style="visibility: hidden">${rec.id}</p>
                    </div>
                    <button onclick="deleteRecord(document.getElementById('${rec.id}').innerHTML)"
                            style="visibility: visible"
                    >Delete</button>
                </div>
            </c:forEach>
            <div style="width:80%; height:auto; background-color:white; float:left; margin-top:20px;"></div>
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

<script>
    function checkIfAuthUserInHomePage(){
        if (document.getElementById("userAuthId").innerHTML == document.getElementById("userId").innerHTML)
                return "";
    }
    checkIfAuthUserInHomePage();
</script>

<script>
    function checkWhatToDoMessagesOrDownloadPicture(){
        if (document.getElementById("userId").innerHTML == document.getElementById("userAuthId").innerHTML) {
            document.getElementById('formForLoadingPictures').style = "visibility:visible;"
            document.getElementById("messageA").style = "visibility: visible;";
        } else {
            document.getElementById('formForLoadingPictures').style = "visibility:hidden;"
            document.getElementById("messageA").style = "visibility: visible;";
        }
    }
    checkWhatToDoMessagesOrDownloadPicture();
</script>


<script>
    function updateRecords(){
        /*alert($('#newRecord').val());*/
        //alert($('#imageToWall').val());
        $.ajax({
            url: "/updateRecords",
            data: ({
                newRecord:$('#newRecord').val(),
                image:$('#imageToWall').val(),
                userFrom:document.getElementById("userAuthId").innerHTML,
                userTo: document.getElementById("userId").innerHTML
            }),
            dataType: "json",
            async:false,
            success: function(data){
                //alert("Hello");
                $.each(data,function(k,v){
                  var elem = document.createElement("div");
                    elem.style = "width:80%; height:auto; background-color:white; float:left; margin-top:20px; border-bottom:1px solid grey;";
                    var pDate = document.createElement("p");
                    pDate.innerHTML = v.date;
                    pDate.style = "float:left; margin-left:10px;";
                    elem.appendChild(pDate);

                    var pClear = document.createElement("p");
                    pClear.style = "clear:left";
                    elem.appendChild(pClear);

                    var imag = document.createElement("div");
                    imag.style = "width:70px; height:50px;" +
                            "background-image: url("+ v.userFromImage+");" +
                            "background-size:cover;" +
                            "float:left; margin-left:10px; margin-top:10px;";
                    elem.appendChild(imag);



                    var elemLeft = document.createElement("div");
                    elemLeft.style = "width:50%; height:auto; background-color:white; float:left;" +
                            "margin-top:20px;";
                    var pThis = document.createElement("p");
                    pThis.innerHTML = v.text;
                    pThis.style = "float:left; margin-left:10px; margin-top:10px;";
                    elemLeft.appendChild(pThis);
                    elem.appendChild(elemLeft);
                    var first=document.getElementById("records").childNodes[0];
                    document.getElementById("records").insertBefore(elem,first);
                  //document.getElementById("records").appendChild(elem);
                });
            }
        });
    }
</script>

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

<script>
    function deleteRecord(recId){
        $.ajax({
            url: "/deleteRecord",
            data:({
                idRecord: recId
            }),
            async: false,
            success: function(data){
                var mainDiv = document.getElementById("records");
                var deletedDiv = document.getElementById(recId+' div');
                mainDiv.removeChild(deletedDiv);
            }
        })
    }
</script>

</body>
</html>
