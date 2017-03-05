<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Rostyslav
  Date: 05.03.2017
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div style="float: left; width: 40%; height: auto; margin-top: 135px; background-color: white;">
    <div style="width: 100%; height: 30px;">
    </div>
    <c:forEach items="${messages}" var="m">
        <c:if test="${userAuth.id!=m.userToIdPattern}">
        <a href="/messagesWithUser/${m.userToIdPattern}" style="text-decoration: none;">
            <div class="messageFromChats">
                <div style="float:left;width: 100%;">
                    <font style="">${m.userToNameLastNamePattern}</font>
                </div>
                <div style="width: 50px; height: 50px; margin-left: 3%; margin-top: 10px;
                        background-image: url(${m.newestUserToUrlImagePattern}); background-size: cover;
                        float:left; border-radius: 50%;">
                </div>
                <div style="float: left; width: auto; height: auto; border-color: #333333; margin-top: 50px; margin-left: 0px;">
                    <p style="float:left; margin-left:0px; text-align: start;">
                        <font style="margin-left: 40px;">${m.shortText}</font>
                        <img src="${m.newestUserFromUrlImagePattern}" width="25" height="25" style="margin-left:3%;background-size:cover;float:left;border-radius:50%;
                    margin-right:10px; margin-top: -15px;">
                    <p style="clear: left; margin-top: 30px;"/>
                    ${m.dateOfMessage}
                    </p>
                </div>
                <div style="height: 1px; width: 100%; background-color: gainsboro; float: left; margin-top: 10px;"></div>
            </div>
        </a>
        </c:if>
        <c:if test="${userAuth.id!=m.userFromIdPattern}">
            <a href="/messagesWithUser/${m.userFromIdPattern}" style="text-decoration: none;">
                <div class="messageFromChats">
                    <div style="float:left; width: 100%;">
                        <font style="">${m.userFromNameLastNamePattern}</font>
                    </div>
                    <div style="width: 50px; height: 50px; margin-left: 3%; margin-top: 10px;
                            background-image: url(${m.newestUserFromUrlImagePattern}); background-size: cover;
                            float:left; border-radius: 50%;">
                    </div>
                    <div style="float: left; width: auto; height: auto; border-color: #333333; margin-top: 50px; margin-left: 0px;">
                        <p style="float:left; margin-left:0px; text-align: start;">
                            <font style="margin-left: 40px;">${m.shortText}</font>
                            <img src="${m.newestUserFromUrlImagePattern}" width="25" height="25" style="margin-left:3%;background-size:cover;float:left;border-radius:50%;
                    margin-right:10px; margin-top: -15px;">
                        <p style="clear: left; margin-top: 30px;"/>
                        ${m.dateOfMessage}
                        </p>
                    </div>
                    <div style="height: 1px; width: 100%; background-color: gainsboro; float: left; margin-top: 10px;"></div>
                </div>
            </a>
           </c:if>
        <c:if test="${userAuth.id==m.userFromIdPattern && userAuth.id==m.userToIdPattern}">
            <a href="/messagesWithUser/${m.userFromIdPattern}" style="text-decoration: none;">
                <div class="messageFromChats">
                    <div style="float:left; width: 100%;">
                        <font style="">${m.userFromNameLastNamePattern}</font>
                    </div>
                    <div style="width: 50px; height: 50px; margin-left: 3%; margin-top: 10px;
                            background-image: url(${m.newestUserFromUrlImagePattern}); background-size: cover;
                            float:left; border-radius: 50%;">
                    </div>
                    <div style="float: left; width: auto; height: auto; border-color: #333333; margin-top: 50px; margin-left: 0px;">
                    <p style="float:left; margin-left:0px; text-align: start;">
                        <font style="margin-left: 40px;">${m.shortText}</font>
                        <img src="${m.newestUserFromUrlImagePattern}" width="25" height="25" style="margin-left:3%;background-size:cover;float:left;border-radius:50%;
                    margin-right:10px; margin-top: -15px;">
                        <p style="clear: left; margin-top: 30px;"/>
                       ${m.dateOfMessage}
                    </p>
                    </div>
                    <div style="height: 1px; width: 100%; background-color: gainsboro; float: left; margin-top: 10px;"></div>
                </div>
            </a>
        </c:if>
    </c:forEach>
    <div style="float: left; margin-top: 20px; width: 100%; height: auto; background-color: white; border-top: 1px solid; border-color: gainsboro;"></div>
</div>
</body>
</html>
