<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 08.10.2016
  Time: 23:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<html>
<head>
    <title>Soc.Ua</title>
    <meta charset="UTF-8">
    <link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css'>
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Open+Sans'>
    <link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.css'>
    <link rel="stylesheet" href="/resources/css/formsStyle.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="/resources/css/style.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="/resources/css/mainStyle.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="/resources/css/simplebox.css" type="text/css">
    <script type="text/javascript" src="/resources/scripts/simplebox_util.js"></script>
    <script type="text/javascript" src="/resources/scripts/simplebox.js"></script>
</head>
<body style="background-color: gainsboro">
<div><tiles:insertAttribute name="header"/></div>
<div><tiles:insertAttribute name="menu"/></div>
<div><tiles:insertAttribute name = "body"/></div>
<div><tiles:insertAttribute name = "footer"/></div>
</body>
</html>
