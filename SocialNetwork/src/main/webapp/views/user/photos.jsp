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
    <%--Всі лінки підключені в template.jsp--%>
</head>
<body id="main">
<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
<div style="width: 60%; height: 100%; margin-left: 20px; max-width: 60%; float: left; overflow: scroll; margin-top: 50px;">
    <div style="float: left;width: 40%; margin-left: 30%;">
        <ul id = "selectAlbum"> <%--onchange="checkImagesFromAlbum()">--%>
            <li><a href="/photosOf/${userPageId}/*" style="text-decoration: none;">*</a></li>
            <c:forEach items="${albums}" var="a">
             <li><a href="/photosOf/${userPageId}/${a}" style="text-decoration: none;">${a}</a></li>
            </c:forEach>
        </ul>
        <p id = "userAuthId" style="visibility: hidden;">${userAuthId}</p>
        <p id = "userPageId" style="visibility: hidden;">${userPageId}</p>
    </div>
    <div id = "photosAll" style="border: 1px coral;">
     <c:forEach items="${images_all}" var="im">
        <div class="albumPhotos">
            <a rel="simplebox" href="${im.urlOfImage}" class="aUrl" id="imgId">
                <img src="${im.urlOfImage}" style="width: 100%; height: 90%;">
            </a>
            <c:if test="${userAuthId==userPageId}">
            <select style="float: left;width:80px;" id="checkAlbum_${im.id}">
                <c:forEach items="${albums}" var="a">
                    <option>${a}</option>
                </c:forEach>
            </select>
            <button style="float: left;" onclick="addImgToAlbum('checkAlbum_${im.id}')">OK</button>
            </c:if>
        </div>
    </c:forEach>
        <c:if test="${userAuthId==userPageId}">
            <a href="/createAlbumPage"><button>Create album</button></a>
        </c:if>
    </div>
</div>

<script>
    function addImgToAlbum(id) {
        alert($('#'+id+' option:selected').text());
        $.ajax({
            url: "/addPhotoToAlbum",
            data: ({
                idPhoto: id.split("_")[1],
                nameAlbum: $('#'+id+' option:selected').text()
            }),
            async: false,
            success: function (data) {
                window.location.href = "/photosOf/${userPageId}/"+ $('#'+id+' option:selected').text();
            }
        });
    }
</script>

<%--<script>
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
                    elem.className = "albumPhotos";
                    if (document.getElementById("userAuthId").innerHTML == document.getElementById("userPageId").innerHTML) {
                            var aElem = document.createElement("a");
                            aElem.className = "aUrl";
                            aElem.rel = "simplebox";
                            aElem.href = url;
                            aElem.id = "imgId";
                            var myImg = document.createElement("img");
                            myImg.src = url;
                            myImg.style="width: 100%; height: 90%;"
                            aElem.appendChild(myImg);
                            elem.appendChild(aElem);
                            $("#photosAll").append(elem);
                    }
                });
            }
        });
    }
</script>--%>

<script type="text/javascript">(
        function(){
            var boxes=[],els,i,l;
                els=document.querySelectorAll('a[rel=simplebox]');
                Box.getStyles('simplebox_css','/resources/css/simplebox.css');
                Box.getScripts('simplebox_js','/resources/scripts/simplebox.js',function(){
                    simplebox.init();
                    for(i=0,l=els.length;i<l;++i)
                        simplebox.start(els[i]);
                    simplebox.start('a[rel=simplebox_group]');
                });
        })
();
</script>

<script>
    var urls = document.getElementsByClassName("aUrl");
    var index = 0;
    var f = function() {
        function eventHandler(event){
            console.log(event.keyCode);
            if (event.keyCode == 39){
                if (index<urls.length-1)
                index++;
                else index = 0;
            } else if (event.keyCode == 37){
                if (index==0) index = urls.length-1;
                else index--;
            }
            console.log(index);
            var val = urls[index];
            console.log(val);
            var link = document.getElementById("imgId");
            link.setAttribute("href",urls[index].getElementsByTagName('img')[0].src);
            document.getElementById("imgId").click();
        }
        var elem = document.getElementById("main");
        elem.addEventListener('keyup',eventHandler,false);
    }
    window.addEventListener("load",f,false);
</script>

</body>
</html>
