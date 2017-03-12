<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 06.03.2017
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<div style="float: left; width: 40%; height: auto; margin-top: 135px; background-color: white;">
    <form:form action="/newCommunity" method="get" cssStyle="margin-left: 80%;">
        <button class="buttonFileStyle" style="height: 30px;">Створити нову</button>
    </form:form>

    <c:forEach items="${communities}" var="c">
        <a href="/community/${c.id}">
        <img src="${c.urlImage}" width="120" height="120" style="float: left; border-radius: 20%; margin-top: 10px; margin-left: 10px;" alt="">
        <div style="float: left;">
            <h2 style="margin-left: 30px;"> ${c.title}</h2>
            <font style="margin-left: 30px;">${c.description}</font>
             <h3 style="margin-left: 30px;">Підписників: ${c.countSubscribers}</h3>
        </div></a>
        <div style="height: 1px; width: 100%; background-color: gainsboro; float: left; margin-top: 10px;"></div>
    </c:forEach>
</div>
</body>
</html>
