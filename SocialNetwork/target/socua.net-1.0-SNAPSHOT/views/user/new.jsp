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
    <title>User</title>
    <link href="<c:url value="/resources/css/style.css"/>" type="text/css" rel="stylesheet">
    <link href="<c:url value="/resources/css/formsStyle1.css"/>" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <meta http-equiv="Content-Type" content="text/html;" charset="UTF-8">
    <%--<script src="/resources/scripts/autoScrollDown.js"></script>--%>
</head>
<body>
<div class="forms" style="margin-top: 150px; margin-left: 5%;">
    <form:form action="/createUser" method="post" modelAttribute="newUser">

        <form:label path="firstName"><h3 style="margin-left: 40%;">First name: </h3></form:label>

        <font style="color: red"><form:errors path="firstName" cssStyle="margin-left: 40%"/></font>
        <form:input path="firstName" cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>

        <form:label path="lastName"><h3 style="margin-left: 40%;">Last name: </h3></form:label>

        <font style="color: red"><form:errors path="lastName" cssStyle="margin-left: 40%"/></font>
        <form:input path="lastName" cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>


        <label><h3 style="margin-left: 40%;">Date: </h3></label>
        <font style="color: red"><form:errors path="birthDate" cssStyle="margin-left: 40%"/></font>
        <input name="birthDateUser" type="date" style="margin-left:40%;
        height: 35px; font-size: 18px;border-radius: 3px;
        padding: 0 3px;"/>

        <form:label path="email"><h3 style="margin-left: 40%;">Email: </h3></form:label>
        <font style="color: red"><form:errors path="email" cssStyle="margin-left: 40%"/></font>
        <form:input path="email" cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>


        <form:label path="password"><h3 style="margin-left: 40%;">Password: </h3></form:label>
        <font style="color: red"><form:errors path="password" cssStyle="margin-left: 40%"/></font>
        <form:password id = "userPassword" path="password" onkeyup="doAjax()" cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%;" />
        <br>

        <label id="levelPassword"></label>
        <form:label path="confirmPassword"><h3 style="margin-left: 40%;">Confirm password: </h3></form:label>


        <form:password path="confirmPassword" cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%; " /><br>

        <p style="margin-left: 40%"><form:button style="width:50px; height: 30px;border-radius:20%;">OK</form:button></p>

    </form:form>
</div>


<script>
    function doAjax(){
        $.ajax({
            url: '/levelPassword',
            data: ({userPassword: $('#userPassword').val()}),
            async: false,
            success: function(data) {
                var attributes = data.split(" ");
                $('#levelPassword').css("color", attributes[1]);
                $('#levelPassword').css("margin-left", "40%");
                $('#levelPassword').html(attributes[0]);

            }
        });
    }
</script>


</body>
</html>
