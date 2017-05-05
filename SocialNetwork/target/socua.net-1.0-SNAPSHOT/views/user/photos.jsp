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
<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 50px;">
    <div style="float: left;width: 40%; margin-left: 30%;">
        <ul id = "selectAlbum"> <%--onchange="checkImagesFromAlbum()">--%>
            <%--<li><a href="/photosOf/${userPageId}/*" style="text-decoration: none;">*</a></li>--%>
            <c:forEach items="${albums}" var="a">
             <li><a href="/photosOf/${userPageId}/${a.name}" style="text-decoration: none;">${a.name}</a></li>
            </c:forEach>
        </ul>
        <%--<p id = "userAuthId" style="visibility: hidden;">${userAuthId}</p>
        <p id = "userPageId" style="visibility: hidden;">${userPageId}</p>--%>
    </div>
    <c:if test="${userAuthId==userPageId}">
        <a href="/createAlbumPage"><button>Create album</button></a>
    </c:if>
    <p style="clear: left"/>
    <div id = "photosAll">
     <c:forEach items="${images_all}" var="im">
        <div class="albumPhotos">
            <%--<a rel="simplebox" href="${im.urlOfImage}" class="aUrl" id="imgId">--%>
                <img src="${im.urlOfImage}" style="width: 100%; height: 100%;" onclick="openPhotoUser('${im.urlOfImage}',${im.id})">
            <%--</a>--%>
            <c:if test="${userAuthId==userPageId}">
            <c:if test="${album.name != 'MY_PAGE_PHOTOS'}">
            <select style="float: left;width:80px;" id="checkAlbum_${im.id}">
                <c:forEach items="${albums}" var="a">
                    <c:if test="${im.albumIdPattern != a.id}">
                    <option>${a.name}</option>
                    </c:if>
                </c:forEach>
            </select>
                <button style="float: left;" onclick="addImgToAlbum('checkAlbum_${im.id}')">OK</button>
            </c:if>
            </c:if>
        </div>
    </c:forEach>
        <c:if test="${userAuthId == userPageId}">
        <div class="albumPhotos">
                <form:form action="/loadImageToAlbum/${album.id}/?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data" cssStyle="float: left;">
                <input type="file" name="file1"  style="float: left;"/>
                <button type="submit" style="float: left;margin-top: 3px;" class="buttonFileStyle">Upload</button>
            </form:form>
        </div>
        </c:if>
    </div>
</div>

<div style="text-align: center; overflow: scroll; cursor: default; float:left;" id="popupWin" class="modalwin">
</div>



<script>
    var time = 0;
    var remembered = false;
    var check = true;
    function openPhotoUser(urlPhoto,idPhoto){
        //var element = document.getElementById("popupWin");
        //while(element.firstChild) element.removeChild(element.firstChild);

        var imageDetails = getDetailsOfPhoto(idPhoto);
        var width = 384*imageDetails.ratio;
        var marginLeft = ($("#popupWin").width()-width)/2.0;

        $("#popupWin").append("<div id = 'idBlock' style='width: "+width+"px; height: 384px; float: left;margin-top: 20px; margin-left: "+marginLeft+"px;background-image: url("+urlPhoto+");background-repeat: no-repeat; background-size: cover; cursor: hand;' class='magnify' onclick='previousImageOfAlbum("+idPhoto+")'></div>");
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
        })

        header();
        updateCommentsGo(idPhoto);
        loadCountLikes('image',idPhoto);
    }

    function previousImageOfAlbum(idPhoto){
        //alert(idPhoto);
        $.ajax({
            url: "/previousImageOfAlbum",//ЯКИЙ АЛЬБОМ ВИЗНАЧАЄТЬСЯ ПО ID ФОТКИ
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
    }

    var id1 = setInterval("showBlockTime()",100);
</script>

<script>
    function addImgToAlbum(id) {
        //alert($('#'+id+' option:selected').text());
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




<%--<script type="text/javascript">(
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
</script>--%>






<%--<script>
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
</script>--%>

</body>
</html>
