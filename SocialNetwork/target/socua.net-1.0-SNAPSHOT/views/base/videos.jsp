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
<head><%--Всі лінки підключені в template.jsp--%>
    <!--Ajax-->
    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
    <!--/Ajax-->
</head>
<body>

<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 80px; background-color: white">
    <form:form action="/videoProcess.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
               enctype="multipart/form-data" cssStyle="float: left;">
        <input type="file" name="file1"  style="float: left;"/>
        <input type="submit" value="Upload" style="float: left;">
    </form:form>
    <p style="clear: left"/>
    <c:forEach items="${videoAll}" var="vid">
        <%--<a href="/video/${vid.id}" style="text-decoration: none; color: black;">--%>
            <div id = "video_${vid.id}"class="videoBannerMain" onclick="showVideo(${vid.id},'${vid.url}','${vid.name}')">
                    <c:if test="${vid.urlImageBanner != null}">
                    <div class="videoBanner" style="background-image: url(${vid.urlImageBanner})"></div>
                    </c:if>
                    <c:if test="${vid.urlImageBanner == null}">
                    <div class="videoBanner" style="background-image: url(/resources/img/icons/videoBannerStandard.png)"></div>
                    </c:if>
                <h3 style="float: left; margin-top: 5px;">${vid.name}</h3>
                <%--<input type="button" value="Add" style="float: left; position: relative;" onclick="addVideoToUser(${vid.id})">--%>
            </div>
        <%--</a>--%>
        <%--<button id = "button ${vid.id}"onclick="addVideoToUser(${vid.id})">Add</button>--%>
    </c:forEach>

        <div style="text-align: center; overflow: scroll; cursor: default; float:left;" id="popupWin" class="modalwin">
        </div>
</div>

        <script>
            function addVideoToUser(idVideo){
                //alert(idVideo);
                $.ajax({
                    url: "/addVideoToUser/"+idVideo,
                    async: false,
                    method: "get",
                    data:{
                    },
                    success: function(data){
                        $("#addingVideoButton").css("background-color", "orangered");
                        $("#addingVideoButton").css("border-color", "orangered");
                        $("#addingVideoButton").html("Delete");
                        $("#addingVideoButton").attr("id","removingVideoButton");
                        $("#removingVideoButton").prop('onclick',null).off('click');
                        $("#removingVideoButton").click(function(){
                            deleteVideoFromUserPage(idVideo);
                        })
                    }
                })
            }

            function deleteVideoFromUserPage(idVideo){
                //$("#video_"+idVideo).prop('onclick',null).off('click');
                $.ajax({
                    url: "/deleteVideoFromUserPage/"+idVideo,
                    method: "get",
                    async: false,
                    success: function(data){
                        $("#removingVideoButton").css("background-color", "#6ea0ff");
                        $("#removingVideoButton").css("border-color", "#6ea0ff");
                        $("#removingVideoButton").html("Add");
                        $("#removingVideoButton").attr("id","addingVideoButton");
                        $("#addingVideoButton").prop('onclick',null).off('click');
                        $("#addingVideoButton").click(function(){
                            addVideoToUser(idVideo);
                        })
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
        if (!checkIfVideoBelongsToAuthUser(id))
        $("#popupWin").append("<button id = 'addingVideoButton' onclick='addVideoToUser("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px;'>Add</button><p style='clear: left;'/>");
        else $("#popupWin").append("<button id = 'removingVideoButton' onclick='deleteVideoFromUserPage("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px; background-color: orangered; border-color: orangered;'>Delete</button><p style='clear: left;'/>");
        $("#popupWin").append("<textarea id = 'videoTextArea' style='height: 50px; width:50%; float: left; margin-top: 20px; margin-left: 30px;' placeholder='Введіть повідомлення: '></textarea>");
        $("#popupWin").append("<button onclick='leaveCommentUnderVideo("+id+")' class='sendButtonStyle' style='float: left; margin-left: 10px; margin-top: 40px;'>Send</button>");
        $("#popupWin").append("<div id = 'comments' style='width: 75%; height: auto; float:left; margin-left: 30px; margin-top: 20px;'>");
        $("#my-video").click(function(){
            playVideo();
        })
        updateCommentsOfVideo(id);
    }
    function playVideo(){
        if ($("#my-video").get(0).paused) $("#my-video").get(0).play();
        else $("#my-video").get(0).pause();
    }
</script>
</body>
</html>
