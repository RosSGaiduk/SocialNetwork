<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 06.03.2017
  Time: 18:57
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

<div style="float: left; width: 40%; height: auto; margin-top: 135px; background-color: white;">
    <h1 style="margin-left: 20px;">${community.title}</h1>
    <h4 style="margin-left: 20px;">${community.description}</h4>
    <c:if test="${belong}">
    <div style="width: 100%; height: 1px; background-color: gainsboro;"></div>
    <textarea id = "newRecord" style="height: 50px; width:50%; float: left" placeholder="Введіть повідомлення: "></textarea>
    <form:form id = "newRecordForm" action="/uploadRecordToCommunity/${community.id}/?${_csrf.parameterName}=${_csrf.token}"  method="post"
               enctype="multipart/form-data" cssStyle="float: left; margin-top: 10px;">
        <input type="file" name="file1" style="float: left;"/>
        <button onclick="setActionToForm()" type="submit" style="float: left;margin-top: 3px;" class="buttonFileStyle">Upload</button>
    </form:form>
    </c:if>
    <div id = "records">
    <c:forEach items="${records}" var="rec">
        <div style="width: 95%; height: auto; float: left; cursor: hand; margin-left: 20px; margin-top: 20px;" id = "${rec.id} div" onclick="openRecord('${rec.type}','${rec.text}','${rec.url}','${rec.nameRecord}')">
            <p style="margin-bottom: 20px;">${rec.text}</p>
            <c:if test="${rec.type == 'image'}">
                <img src="${rec.url}" style="background-size: cover; background-repeat: no-repeat; float: left;" width="300" height="200">
            </c:if>
            <c:if test="${rec.type == 'audio'}">
                <p style="margin-top: 10px;">${rec.nameRecord}</p>
                <audio controls style="margin-top: 10px;">
                    <source src="${rec.url}" type="audio/mpeg" style="cursor: hand">
                    Your browser does not support the audio element.
                </audio>
            </c:if>
            <p style="clear: left"/>
            <c:if test="${belong}">
            <button onclick="deleteRecord(${rec.id})" style="margin-top: 10px;">Delete</button>
            </c:if>
            <div style="width: 100%; height: 1px; background-color: gainsboro; float: left; margin-top: 10px;"></div>
        </div>
    </c:forEach>
    </div>
</div>



<div style="float: left;width: 240px; height: auto; margin-top: 135px; background-color: white; margin-bottom: 30px; margin-left: 10px;">
        <img src="${community.urlImage}" width="220px;" height="220px;" style="background-size: cover; background-repeat: no-repeat; margin-left: 10px; margin-top: 10px;">
        <c:if test="${!subscribed}">
        <button id = "subscribeBtn" onclick="subscribe()" style="width: 120px; height: 30px; margin-left: 60px; margin-top: 10px;">Підписатись</button>
        </c:if>

        <c:if test="${subscribed}">
        <button id = "subscribeBtn" onclick="subscribe()" style="width: 120px; height: 30px; margin-left: 60px; margin-top: 10px;">Відписатись</button>
        </c:if>

    <c:if test="${belong}">
    <form:form action="/uploadCommunityLogo/${community.id}?${_csrf.parameterName}=${_csrf.token}" method="post"
               enctype="multipart/form-data" cssStyle="float: left; margin-top: 10px;">
        <input type="file" name="file1" style="float: left;"/>
        <button type="submit" style="float: left;margin-top: 3px;" class="buttonFileStyle">Upload</button>
        </form:form>
    </c:if>
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Фото якихось 6 підписників-->
        <p onclick="showModalWinWithSubscribers()" style="cursor: hand;">
         <span style="margin: 20px 0px 0px 20px; color: blue;"><strong>Підписники <span id = "countSubscribers">${community.countSubscribers}</span></strong></span>
        </p>
        <div id = "subscribersBlock" style="float: left; width: 100%; height: 120px; margin-top: 30px;">
            <c:forEach items="${subscribers}" var="s">
                <a href="/user/${s.id}">
                <div class="imageSubscriberInCommunity" style="background-image: url(${s.newestImageSrc});">
                    <div class="underImageSubscriberInCommunityInfo">
                        ${s.firstName}
                    </div>
                </div>
                </a>
                <%--<img src="${s.newestImageSrc}" width="70" height="70" style="background-size: cover; border-radius: 50%;">--%>
            </c:forEach>
        </div>
        <!--Між блоками завжди така дів-->
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Фотоальбоми-->
        <div style="float: left; width: 100%; height: 120px; margin-top: 50px;"></div>
        <!--Між блоками завжди така дів-->
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Аудіозаписи-->

        <p onclick="showModalWinWithMusics()" style="text-decoration: none; cursor: hand;">
            <span style="margin: 20px 0px 0px 20px; color: blue;"><strong>Аудіозаписи</strong></span>
        </p>

        <div style="float: left; width: 100%; height: auto; margin-top: 0px;">
            <c:forEach items="${musics}" var="mus">
                <p style="clear: left"/>
                <p style="margin-top: 10px;">${mus.nameOfSong}</p>
                <audio controls style="margin-top: 10px; width: 100%;">
                    <source src="${mus.urlOfSong}" type="audio/mpeg" style="cursor: hand">
                    Your browser does not support the audio element.
                </audio>
                <%--<button id = "button ${mus.id}"onclick="addMusicToUser(${mus.id})">Add</button>--%>
            </c:forEach>
        </div>
        <!--Між блоками завжди така дів-->
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Відеозаписи-->
        <div style="float: left; width: 100%; height: 120px; margin-top: 50px;"></div>
</div>

<div style="text-align: center; overflow: scroll;" id="popupWin" class="modalwin">
    <%--<form:form action="/musicToCommunity/${community.id}?${_csrf.parameterName}=${_csrf.token}" method="post"
               enctype="multipart/form-data" cssStyle="float: left;">
        <input type="file" name="file1"  style="float: left;"/>
        <input type="submit" value="Upload" style="float: left;">
    </form:form>--%>


</div>



<script>
    function setActionToForm(){
        var textInput =  $("#newRecord").val();
        if (textInput!="")
            $('#newRecordForm').attr('action', '/uploadRecordToCommunity/${community.id}/'+textInput+'?${_csrf.parameterName}=${_csrf.token}');
        else $('#newRecordForm').attr('action', '/uploadRecordToCommunity/${community.id}/there is no text here just sent to avoid mistake?${_csrf.parameterName}=${_csrf.token}');
    }
</script>

<script>
    function openRecord(recordType,recordText,recordUrl,recordName){
        //alert("clicked");
        header();
        //alert("TYPE: "+recordType+"\nTEXT: "+recordText+"\nURL: "+recordUrl+"\nNAME: "+recordName);

        var main = document.getElementById("popupWin");
        while (main.firstChild) (main.removeChild(main.firstChild));

        switch (recordType){
            case "": {
                var element = document.createElement("p");
                element.style = "margin-top: 25%; width: 60%; margin-left:20%;";
                element.innerText = recordText;
                document.getElementById("popupWin").appendChild(element);
            } break;

            case "audio": {
                var divMain = document.createElement("div");
                divMain.style = "float:left; margin-left:30px;margin-top:25%;width:80%";

                var pNameSong = document.createElement("p");
                pNameSong.style = "float:left; margin-top: 10px; text-align:left; margin-left:30px;";
                pNameSong.innerHTML = recordName;

                var audio = document.createElement("audio");
                audio.style = "float:left; width: 50%; margin-left:30px; margin-top:10px;";
                audio.controls = "true";

                var source = document.createElement("source");
                source.src = recordUrl;
                source.type = "audio/mpeg";

                var pElem = document.createElement("p");
                pElem.style = "float:left;margin-left:30px; margin-top:20px; text-align:left; clear:left;";
                pElem.innerHTML = recordText;

                audio.appendChild(source);
                divMain.appendChild(pNameSong);
                divMain.appendChild(audio);
                divMain.appendChild(pElem)
                main.appendChild(divMain);
            } break;
            case "image": {
                var mainDiv = document.createElement("div");
                mainDiv.style = "width: 75%; height: 64%; float: left; margin-top: 20px; margin-left: 30px;" +
                        "background-image: url("+recordUrl+");"+
                "background-repeat: no-repeat; background-size: cover; cursor: hand;";

                var elementText = document.createElement("p");
                elementText.style = "float:left; margin-top:10px; margin-left: 30px; text-align: left; clear:left;";
                elementText.innerText = recordText;

                main.appendChild(mainDiv);
                main.appendChild(elementText);
            } break;
        }
    }
</script>

<script>
    function subscribe(){
        $.ajax({
            url: "/subscribe/${community.id}",
            method: "get",
            async: "false",
            data: {
                userId: ${userAuth.id},
            },
            success: function (data) {
                //alert(data);

                if (data.split("|")[0]=='subscribed') {
                    $("#subscribeBtn").text("Відписатись");

                }
                else {
                    $("#subscribeBtn").text("Підписатись");
                }
                document.getElementById("countSubscribers").innerHTML = data.split("|")[1];
                updateSubscribersLogos();
            }
        })
    }


    function updateSubscribersLogos(){
        $.ajax({
            url: "/updateLogosSubscribersOfCommuity/${community.id}",
            async: false,
            data: {

            },
            dataType: "json",
            method: "get",
            success: function(data){
                var element = document.getElementById("subscribersBlock");
                while (element.firstChild) element.removeChild(element.firstChild);
                //$("#popupWin div").remove();

                $.each(data, function (k, v) {
                    var aMain = document.createElement("a");
                    aMain.href = "/user/"+ v.id;

                    var divImage = document.createElement("div");
                    divImage.setAttribute("class","imageSubscriberInCommunity");
                    divImage.style = "background-image: url("+v.urlImage +");";

                    var divInfo= document.createElement("div");
                    divInfo.setAttribute("class","underImageSubscriberInCommunityInfo");
                    divInfo.text = v.firstName;

                    divImage.appendChild(divInfo);
                    aMain.appendChild(divImage);
                    document.getElementById("subscribersBlock").appendChild(aMain);
                })
            }
        })
    }

</script>

<script type="text/javascript">


    function showModalWinWithSubscribers() {

        header();

        $.ajax({
            url: "/showAllSubscribersOfCommunity/${community.id}",
            dataType: "json",
            async: false,
            method: "get",
            data: {

            },
            success: function(data){
                var element = document.getElementById("popupWin");
                while (element.firstChild) element.removeChild(element.firstChild);

                //$("#popupWin div").remove();

                $.each(data,function(k,v){
                    var aElem = document.createElement("a");
                    aElem.href = "/user/"+ v.id;
                    var mainDiv = document.createElement("div");
                    mainDiv.style = "float:left; margin-left:20px; margin-top:10px; width:150px; height:150px; background-size: cover;";
                    var image = document.createElement("img");
                    image.setAttribute("src",v.urlImage);
                    image.style = "width:120px; height:120px; border-radius:50%;";
                    var pInfo = document.createElement("p");
                    pInfo.innerHTML = v.firstName+" "+ v.lastName;
                    mainDiv.appendChild(image);
                    mainDiv.appendChild(pInfo);
                    aElem.appendChild(mainDiv);
                    element.appendChild(aElem);
                })
            }
        })
    }


    function showModalWinWithMusics(){
        header();

        $.ajax({
            url: "/showAllMusicOfCommunity/${community.id}",
            dataType: "json",
            async: false,
            method: "get",
            contentType: "text/html; charset/UTF-8; charset=windows-1251;",
            data: {

            },
            success: function(data){
                var element = document.getElementById("popupWin");
                while (element.firstChild) element.removeChild(element.firstChild);


                var form = document.createElement("form");
                form.action = "/musicToCommunity/${community.id}?${_csrf.parameterName}=${_csrf.token}";
                form.method = "post";
                form.enctype = "multipart/form-data";

                var input1 = document.createElement("input");
                input1.type = "file";
                input1.name = "file1";
                input1.style = "float:left";

                form.appendChild(input1);

                var input2 = document.createElement("input");
                input2.type = "submit";
                input2.value = "Upload";
                input2.style = "float:left";


                form.appendChild(input2);


                element.appendChild(form);


                $.each(data,function(k,v){
                    /*<p style="clear: left"/>
                    <p style="margin-top: 10px;">${mus.nameOfSong}</p>
                            <audio controls style="margin-top: 10px; width: 100%;">
                            <source src="${mus.urlOfSong}" type="audio/mpeg" style="cursor: hand">
                            Your browser does not support the audio element.
                    </audio>*/

                    var pElem = document.createElement("p");
                    pElem.style = "clear:left;";

                    var pNameSong = document.createElement("p");
                    pNameSong.style = "margin-top: 10px; text-align:left; margin-left:25%;";
                    pNameSong.innerHTML = v.nameOfSong;

                    var audio = document.createElement("audio");
                    audio.style = "margin-top: 10px; width: 50%;";
                    audio.controls = "true";

                    var source = document.createElement("source");
                    source.src = v.urlOfSong;
                    source.type = "audio/mpeg";

                    audio.appendChild(source);
                    element.appendChild(pElem);
                    element.appendChild(pNameSong);
                    element.appendChild(audio);
                })
            }
        })
    }
</script>

</body>
</html>
