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
    <script src="/resources/scripts/main.js"></script>
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
        <div style="width: 95%; height: auto; float: left; margin-left: 20px; margin-top: 20px;" id = "${rec.id} div">
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
            <button onclick="deleteRecord(${rec.id})" style="margin-top: 10px;">Delete</button>
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
         <h4 style="margin: 20px 0px 0px 20px">Підписники ${community.countSubscribers}</h4>
        <div style="float: left; width: 100%; height: 120px; margin-top: 50px;">
            <c:forEach items="${subscribers}" var="s">
                <div class="imageSubscriberInCommunity" style="background-image: url(${s.newestImageSrc});">
                    <div class="underImageSubscriberInCommunityInfo">
                        ${s.firstName}
                    </div>
                </div>
                <%--<img src="${s.newestImageSrc}" width="70" height="70" style="background-size: cover; border-radius: 50%;">--%>
            </c:forEach>
        </div>
        <!--Між блоками завжди така дів-->
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Фотоальбоми-->
        <div style="float: left; width: 100%; height: 120px; margin-top: 50px;"></div>
        <!--Між блоками завжди така дів-->
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Відеозаписи-->
        <div style="float: left; width: 100%; height: 120px; margin-top: 50px;"></div>
        <!--Між блоками завжди така дів-->
        <div style="float: left; width: 100%; height: 20px; background-color: gainsboro; margin-top: 50px;"></div>
        <!--Аудіозаписи-->
        <div style="float: left; width: 100%; height: 120px; margin-top: 50px;"></div>
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

                if (data=='subscribed') {
                    $("#subscribeBtn").text("Відписатись");
                }
                else {
                    $("#subscribeBtn").text("Підписатись");
                }
            }
        })
    }

</script>

</body>
</html>
