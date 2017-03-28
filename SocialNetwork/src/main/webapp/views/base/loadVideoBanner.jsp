<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 28.03.2017
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div style="width: 60%; height: auto; margin-left: 20px; max-width: 60%; float: left; margin-top: 80px; background-color: white">
<form:form action="/videoLoadBanner/${idVideo}/?${_csrf.parameterName}=${_csrf.token}" method="post"
           enctype="multipart/form-data" cssStyle="float: left;">
    <input type="file" name="file1"  style="float: left;"/>
    <button type="submit" value="Upload" style="float: left;">OK</button>
</form:form>
    </div>
</body>
</html>
