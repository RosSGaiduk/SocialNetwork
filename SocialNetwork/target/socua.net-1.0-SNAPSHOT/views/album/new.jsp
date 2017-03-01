<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 12.01.2017
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--Всі лінки підключені в template.jsp--%>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.5/angular.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html;" charset="UTF-8">
</head>
<body>
    <div class="forms" style="margin-top: 150px; margin-left: 5%;">
        <form:form method="post" action="/createAlbum" modelAttribute="album">
            <form:label path="name"><h3 style="margin-left: 40%;">Name: </h3></form:label>
            <form:input path="name" cssStyle="font-size: 18px;  border-radius: 8px;
             background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>
            <p style="margin-left: 40%"><form:button style="width:50px; height: 30px;border-radius:20%;">OK</form:button></p>
        </form:form>
    </div>
</body>
</html>
