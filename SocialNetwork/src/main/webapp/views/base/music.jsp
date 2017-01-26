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
    <link rel="stylesheet" href="/resources/css/mainStyle.css" media="screen" type="text/css" />
</head>
<body>
<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>

<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 80px; background-color: white">
<form:form action="/musicProcess.htm?${_csrf.parameterName}=${_csrf.token}" method="post"
           enctype="multipart/form-data" cssStyle="float: left;">
    <input type="file" name="file1"  style="float: left;"/>
    <input type="submit" value="Upload" style="float: left;">
</form:form>

    <c:forEach items="${musicAll}" var="mus">
        <p style="clear: left"/>
        <p style="margin-top: 10px; margin-left: 20%;">${mus.nameOfSong}</p>
        <audio controls style="margin-top: 10px; margin-left: 20%;">
            <source src="${mus.urlOfSong}" type="audio/mpeg" style="cursor: hand">
            Your browser does not support the audio element.
        </audio>
        <button id = "button ${mus.id}"onclick="addMusicToUser(${mus.id})">Add</button>
    </c:forEach>
    </div>


<script>
    function addMusicToUser(id){
        $.ajax({
           url:"/addMusicToUser",
            data: ({idMusic: id}),
            async: false,
            success: function(data){
                document.getElementById("button "+id).style.visibility = "hidden";
            }
        });
    }
</script>


</body>
</html>
