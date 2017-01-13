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
<div style="width: 60%; height: 100%; margin-left: 20px; max-width: 60%; float: left; overflow: scroll; margin-top: 50px;">
    <div style="float: left;width: 40%; margin-left: 30%;">
        <select id = "selectAlbum" style="width: 30%;" onchange="checkImagesFromAlbum()">
            <option id = "allPhotos">*</option>
            <c:forEach items="${albums}" var="a">
                <option>${a.name}</option>
            </c:forEach>
        </select>
        <p id = "userAuthId" style="visibility: hidden;">${userAuthId}</p>
        <p id = "userPageId" style="visibility: hidden;">${userPageId}</p>
    </div>

    <div id = "photosAll">
    <%--<c:forEach items="${images_all}" var="im">
        <div id = "photo" style="width: 300px; height: 300px; background-repeat: no-repeat; background-size: cover;float: left; margin-left: 10px; margin-top: 10px; cursor: hand; margin-top: 20px;;
        background-image: url(${im.urlOfImage});" onclick="zoom()">
            <c:if test="${userAuthId==userPageId}">
            <select style="float: left; margin-top: 300px; width:80px;" id="checkAlbum">
                <c:forEach items="${albums}" var="a">
                    <option>${a.name}</option>
                </c:forEach>
            </select>
            <button style="float: left; margin-top: 300px" onclick="addImgToAlbum(${im.id})">OK</button>
            </c:if>
        </div>
    </c:forEach>
    </div>
    <c:if test="${userAuthId==userPageId}">
    <a href="/createAlbumPage"><button>Create album</button></a>
    </c:if>--%>
</div>


<script>
    function addImgToAlbum(id) {
        //alert($('#'+id+' option:selected').text());
        $.ajax({
            url: "/addPhotoToAlbum",
            data: ({
                idPhoto: id,
                nameAlbum: $('#'+id+' option:selected').text()
            }),
            async: false,
            success: function (data) {
                //alert(id);
                checkImagesFromAlbum();
            }
        });
    }
</script>

<script>
    function checkImagesFromAlbum(){
        var element = document.getElementById("photosAll");
        while(element.firstChild) element.removeChild(element.firstChild);
        $.ajax({
           url: "/checkPhotosFromAlbumOfUser",
            data: ({
                idUserChecked: $('#userPageId').html(),
                nameAlbum: $('#selectAlbum').val()
            }),
            async: false,
            dataType: "json",
            success: function(data){
                var index = 0;
                $.each(data,function(k,v){
                    var url = v.url;
                    var iD = v.idOfImg;
                var elem = document.createElement("div");
                    elem.style = "width: 300px; height: 300px; background-repeat: no-repeat; background-size: cover;float: left; margin-left: 10px; margin-top: 10px; cursor: hand; margin-top: 20px;  background-image: url("+ url+");";
                    if (document.getElementById("userAuthId").innerHTML == document.getElementById("userPageId").innerHTML) {
                        index++;
                        var sel = document.createElement("select");
                        sel.style = "float: left; margin-top: 300px; width:80px;";
                        sel.id = iD;
                        $.each(v.albums,function(k,v1) {
                            if (v1 != v.albumOfPhoto) {
                                var option = document.createElement("option");
                                option.text = v1;
                                sel.appendChild(option);
                            }
                        });
                        elem.appendChild(sel);
                        var btn = document.createElement("button");
                        btn.style = "float: left; margin-top: 300px";
                        btn.id = "btn" + iD;
                        btn.textContent = btn.id;
                        elem.appendChild(btn);
                        document.getElementById("photosAll").appendChild(elem);
                        $('#btn' + iD).on("click", function () {
                            addImgToAlbum(iD);
                        });
                    }
                });
            }
        });
    }
</script>

<script>
    checkImagesFromAlbum();
</script>

</body>
</html>
