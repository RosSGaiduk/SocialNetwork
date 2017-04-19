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
<html lang = "en">
<head><%--Всі лінки підключені в template.jsp--%>
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

<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 80px; background-color: white">
    <form:form action="/videoProcess.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
    enctype="multipart/form-data" cssStyle="float: left;">
    <input type="file" name="file1"  style="float: left;"/>
   <%-- <input type="submit" value="Upload" style="float: left;">--%>
        <button type="submit" style="float: left;margin-top: 3px;" class="buttonFileStyle">Upload</button>
    </form:form>
    <p style="clear: left;"/>
    <div id = "allVideos">
       <div style="float: left; margin-top: 20px;">
            <input id = "searchVideo" type="text" class="inputStyle" onkeyup="findVideo()">
       </div>
        <p style="clear: left;"/>
        <h3 style="clear: left;margin-left: 20px; margin-top: 20px; border-top: 1px solid black;">Мої відео</h3>
        <div id = "myVideosFound"></div>
        <h3 style="clear: left; margin-left: 20px; margin-top: 20px; border-top: 1px solid black;">Інші відео</h3>
        <div id = "videosForEachFromServer">
         <%--<c:forEach items="${videoAll}" var="vid">
            <div id = "video_${vid.id}"class="videoBannerMain" onclick="showVideo(${vid.id},'${vid.url}','${vid.name}')">
                    <c:if test="${vid.urlImageBanner != null}">
                    <div class="videoBanner" style="background-image: url(${vid.urlImageBanner})"></div>
                    </c:if>
                    <c:if test="${vid.urlImageBanner == null}">
                    <div class="videoBanner" style="background-image: url(/resources/img/icons/videoBannerStandard.png)"></div>
                    </c:if>
                <h3 style="float: left; margin-top: 5px;">${vid.name}</h3>
            </div>
         </c:forEach>--%>
    </div>
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
        $("#popupWin").append("<div id = 'similarVideos' style='float: left; width: 20%; height: 100%;'></div>");
        $("#my-video").click(function(){
            playVideo();
        })
        checkIfUserLikedVideo(id);
        loadCountLikesUnderVideo(id);
        updateCommentsOfVideo(id);
        findVideosLikeTheVideoWeOpened(id,name);
    }
    function playVideo(){
        var isFirefox = typeof InstallTrigger !== 'undefined';
        if (isFirefox) return;
        if ($("#my-video").get(0).paused) $("#my-video").get(0).play();
        else $("#my-video").get(0).pause();
    }


    function findVideo() {
        $.ajax({
            url: "/findVideos",
            async: false,
            data: {
                text: $("#searchVideo").val()
            },
            dataType: "json",
            success: function (data) {
                $("#videosForEachFromServer").html("");
                $("#myVideosFound").html("");
                $.each(data, function (k, v) {
                    if (v.myVideo)
                        $("#myVideosFound").append("<div id = '" + v.idVideo + "' class='videoBannerMain'><div class='videoBanner' style='background-image: url(" + v.urlImage + ")'></div><h3 style='float: left; margin-top: 5px;'>" + v.nameVideo + "</h3></div>");
                    else $("#videosForEachFromServer").append("<div id = '" + v.idVideo + "' class='videoBannerMain'><div class='videoBanner' style='background-image: url(" + v.urlImage + ")'></div><h3 style='float: left; margin-top: 5px;'>" + v.nameVideo + "</h3></div>");
                    $("#" + v.idVideo).click(function () {
                        var element = document.getElementById("popupWin");
                        while (element.firstChild) element.removeChild(element.firstChild);
                        showVideo(v.idVideo, v.urlVideo, v.nameVideo);
                    })
                })
            }
        })
    }
        function findVideosLikeTheVideoWeOpened(id,value) {
            $.ajax({
                url: "/findVideosLikeOpened",
                async: false,
                data: {
                    text: value,
                    videoId: id
                },
                dataType: "json",
                success: function (data) {
                    //$("#videosForEachFromServer").html("");
                    //$("#myVideosFound").html("");
                    $.each(data, function (k, v) {
                        $("#similarVideos").append("<div id = 'subVideo_" + v.idVideo + "' style='width: 100%; height: 100px; margin-top: 30px;'>" +
                                "<div style='width: 100%; height: 80px; border: 1px solid black; background-size: cover; background-image: url(" + v.urlImage + ")'></div><span style='float: left; margin-top: 5px;'>" + v.nameVideo + "</h3></div>");
                        $("#subVideo_" + v.idVideo).click(function () {
                            //alert("asda");
                            var darkLayer = document.getElementById('shadow');
                            darkLayer.parentNode.removeChild(darkLayer);
                            showVideo(v.idVideo, v.urlVideo, v.nameVideo);
                        })
                    })
                }
            })
        }
    findVideo();
</script>
</body>
</html>
