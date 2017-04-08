<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 05.04.2017
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 50px; cursor: hand;">
<h1>Title of session: <font color="#ff1493">${mySession.title}</font>  and changed <font color="#ff1493">${mySession.countChanged}</font> times</h1>
<form:form method="post" action="/changeSessionValue"  modelAttribute="mySessionModel" cssStyle="margin-left: 20%; margin-top: 20px;">
   <%-- <input type="text" name="changeValueOfSession" placeholder="New name of session: "/>
    <button type="submit">OK</button>--%>
    <form:input path="title"/>
    <form:button>OK</form:button>
</form:form>
</div>
</body>
</html>
