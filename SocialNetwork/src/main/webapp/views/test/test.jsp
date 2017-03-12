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
    <%--Всі лінки підключені в template.jsp--%>
    <!--Ajax-->
    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery-mousewheel/3.1.11/jquery.mousewheel.min.js'></script>
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jScrollPane/2.0.14/jquery.jscrollpane.min.js'></script>
    <!--/Ajax-->
</head>

<body>
<font id = "minId" style="visibility: hidden;"></font>
<font id = "maxId" style="visibility: hidden;">10</font>
<font id = "childrenCount" style="visibility: hidden;"></font>


<select id = "selct" onchange="changedSelect()" style="margin-top: 70px; visibility: hidden;">
    <c:forEach items="${users}" var="u">
        <option>${u.id}</option>
    </c:forEach>
</select>


<div id = "messages" style="width: 50%; height: 80%; float: left; background-color: white; overflow: scroll; margin-top: 70px;">
    <%--<div style="width: 50%; height: auto; margin-left: 20%; margin-top: 20px;">
       <font size="3" style="text-align: center">Hello asdjasnoq ajsklas asdlkasd ojqwqw  oqwejoqwiej sjsdfnsdj oqieqwioeqw oifosifjsd qiweoqwe</font>
    </div>--%>
</div>

<textarea id = "textAr" style="height: 50px; width:50%; float: left" onkeyup="doAj()" placeholder="Введіть повідомлення: "></textarea>
<button onclick="sendMessage()"  class="sendButtonStyle">Send</button>

<%--
Якщо у функцію ajax ми хочемо передати значення з тегів p,span,h1,h2,...h6,font,...
тобто з тегів, які містять текст, то передавати потрібно в data document.getElementById("id").innerHtml.
Якщо ми використовуємо двні з тегів без тексту, тікі як select,div,inpuut,textarea..., тоді $('#id').val()
--%>

<script>
    function setSelect(){
        var element = document.getElementById('selct');
        element.value = ${idOfUser};
    }
    setSelect();
</script>

<script>
    function sendMessage(){
        if (document.getElementById("textAr").value != "") {
            $.ajax({
                url: "/sendMessage",
                data: (
                {
                    message: $("#textAr").val(),
                    userToId: $("#selct").val()
                }),
                async: false,
                /*dataType: "json",*/
                success: function (data) {
                    document.getElementById("textAr").value = "";
                    //$("#messages").children('div').eq(0).remove();
                    //$("#messages").children('p').eq(0).remove();
                    //changedSelect();
                    //alert(document.getElementById("minId").innerHTML);
                    //alert(document.getElementById("maxId").innerHTML);
                    update(true,false);
                }
            });
        }
    }
</script>

<script>
    var was1 = 0;
    function update(value,updateMinId){
        var val = $("#selct").val();
        $.ajax({
           url: "/update",
            dataType: "json",
            data: ({
                userToId: val,
                maxId: document.getElementById("maxId").innerHTML
                //count: parseInt(document.getElementById("messages").childElementCount/2-1)
            }),
            async: false,
            success: function(data){
                var heightValue = document.getElementById('messages').scrollHeight;
                $.each(data,function(k,v){
                    /*if (parseInt(was1)==0) {
                        document.getElementById("minId").innerHTML = v.id;
                    }*/
                    was1++;
                    var elem = document.createElement("div");
                    var elemData = document.createElement("p");
                    elemData.style = "font-size:12px;margin-top:20px;margin-left:10%;float:left;margin-botom:20px;"
                    elemData.innerHTML = v.data;
                    document.getElementById("messages").appendChild(elemData);

                    if (v.fromUser) {
                        elem.style = "background-color: #e4eaee; width:70%; height:auto;float:left; margin-top:10px;";
                    }
                    else elem.style = "background-color: #e4eaee; width:70%; height:auto;float:left; margin-top:10px; margin-left:20%";

                    var divNew = document.createElement("div");
                    divNew.style = "padding: 14px;border-left: 1px solid #cfdae1;float: left;color: black; width: 214px;";

                    var elemText = document.createElement("p");
                    elemText.innerHTML = v.text;
                    divNew.appendChild(elemText);

                    elem.appendChild(divNew);
                    var myDivMessages = document.getElementById('messages');
                    document.getElementById("maxId").innerHTML = v.maxId;
                    if (updateMinId) {
                        document.getElementById("minId").innerHTML = v.minId;
                    }

                    myDivMessages.appendChild(elem);
                    //якщо повідомлення надіслав даний користувач
                    if (value==true) {
                        myDivMessages.scrollTop = myDivMessages.scrollHeight;
                        //alert("true"+value);
                    } else {
                        //alert(""+(myDivMessages.scrollHeight-myDivMessages.scrollTop)); //590 або 577 в різних браузерах
                        //alert(""+(myDivMessages.scrollTop+" "+myDivMessages.scrollHeight));
                        //alert(myDivMessages.scrollHeight+" "+heightValue); //4385 4304
                        var heightMessage = myDivMessages.scrollHeight-heightValue;
                        if (parseInt(myDivMessages.scrollHeight-(myDivMessages.scrollTop+heightMessage))<600){
                            myDivMessages.scrollTop = myDivMessages.scrollHeight;
                        }
                        else {
                            alert("New message, scroll down to read it");
                        }
                    }
                    document.getElementById("childrenCount").innerHTML = parseInt(document.getElementById("messages").childElementCount/2);
                });
            }
        });
    }
    var id1 = setInterval("update(false,false)",100);
</script>


<script>
    function changedSelect(){
        var element = document.getElementById("messages");
        while(element.firstChild) element.removeChild(element.firstChild);

        var btn = document.createElement("button");
        btn.innerHTML = "Previous";
        btn.setAttribute("class","buttonPreviousMessagesStyle")
        btn.setAttribute("id","previousMessagesBtn");
        btn.style = "margin-left:20%;";
        $("#messages").append(btn);

        $("#previousMessagesBtn").click(function() {
            $.ajax({
                url: "/getPreviousMessages",
                dataType: "json",
                async: false,
                data: ({
                    userToId: $("#selct").val(),
                    minId: document.getElementById("minId").innerHTML
                }),
                success: function (data) {
                    var height = element.scrollHeight;
                    //alert("Height: "+height+" top: "+element.scrollTop);
                    /*var first=document.getElementById("records").childNodes[0];
                    document.getElementById("records").insertBefore(elem,first);*/
                    var scroll = document.getElementById("messages").scrollTop;
                    //alert(parseInt(scroll+200));
                    $.each(data, function (k, v) {
                        document.getElementById("minId").innerHTML = v.id;

                        var elem = document.createElement("div");
                        var elemData = document.createElement("p");
                        elemData.style = "font-size:12px;margin-top:20px;margin-left:10%;float:left;margin-botom:20px;"
                        elemData.innerHTML = v.data;
                        //document.getElementById("messages").appendChild(elemData);
                        var first = document.getElementById("messages").childNodes[1];
                        document.getElementById("messages").insertBefore(elemData, first);


                        if (v.fromUser) {
                            elem.style = "background-color: #e4eaee; width:70%; height:auto;float:left; margin-top:10px;";
                        }
                        else elem.style = "background-color: #e4eaee; width:70%; height:auto;float:left; margin-top:10px; margin-left:20%";

                        var divNew = document.createElement("div");
                        divNew.style = "padding: 14px;border-left: 1px solid #cfdae1;float: left;color: black; width: 214px;";

                        var elemText = document.createElement("p");
                        elemText.innerHTML = v.text;
                        divNew.appendChild(elemText);

                        elem.appendChild(divNew);
                        var myDivMessages = document.getElementById('messages');


                        myDivMessages.appendChild(elem);

                        var second = document.getElementById("messages").childNodes[2];
                        document.getElementById("messages").insertBefore(elem, second);

                        var heightUpdated = myDivMessages.scrollHeight;
                        myDivMessages.scrollTop = heightUpdated-height;


                        //myDivMessages.scrollTop = scroll+5450;

                        document.getElementById("childrenCount").innerHTML = parseInt(document.getElementById("messages").childElementCount / 2);

                    })
                }
            })
        })
        //document.getElementById("maxId").innerHTML = "10";
        //document.getElementById("minId").innerHTML = "10";
        document.getElementById("childrenCount").innerHTML = parseInt(document.getElementById("messages").childElementCount/2);
        update(true,true);
    }
    changedSelect();
</script>
</body>
</html>
