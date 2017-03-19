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
    <%--Всі лінки підключені в template.jsp--%>
    <!--Ajax-->
    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
    <!--/Ajax-->

    <script src="/resources/scripts/mainFunctions.js"></script>
</head>
<body>
<div style="width: 60%; height: 100%; margin-left: 20px; max-width: 60%; float: left; margin-top: 50px; cursor: hand;">
    <sec:authorize access="isAuthenticated()">
        <div class="userPhoto">
            <div onclick="openPhotoUser(${user.newestImageId})" id = "photoOfUser" style="
                    width: 75%; height: 64%; float: left;
                    margin-top: 20px; margin-left: 30px;background-image: url(${user.newestImageSrc});
                    background-repeat: no-repeat; background-size: cover;
                    " class="magnify">
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
                    <input type="file" name="file1"  style="float: left;"/>
                    <button type="submit" style="float: left;margin-top: 3px;" class="buttonFileStyle">Upload</button>
                </form:form>
            </div>
        </div>

        <%--<img src="/upload/1/img1.JPG" style="float: left">--%>
        <%--<img src="/resources/1/1.JPG" style="float: left">--%>

        <div id = "info" class = "userInfo">
            <div style="width: 100%; height: 70%; float: left; float: left;">
                <c:if test="${user.isOnline}">
                <h3 style="text-align: center; color: blue;">Online</h3>
                </c:if>
                <c:if test="${!user.isOnline}">
                    <h3 style="text-align: center; color: blue;">Was online: ${user.lastOnline}</h3>
                </c:if>
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
                    <div class = "logos" style="background-image: url(/resources/img/icons/followers.jpg);"></div></a>
                <a href="/photosOf/${user.id}/*">
                    <div class = "logos" style="background-image: url(/resources/img/icons/camera.png);"></div></a>
                <a href="/messagesWithUser/${user.id}">
                    <div class = "logos"  style="background-image: url(/resources/img/icons/message.png);"></div></a>
            </div>
        </div>

        <%--<p style="text-align: right; margin-top: 10px; float: left; margin-left: 20px;">Online</p>--%>

        <%--<div style="width: 50%; height: 100px; float: left; background-color: white; margin-top: 20px; margin-left: 15px;">
            </div>--%>

        <p style="clear: left"></p>

        <div class="userEdit">
            <button style="width: 80%; height: 80%; margin-left: 10%; margin-top:5px;background-color: gainsboro;
            ">Edit</button>
        </div>
        <p style="clear: left"></p>

        <div class = "userFriends">
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

        <p style="clear: left"/>

        <div class = "userMusic">
            <a href="/musicOf/${user.id}" style="margin-left: 10px;">Музика</a>
            <p style="clear: left"/>
            <c:forEach items="${musicOfAuth}" var="m">
            <p style="margin-top: 10px;">${m.nameOfSong}</p>
            <audio controls style="margin-top: 10px; width: 100%;" >
                <source src="${m.urlOfSong}" type="audio/mpeg">
                Your browser does not support the audio element.
            </audio>
            </c:forEach>
        </div>

        <div class="userRecords">
            <div style="width: 70px; height: 50px; float: left; background-image: url(${userAuth.newestImageSrc});
                    background-size: cover; background-repeat: no-repeat; margin-left: 10px; margin-top: 3px;
                    ">
            </div>

           <textarea id = "newRecord" style="height: 50px; width:50%; float: left" placeholder="Введіть повідомлення: "></textarea>

            <button onclick="updateRecords()" class = "buttonSendRecordStyle">Send</button>

           <%-- <div style="margin-left: 0%; width: 40%; height: 20px; ">
                <form:form id = "formForLoadingPicturesToWall" action="upload/process1.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
                           enctype="multipart/form-data" cssStyle="float: left;">
                <input id = "imageToWall" type="file" name="file2" style="float: left;"/>
                <input type="submit" value="Upload2" style="float: left; margin-left: 85%; margin-top: -20px;">
                </form:form>
            </div>--%>
            <%--<form action="./upload?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">--%>
            <form:form id =  "newRecordForm" action="/newRecordOf/${user.id}/?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" cssStyle="float: left;">
            <input id = "imageToWall" type="file" name="file2" style="float: left;"/>
            <button onclick="setActionToForm()" type="submit" class="buttonFileStyle">Upload</button>
            </form:form>
        </div>

        <div id = "records" class="userRecordsFromDb">
            <c:forEach var="rec" items="${records}">
                <div id = "${rec.id}_div" style="width:80%; height:auto; background-color:white; float:left; margin-top:20px; border-bottom:1px solid grey;" onclick="openRecord('${rec.type}','${rec.text}','${rec.url}','${rec.nameRecord}')">
                    <p style="float:left; margin-left:10px;">${rec.dateOfRecord}</p>
                    <p style="clear: left;"/>
                    <div style="width:70px; height:50px;
                           background-image: url(${rec.urlUserImagePattern});background-size:cover;
                            float:left; margin-left:10px; margin-top:10px;"></div>
                    <div style = "width:50%; height:auto; background-color:white; float:left;margin-top:20px;">
                        <p style="float:left; margin-left:10px; margin-top:10px;">${rec.text}</p>
                        <div style="width: 50%; float: left; height: 50px;"></div>
                        <c:if test="${rec.type == 'IMAGE'}">
                        <img src="${rec.url}" style=" margin-left: 0px; margin-left: -20px; width: 100%; height: auto;
                        background-size: cover;
                        "></c:if>
                        <c:if test="${rec.type == 'AUDIO'}">
                            <p style='float:left;'>${rec.nameRecord}</p>
                            <audio controls  style='width: 160%; height: auto; float: left; margin-top: 50px;'>
                                <source src="${rec.url}" type="audio/mpeg" style="cursor: hand">
                            </audio>
                        </c:if>
                        <p id = "${rec.id}" style="visibility: hidden">${rec.id}</p>
                    </div>
                    <c:if test="${user.id == userAuth.id}">
                    <button onclick="deleteRecord(document.getElementById('${rec.id}').innerHTML)">Delete</button>
                    </c:if>
                </div>
            </c:forEach>
            <div style="width:80%; height:auto; background-color:white; float:left; margin-top:20px;"></div>
        </div>
        <!--<button onclick="" style="float: left; width: 100px; height: 30px; margin-top: 250px; margin-left: -100px" >Add photo</button>-->
    </sec:authorize>
</div>

<div style="text-align: center; overflow: scroll; cursor: default" id="popupWin" class="modalwin">
    <div style="
            width: 75%; height: 64%; float: left;
            margin-top: 20px; margin-left: 30px;background-image: url(${user.newestImageSrc});
            background-repeat: no-repeat; background-size: cover; cursor: hand;
            " class="magnify" onclick="nextImage()">
    </div>
    <textarea id = "textAr" style="height: 50px; width:50%; float: left; margin-top: 20px; margin-left: 30px;" placeholder="Введіть повідомлення: "></textarea>
    <button onclick="leaveComment(${user.newestImageId})" class="sendButtonStyle" style="float: left; margin-left: 10px; margin-top: 40px;">Send</button>
    <c:if test="${user.newestImageId != 0}">
    <img id = "currentImage" src="/resources/img/icons/like.png" id = "likeImg" style="float:left; width:16px;height:14px; margin-top: 20px; margin-left: 20px; cursor: hand;" onclick="leaveLike('image','${user.newestImageId}')">
    <span id = "countLikesUnderPhoto" style="float: left; margin-top: 20px; margin-left: 7px;">1</span>
    </c:if>
    <div id = "comments" style="width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;">
    </div>
</div>

<%--<script>
    function openRecordAndBanScrolling(type,text,url,name){
        openRecord(type,text,url,name);
        //banScroll(true);
    }
</script>--%>

<!--Зробив щоб попрактикуватись з методом append(), цей метод не викликається ні разу-->
<script charset="UTF-8">
    function loadRecords(){
        $.ajax({
            url: "/loadAllRecords",
            data: ({
                userId: document.getElementById("userId").innerHTML
            }),
            dataType: "json",
            async:false,
            contentType: "text/html; charset/UTF-8; charset=windows-1251;",
            success: function(data){
                $.each(data,function(k,v){
                    var codeGenerator = "<div id = '" + v.id + "_div' style='width:80%; height:auto; background-color:white; float:left; margin-top:20px; border-bottom:1px solid grey;'>" +
                            "<p style='float:left; margin-left:10px;'>" + v.date + "</p>" +
                            "<p style='clear: left;'/>" +
                            "<div style='width:70px; height:50px;background-image: url(" + v.urlUserImage + ");background-size:cover;float:left; margin-left:10px; margin-top:10px;'></div>" +
                            "<div style = 'width:50%; height:auto; background-color:white; float:left;margin-top:20px;'>" +
                            "<p style='float:left; margin-left:10px; margin-top:10px;'>" + v.text + "</p>" +
                            "<div style='width: 50%; float: left; height: 50px;'></div>";

                    if (v.type == 'IMAGE'){
                        $("#records").append(codeGenerator+"<img src='"+v.url+"' style='margin-left: 0px; margin-left: -20px; width: 100%; height: auto;background-size: cover;'>"+
                                "<p id = '"+v.id+"' style='visibility: hidden'>"+v.id+"</p></div></div>");
                        }
                    else if (v.type == 'AUDIO'){
                        $("#records").append(codeGenerator+"<p style='float:left;'>"+ v.nameRecord+"</p><audio controls  style='width: 160%; height: auto; float: left; margin-top: 50px;'><source src='"+v.url+"' type='audio/mpeg' style='cursor: hand'></audio>"+
                        "<p id = '"+v.id+"' style='visibility: hidden'>"+v.id+"</p></div></div>");
                    }

                    else if (v.type == 'TEXT'){
                        $("#records").append(codeGenerator+
                                "<p id = '"+v.id+"' style='visibility: hidden'>"+v.id+"</p></div></div>");
                    }

                    if ($("#userId").html() == $("#userAuthId").html()){
                        $("#records").append("<button onclick='deleteRecord("+ v.id+")' id = '"+ v.id +"_button' style='float:left;'>Delete</button>");
                    }

                })
            }
        })
    }
    //loadRecords();
</script>

<script>
    function leaveLike(typeOfEntity,idOfEntity){
        $.ajax({
            url: "/leaveLike",
            async: false,
            data: {
                type: typeOfEntity,
                id: idOfEntity
            },
            success: function (data) {
                loadCountLikes(typeOfEntity,idOfEntity);
            }
        })
    }

    function nextImage(){
    }
</script>

<script>
    function updateCommentsGo(imageId){
        $.ajax({
            url: "/loadCommentsUnderImage",
            async: false,
            method: "get",
            dataType: "json",
            data: {
                id: imageId
            },
            success: function(data){
                $.each(data,function(k,v){
                    var aHref = document.createElement("a");
                    aHref.href = "/user/"+v.id;
                    var mainDiv = document.createElement("div");
                    mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ v.userUrlImage+"); border-radius:50%;";
                    aHref.appendChild(mainDiv);
                    var divP = document.createElement("div");
                    divP.style = "float:left; width: 60%;";
                    var textP = document.createElement("p");
                    textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
                    textP.innerHTML = v.text;
                    var clearP = document.createElement("p");
                    clearP.style = "clear:left;";
                    divP.appendChild(textP);

                    document.getElementById("comments").appendChild(aHref);
                    document.getElementById("comments").appendChild(divP);
                    document.getElementById("comments").appendChild(clearP);
                })
            }
        })
    }
</script>

<script>
    function openPhotoUser(idPhoto){
        var element = document.getElementById("popupWin");
        while(element.firstChild) element.removeChild(element.firstChild);

        $("#popupWin").append("<div style='width: 75%; height: 64%; float: left;margin-top: 20px; margin-left: 30px;background-image: url(${user.newestImageSrc});background-repeat: no-repeat; background-size: cover; cursor: hand;' class='magnify' onclick='nextImage()'></div>");
        $("#popupWin").append("<textarea id = 'textAr' style='height: 50px; width:50%; float: left; margin-top: 20px; margin-left: 30px;' placeholder='Введіть повідомлення: '></textarea>");
        $("#popupWin").append("<button onclick='leaveComment(${user.newestImageId})' class='sendButtonStyle' style='float: left; margin-left: 10px; margin-top: 40px;'>Send</button>");
        //наступні 2 append мають виконуватись при умові c:if test="{user.newestImageId != 0">
        $("#popupWin").append("<img  src='/resources/img/icons/like.png' id = 'likeImg' style='float:left; width:16px;height:14px; margin-top: 20px; margin-left: 20px; cursor: hand;'>");
        $("#popupWin").append("<span id = 'countLikesUnderPhoto' style='float: left; margin-top: 20px; margin-left: 7px;'></span>");
        $("#popupWin").append("<div id = 'comments' style='width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;'>");

        $("#likeImg").click(function(){
            leaveLike('image',idPhoto);
        })

        header();

        updateCommentsGo(idPhoto);
        loadCountLikes('image',idPhoto);
    }

    function loadCountLikes(typeEntity,idEntity){
        //alert(idEntity);
        $.ajax({
            url: "/getCountLikesOfEntity/"+idEntity,
            method: "get",
            dataType: "json",
            async: false,
            data:{
                type: typeEntity
            },
            success: function (data) {
                $("#countLikesUnderPhoto").html(data.countLikes);
                if (!data.liked){
                    var image = document.getElementById("likeImg");
                    image.src = "/resources/img/icons/likeClear.png";
                } else {
                    var image = document.getElementById("likeImg");
                    image.src = "/resources/img/icons/like.png";
                }
            }
        })
    }

    function leaveComment(imageId){
        $.ajax({
            url: "/leaveComment/"+imageId,
            async: false,
            dataType: "json",
            data: {
                text: $("#textAr").val()
            },
            success: function (data) {
                var aHref = document.createElement("a");
                aHref.href = "/user/"+data.id;
                var mainDiv = document.createElement("div");
                mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ data.imageSrc+"); border-radius:50%;";
                aHref.appendChild(mainDiv);
                var textP = document.createElement("p");
                textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
                textP.innerHTML = data.text;
                var clearP = document.createElement("p");
                clearP.style = "clear:left;"

                var first=document.getElementById("comments").childNodes[0];
                document.getElementById("comments").insertBefore(aHref,first);

                var second=document.getElementById("comments").childNodes[1];
                document.getElementById("comments").insertBefore(textP,second);

                var third=document.getElementById("comments").childNodes[2];
                document.getElementById("comments").insertBefore(clearP,third);

                //document.getElementById("comments").appendChild(mainDiv);
                //document.getElementById("comments").appendChild(textP);
                //document.getElementById("comments").appendChild(clearP);

                $("#textAr").val("");
            }
        })
    }
</script>




<script>
    function setActionToForm(){
        var textInput =  fixString($("#newRecord").val());
        //var textInput = $("#newRecord").val();
        if (textInput!="")
        $('#newRecordForm').attr('action', '/newRecordOf/${user.id}/'+textInput+'?${_csrf.parameterName}=${_csrf.token}');
        else $('#newRecordForm').attr('action', '/newRecordOf/${user.id}/there is no text here just sent to avoid mistake?${_csrf.parameterName}=${_csrf.token}');
    }
</script>

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
        //var fixStr = fixString($('#newRecord').val());
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
            success: function(v){

                  var elem = document.createElement("div");
                    elem.style = "width:80%; height:auto; background-color:white; float:left; margin-top:20px; border-bottom:1px solid grey;";
                    elem.setAttribute("id", v.id+"_div");
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
                    elemLeft.style = "width:50%; height:auto; background-color:white; float:left;margin-top:20px;";
                    var pThis = document.createElement("p");
                    pThis.innerHTML = v.text;
                    pThis.style = "float:left; margin-left:10px; margin-top:10px;";
                    elemLeft.appendChild(pThis);
                    elem.appendChild(elemLeft);

                    if ($("#userId").html() == $("#userAuthId").html()) {
                    var btn = document.createElement("button");
                    //btn.setAttribute("id",v.id+" button");
                    btn.setAttribute("id", v.id+"_button");
                    btn.textContent = "Delete";
                    }

                    elem.appendChild(btn);


                    var first=document.getElementById("records").childNodes[0];
                    document.getElementById("records").insertBefore(elem,first);
                    $("#newRecord").val("");
                    $("#"+ v.id+"_button").click(function(){
                        deleteRecord(v.id);
                    })
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

</body>
</html>
