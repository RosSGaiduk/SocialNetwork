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
<html lang = "en" ng-app = "app">
<head>
    <%--Всі лінки підключені в template.jsp--%>
    <!--Angular-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.5/angular.min.js"></script>
    <!--Angular-->
    <!--Ajax-->
    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
    <!--/Ajax-->
    <script type="text/javascript" src="/resources/scripts/angularControllers.js"></script>
</head>
<body>
<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 50px; cursor: hand;">
    <sec:authorize access="isAuthenticated()">
        <div class="userPhoto">
            <div onclick="openPhotoUser('${user.newestImageSrc}',${user.newestImageId})" id = "photoOfUser" style="
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

        <div id = "info" class = "userInfo" ng-controller = "myCtrl">
            <div style="width: 100%; height: 70%; float: left; float: left;">
                <c:if test="${user.isOnline}">
                    <h3 style="text-align: center; color: blue;" id = 'onlineCheck'>Online</h3>
                </c:if>
                <c:if test="${!user.isOnline}">
                    <h3 style="text-align: center; color: blue;" id = 'onlineCheck'>Was online: ${user.lastOnline}</h3>
                </c:if>
                <h2 style="text-align: center">${user.lastName} ${user.firstName}</h2>
                <h3 style="text-align: center">День народження: {{${birthDate} | date:'dd.MM.yyyy'}}</h3>
                <h3 style="text-align: center">Ім'я: ${user.firstName}</h3>
                <h3 style="text-align: center">Прізвище: ${user.lastName}</h3>
                <h3 id = "userId" style="text-align: center;visibility: hidden;">${user.id}</h3>
                <h3 id = "userAuthId" style="text-align: center;visibility: hidden;">${userAuth.id}</h3>
            </div>


            <div style="width: 90%; height: 30%; float: left; border-top: 1px solid gainsboro; float: left; margin-left: 5%;">
                <div style="width: 20%; height: 90%; float: left; margin-left: 10px; margin-top: 1%; border-right: 1px solid gainsboro; cursor: hand;"></div>
                <a href="/friendsOf/${user.id}" style="text-decoration: none">
                    <div class = "logos" style="background-image: url(/resources/img/icons/followers.jpg);"></div></a>
                <a href="/photosOf/${user.id}/MY_PAGE_PHOTOS">
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

        <p style="clear: left"/>
        <div class = "userVideo">
            <a href="/videosOf/${user.id}" style="margin-left: 10px;">Відео</a>
            <c:if test="${lastVideo != null}">
                <div class="videoBannerMain" onclick="showVideo(${lastVideo.id},'${lastVideo.url}','${lastVideo.name}')">
                    <c:if test="${lastVideo.urlImageBanner != null}">
                        <div class="videoBanner" style="background-image: url(${lastVideo.urlImageBanner})"></div>
                    </c:if>
                    <c:if test="${lastVideo.urlImageBanner == null}">
                        <div class="videoBanner" style="background-image: url(/resources/img/icons/videoBannerStandard.png)"></div>
                    </c:if>
                    <h3 style="float: left; margin-top: 5px;">${lastVideo.name}</h3>
                </div>
            </c:if>
            <p style="clear: left"/>
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

<div style="text-align: center; overflow: scroll; cursor: default; float:left;" id="popupWin" class="modalwin">
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
    <div id = "comments" style="width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;"></div>
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
    function previousImage(idPhoto){
        //alert(idPhoto);
        $.ajax({
            url: "/previousAvaOfUser/"+document.getElementById("userId").innerHTML,
            async: false,
            method: "get",
            dataType: "json",
            data: ({
                photoId: idPhoto
            }),
            success: function(data){
                var darkLayer = document.getElementById("shadow");
                darkLayer.parentNode.removeChild(darkLayer); //видаляєм затемнення, тому, що в наступному методі
                //який ми викликаєм знову встановлюється затемнення
                openPhotoUser(data.url,data.id);
            }
        })
    }


    function nextImage(idPhoto){
        //alert(idPhoto);
        $.ajax({
            url: "/nextAvaOfUser/"+document.getElementById("userId").innerHTML,
            async: false,
            method: "get",
            dataType: "json",
            data: ({
                photoId: idPhoto
            }),
            success: function(data){
                var darkLayer = document.getElementById("shadow");
                darkLayer.parentNode.removeChild(darkLayer); //видаляєм затемнення, тому, що в наступному методі
                //який ми викликаєм знову встановлюється затемнення
                openPhotoUser(data.url,data.id);
            }
        })
    }

</script>


<script>
    var time = 0;
    var remembered = false;
    var check = true;
    function openPhotoUser(urlPhoto,idPhoto){
        var element = document.getElementById("popupWin");
        while(element.firstChild) element.removeChild(element.firstChild);
        var imageDetails = getDetailsOfPhoto(idPhoto);
        var width = 384*imageDetails.ratio;
        //$("#idBlock").css("width",384*imageDetails.ratio);
        var marginLeft = ($("#popupWin").width()-width)/2.0;
        //$("#idBlock").css("margin-left",marginLeft);
        $("#popupWin").append("<img src = '/resources/img/icons/icon-arrow-back-128.png' width='32' height='32' style = 'float:left; clear:left; cursor:hand;' onclick='nextImage("+idPhoto+")'>");
        $("#popupWin").append("<div id = 'idBlock' style='width: "+width+"px; height: 384px; float: left; margin-left: "+marginLeft+"px;background-image: url("+urlPhoto+");background-repeat: no-repeat; background-size: cover; cursor: hand;' class='magnify' onclick='previousImage("+idPhoto+")'></div>");
        $("#popupWin").append("<textarea id = 'textAr' style='height: 50px; width:50%; float: left; margin-top: 20px; margin-left: 30px;' placeholder='Введіть повідомлення: '></textarea>");
        $("#popupWin").append("<button onclick='leaveComment("+idPhoto+")' class='sendButtonStyle' style='float: left; margin-left: 10px; margin-top: 40px;'>Send</button>");
        //наступні 2 append мають виконуватись при умові c:if test="{user.newestImageId != 0">
        $("#popupWin").append("<img  src='/resources/img/icons/like.png' id = 'likeImg' style='float:left; width:16px;height:14px; margin-top: 20px; margin-left: 20px; cursor: hand;'>");
        $("#popupWin").append("<span id = 'countLikesUnderPhoto' style='float: left; margin-top: 20px; margin-left: 7px;'></span>");
        $("#popupWin").append("<div id = 'comments' style='width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;'>");
        $("#idBlock").append("<div id = 'windowLikeId' class = 'windowsWithLikes' style='display: none;'></div>")
        $("#likeImg").click(function(){
            leaveLike('image',idPhoto);
        });
        loadUsersThatLeftLikeWithLimit(idPhoto);
        $("#likeImg").mouseover(function(){
            $("#windowLikeId").css("display","block");
            remembered = false;
            check = false;
            //alert(remembered);
            console.log(check);
        })
        $("#likeImg").mouseout(function(){
            if (!remembered) {
                time = (new Date).getTime();
                remembered = true;
            }
            check = true;
            //alert(remembered);
            console.log(check);
        })
        $("#windowLikeId").mouseover(function(){
            check = false;
            $("#windowLikeId").css("display","block");
        })
        $("#windowLikeId").mouseout(function(){
            check = true;
            //$("#windowLikeId").css("display","none");
        })
        $("#windowLikeId").click(function(){
            $("#popupWin").append("<div id = 'usersLikedBigBanner' class = 'bigBannerWithLikes'><button id = 'closeBtn' style='width: 10%; height: 5%; margin-left: 89%;'>Close</button><p style='clear: left'/></div>");
            $("#closeBtn").click(function(){
                $("#usersLikedBigBanner").remove();
                $("#idBlock").click(function(){
                    previousImage(idPhoto);
                })
            })
            $.ajax({
                url: "/loadAllUsersWhoLikedImage/"+idPhoto,
                method: "get",
                async: false,
                dataType: "json",
                data: ({
                }),
                success: function(data){
                    //alert(data.length);
                    $.each(data,function(k,v){
                        var aMain = document.createElement("a");
                        aMain.href = "/user/"+ v.id;
                        var mainDiv = document.createElement("div");
                        mainDiv.setAttribute("class","userThatLikedImageDiv");
                        var divImage = document.createElement("div");
                        divImage.setAttribute("class","userThatLikedImageImg");
                        divImage.style = "background-image: url("+v.urlImage +");";
                        var pInfo= document.createElement("p");
                        pInfo.innerHTML = v.name+" "+ v.lastName;
                        mainDiv.appendChild(divImage);
                        mainDiv.appendChild(pInfo);
                        aMain.appendChild(mainDiv);
                        document.getElementById("usersLikedBigBanner").appendChild(aMain);
                    })
                }
            })
            $("#idBlock").prop('onclick',null).off('click');
            /*$("#idBlock").click(function(){
             previousImage(idPhoto);
             });*/
            //$('...').prop('onclick',null).off('click');
        })
        header();
        updateCommentsGo(idPhoto);
        loadCountLikes('image',idPhoto);
    }
    function showBlockTime(){
        console.log(check);
        if (check) {
            var currentTime = (new Date).getTime();
            var difference = parseInt(currentTime - time);
            if (difference < 100) {
                $("#windowLikeId").css("display", "block");
            } else {
                $("#windowLikeId").css("display", "none");
            }
        } else {
            $("#windowLikeId").css("display", "block");
        }
        /*alert("current time: "+currentTime+" time: "+time+" difference: "+parseInt(currentTime-time));*/
    }
    var id1 = setInterval("showBlockTime()",100);
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
                    elem.appendChild(btn);
                }
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

<script>
    function showVideo(id,url,name){
        var element = document.getElementById("popupWin");
        while (element.firstChild) element.removeChild(element.firstChild);
        header();
        $("#popupWin").append("<video id='my-video' controls preload='auto' width='800' height='464'"+
                "poster='' style='cursor: hand;'>"+
                "<source src='"+url+"' type='video/mp4'></video><h1 style='text-align: left;'>"+name+"</h1>");
        $("#popupWin").append("<textarea id = 'videoTextArea' style='height: 50px; width:50%; float: left; margin-top: 20px; margin-left: 30px;' placeholder='Введіть повідомлення: '></textarea>");
        $("#popupWin").append("<button onclick='leaveCommentUnderVideo("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px; margin-top: 40px;'>Send</button>");
        $("#popupWin").append("<div id = 'comments' style='width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;'>");
        updateCommentsOfVideo(id);
        $("#my-video").click(function(){
            playVideo();
        })
    }
    function playVideo(){
        if ($("#my-video").get(0).paused) $("#my-video").get(0).play();
        else $("#my-video").get(0).pause();
    }
</script>
</body>
</html>