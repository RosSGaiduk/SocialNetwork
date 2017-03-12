<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 06.03.2017
  Time: 18:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<div class="forms" style="margin-top: 150px; margin-left: 5%;">
        <form:form action="/createCommunity" method="post" modelAttribute="community">
            <form:label path="title"><h3 style="margin-left: 40%;">Title</h3></form:label>
            <form:input path="title" cssStyle="font-size: 18px;  border-radius: 8px;
             background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>
            <form:label path="title"><h3 style="margin-left: 40%;">Description</h3></form:label>
            <form:input path="description" cssStyle="font-size: 18px;  border-radius: 8px;
             background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>
            <p style="margin-left: 40%; margin-top: 10px;"><form:button style="width:50px; height: 30px;border-radius:20%;">OK</form:button></p>
        </form:form>
    </div>
</body>
</html>
