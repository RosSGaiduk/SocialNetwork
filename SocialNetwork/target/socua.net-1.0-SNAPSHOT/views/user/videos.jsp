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

<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 80px; background-color: white">
    <%--<form:form action="/videoProcess.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
               enctype="multipart/form-data" cssStyle="float: left;">
        <input type="file" name="file1"  style="float: left;"/>
        <input type="submit" value="Upload" style="float: left;">
    </form:form>--%>
    <p style="clear: left"/>
    <c:forEach items="${videosAll}" var="vid">
        <%--<a href="/video/${vid.id}" style="text-decoration: none; color: black;">--%>
        <div id = "video_${vid.id}" class="videoBannerMain" onclick="showVideo(${vid.id},'${vid.url}','${vid.name}')">
            <c:if test="${vid.urlImageBanner != null}">
                <div class="videoBanner" style="background-image: url(${vid.urlImageBanner})"></div>
            </c:if>
            <c:if test="${vid.urlImageBanner == null}">
                <div class="videoBanner" style="background-image: url(/resources/img/icons/videoBannerStandard.png)"></div>
            </c:if>
            <h3 style="float: left; margin-top: 5px;">${vid.name}</h3>
        </div>
    </c:forEach>

    <div style="text-align: center; overflow: scroll; cursor: default; float:left;" id="popupWin" class="modalwin">
    </div>
</div>

<script>
    function showVideo(id,url,name){
        var element = document.getElementById("popupWin");
        while (element.firstChild) element.removeChild(element.firstChild);
        header();
        $("#popupWin").append("<video id='my-video' controls preload='auto' width='800' height='464'"+
                "poster='' style='cursor: hand;'>"+
                "<source src='"+url+"' type='video/mp4'></video><h1 style='text-align: left;'>"+name+"</h1>");
        if (!checkIfVideoBelongsToAuthUser(id))
            $("#popupWin").append("<button id = 'addingVideoButton' onclick='addVideoToUser("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px;'>Add</button><p style='clear: left;'/>");
        else $("#popupWin").append("<button id = 'removingVideoButton' onclick='deleteVideoFromUserPage("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px; background-color: orangered; border-color: orangered;'>Delete</button><p style='clear: left;'/>");
        $("#popupWin").append("<div style='float: left; margin-left: 70%; margin-top: -20px;'><img id = 'likeImgId' src='/resources/img/icons/like.png' onclick='leaveLikeUnderVideo("+id+")' style='float: left; cursor: hand;'><p id = 'countLikesUnderVideo' style='float: left; margin-left: 10px;'></p></div>");
        $("#popupWin").append("<textarea id = 'videoTextArea' style='height: 50px; width:50%; float: left; margin-top: 20px; margin-left: 30px;' placeholder='Введіть повідомлення: '></textarea>");
        $("#popupWin").append("<button onclick='leaveCommentUnderVideo("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px; margin-top: 40px;'>Send</button>");
        $("#popupWin").append("<div id = 'comments' style='width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;'>");
        $("#my-video").click(function(){
            playVideo();
        })
        checkIfUserLikedVideo(id);
        loadCountLikesUnderVideo(id);
        updateCommentsOfVideo(id);
    }
    function playVideo(){
            var isFirefox = typeof InstallTrigger !== 'undefined';
            if (isFirefox) return;
            if ($("#my-video").get(0).paused) $("#my-video").get(0).play();
            else $("#my-video").get(0).pause();
    }

    function loadCountLikesUnderVideo(idVideo){
        $.ajax({
            url: "/loadCountLikesUnderVideo/"+idVideo,
            async: false,
            method: "get",
            success: function (data) {
                $("#countLikesUnderVideo").html(data);
            }
        })
    }
    function checkIfUserLikedVideo(idVideo){
        $.ajax({
            url: "/checkIfUserLikedVideo/" + idVideo,
            async: false,
            method: "get",
            success: function (data) {
                if (data=="true"){
                    $("#likeImgId").attr("src","/resources/img/icons/like.png");
                } else {
                    $("#likeImgId").attr("src","/resources/img/icons/likeClear.png");
                }
            }
        })
    }
    function leaveLikeUnderVideo(idVideo){
        $.ajax({
            url: "/leaveLikeUnderVideo/" + idVideo,
            async: false,
            method: "get",
            dataType: "json",
            success: function (data) {
                if (data.liked){
                    $("#likeImgId").attr("src","/resources/img/icons/like.png");
                } else {
                    $("#likeImgId").attr("src","/resources/img/icons/likeClear.png");
                }
                $("#countLikesUnderVideo").html(data.countLikes);
            }
        })
    }
</script>
</body>
</html>
