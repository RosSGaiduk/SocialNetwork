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
    <meta charset="UTF-8">
    <link rel='stylesheet prefetch' href='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.css'>
    <link rel="stylesheet" href="/resources/css/formsStyle.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="/resources/css/style.css" media="screen" type="text/css" />
</head>

<body>
<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script><script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
<font id = "pId">asd</font>

<select id = "selct" onchange="changedSelect()">
    <c:forEach items="${users}" var="u">
        <option>${u.id}</option>
    </c:forEach>
</select>


<div id = "messages" style="width: 50%; height: 80%; float: left; background-color: white; overflow: scroll;">
    <%--<div style="width: 50%; height: auto; margin-left: 20%; margin-top: 20px;">
       <font size="3" style="text-align: center">Hello asdjasnoq ajsklas asdlkasd ojqwqw  oqwejoqwiej sjsdfnsdj oqieqwioeqw oifosifjsd qiweoqwe</font>
    </div>--%>
</div>

<textarea id = "textAr" style="height: 50px; width:50%; float: left" onkeyup="doAj()" placeholder="Введіть повідомлення: "></textarea>
<button onclick="sendMessage()" style="float: left; margin-left: 45%;">Send</button>

<%--
Якщо у функцію ajax ми хочемо передати значення з тегів p,span,h1,h2,...h6,font,...
тобто з тегів, які містять текст, то передавати потрібно в data document.getElementById("id").innerHtml.
Якщо ми використовуємо двні з тегів без тексту, тікі як select,div,inpuut,textarea..., тоді $('#id').val()
--%>

<%--<script>
    function doAj(){
        $.ajax({
            url: "/testGo",
            data:({message:$("#textAr").val()}),
            async:false,
            success: function(data){
                alert(data);
            }
        });
    }
    //var id = setInterval("doAj()",1000);
</script>--%>


<script>
    function sendMessage(){
        $.ajax({
           url: "/sendMessage",
            data:(
            {
                message:$("#textAr").val(),
                userToId: $("#selct").val()
            }),
            async:false,
            /*dataType: "json",*/
            success: function(data){
                document.getElementById("textAr").value = "";
                update();
            }
        });
    }
</script>


<script>
    function update(){
        var val = $("#selct").val();
        //alert(document.getElementById("messages").childElementCount);
        $.ajax({
           url: "/update",
            dataType: "json",
            data: ({
                userToId: val,
                count: document.getElementById("messages").childElementCount/2
            }),
            async: false,
            success: function(data){
                $.each(data,function(k,v){

                    var elem = document.createElement("div");
                    var elemData = document.createElement("p");
                    elemData.style = "font-size:12px;margin-top:20px;margin-left:10%;float:left;margin-botom:20px;"
                    elemData.innerHTML = v.data;
                    document.getElementById("messages").appendChild(elemData);


                    if (v.fromUser)
                        elem.style = "background-color: #e4eaee; width:70%; height:auto;float:left; margin-top:10px;";
                    else elem.style = "background-color: #e4eaee; width:70%; height:auto;float:left; margin-top:10px; margin-left:20%";


                    document.getElementById("messages").appendChild(elem);
                    var divNew = document.createElement("div");
                    divNew.style = "padding: 14px;border-left: 1px solid #cfdae1;float: left;color: black; width: 214px;";

                    var elemText = document.createElement("p");
                    elemText.innerHTML = v.text;
                    divNew.appendChild(elemText);

                    elem.appendChild(divNew);
                    var myDivMessages = document.getElementById('messages');
                    myDivMessages.appendChild(elem);
                    myDivMessages.scrollTop = myDivMessages.scrollHeight;
                });
            }
        });
    }
    var id1 = setInterval("update()",1000);
</script>

<script>
    function changedSelect(){
        var element = document.getElementById("messages");
        while(element.firstChild) element.removeChild(element.firstChild);
    }
</script>

</body>
</html>
