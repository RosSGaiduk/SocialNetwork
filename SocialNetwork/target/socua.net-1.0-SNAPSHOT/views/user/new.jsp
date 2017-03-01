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
<html lang = "en" ng-app = "app">
<head>
    <%--Всі лінки підключені в template.jsp--%>
    <!--AngularJs-->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.5/angular.min.js"></script>
    <!--Скріпт, у якому міститься контроллер з перевіркою сили паролю-->
    <script type="text/javascript" src="/resources/scripts/passwordStrength.js"></script>
</head>
<body>
<div class="forms" style="margin-top: 150px; margin-left: 5%;" ng-controller = "passwordCtrl">
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
        <form:password path="password" ng-model = 'password' ng-keyup = 'checkStrength()' cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%;" /><br>
        <h3 style="margin-left: 40%; color: {{color}};">{{passwordStrength}}</h3>

        <form:label path="confirmPassword"><h3 style="margin-left: 40%;">Confirm password:</h3></form:label>
        <font style="color: red"><form:errors path="confirmPassword" cssStyle="margin-left: 40%"/></font>
        <form:password path="confirmPassword" ng-model = 'confirmPassword' cssStyle="font-size: 18px;  border-radius: 8px;
         background: #F6F6f6; padding: 6px 0 4px 10px; margin-left: 40%;" /><br>
        <p style="margin-left: 40%"><form:button style="width:50px; height: 30px;border-radius:20%;">OK</form:button></p>
    </form:form>
</div>
</body>
</html>
