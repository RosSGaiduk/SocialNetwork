/**
 * Created by Rostyslav on 09.03.2017.
 */


function banScroll(val){
    if (val) {
        var $window = $(window), previousScrollTop = 0, scrollLock = true;
        $window.scroll(function (event) {
            if (scrollLock) {
                $window.scrollTop(0);
            }
            previousScrollTop = $window.scrollTop();

        });
    }
}

function deleteRecord(recId){
    $.ajax({
        url: "/deleteRecord",
        data:({
            idRecord: recId
        }),
        async: false,
        success: function(data){
            //скасовуєм відкриття блоку поверх сторінки(бо якщо цього не зробити, то при натисканні кнопки delete
            //яка є всередині блоку, при кліку на який відкриється record поверх вікна, викличеться цей onclick
            //і матимемо відкриття запису останній раз перед видаленням)
            $("#"+recId+"_div").prop('onclick',null).off('click');
            var mainDiv = document.getElementById("records");
            var deletedDiv = document.getElementById(recId+'_div');
            mainDiv.removeChild(deletedDiv);
        }
    })
}

function updateTime(val,userId){
    timeUpdated = (new Date).getTime();
    $.ajax({
        url: "/updateOnlineUser/"+userId,
        method: "get",
        async: false,
        data:({
            time: currentTime,
            setOnline: val,
        }),
        success: function(data){
            $("#onlineCheck").html(data);
        }
    })
}
//залишити лайк під будь чим
function leaveLike(typeOfEntity,idOfEntity){
    $.ajax({
        url: "/leaveLike",
        async: false,
        data: {
            type: typeOfEntity,
            id: idOfEntity
        },
        success: function (data) {
            loadCountLikes(typeOfEntity,idOfEntity);
            loadUsersThatLeftLike(idOfEntity);
        }
    })
}
//залишити коментар під фоткою
function leaveComment(imageId){
    $.ajax({
        url: "/leaveComment/"+imageId,
        async: false,
        dataType: "json",
        data: {
            text: $("#textAr").val()
        },
        success: function (data) {
            var aHref = document.createElement("a");
            aHref.href = "/user/"+data.id;
            var mainDiv = document.createElement("div");
            mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ data.imageSrc+"); border-radius:50%;";
            aHref.appendChild(mainDiv);
            var textP = document.createElement("p");
            textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
            textP.innerHTML = data.text;
            var clearP = document.createElement("p");
            clearP.style = "clear:left;"

            var first=document.getElementById("comments").childNodes[0];
            document.getElementById("comments").insertBefore(aHref,first);

            var second=document.getElementById("comments").childNodes[1];
            document.getElementById("comments").insertBefore(textP,second);

            var third=document.getElementById("comments").childNodes[2];
            document.getElementById("comments").insertBefore(clearP,third);

            //document.getElementById("comments").appendChild(mainDiv);
            //document.getElementById("comments").appendChild(textP);
            //document.getElementById("comments").appendChild(clearP);

            $("#textAr").val("");
        }
    })
}

function openRecord(recordType,recordText,recordUrl,recordName){
    switch (recordType){
        case "TEXT": {
            header();
            var main = document.getElementById("popupWin");
            while (main.firstChild) (main.removeChild(main.firstChild));
            var element = document.createElement("p");
            element.style = "margin-top: 25%; width: 60%; margin-left:20%;";
            element.innerText = recordText;
            document.getElementById("popupWin").appendChild(element);
        } break;

        case "AUDIO": {
            header();
            var main = document.getElementById("popupWin");
            while (main.firstChild) (main.removeChild(main.firstChild));
            var divMain = document.createElement("div");
            divMain.style = "float:left; margin-left:30px;margin-top:25%;width:80%";

            var pNameSong = document.createElement("p");
            pNameSong.style = "float:left; margin-top: 10px; text-align:left; margin-left:30px;";
            pNameSong.innerHTML = recordName;

            var audio = document.createElement("audio");
            audio.style = "float:left; width: 50%; margin-left:30px; margin-top:10px;";
            audio.controls = "true";

            var source = document.createElement("source");
            source.src = recordUrl;
            source.type = "audio/mpeg";

            var pElem = document.createElement("p");
            pElem.style = "float:left;margin-left:30px; margin-top:20px; text-align:left; clear:left;";
            pElem.innerHTML = recordText;

            audio.appendChild(source);
            divMain.appendChild(pNameSong);
            divMain.appendChild(audio);
            divMain.appendChild(pElem)
            main.appendChild(divMain);
        } break;
        case "IMAGE": {
            header();
            var main = document.getElementById("popupWin");
            while (main.firstChild) (main.removeChild(main.firstChild));
            var mainDiv = document.createElement("div");
            mainDiv.style = "width: 75%; height: 64%; float: left; margin-top: 20px; margin-left: 30px;" +
                "background-image: url("+recordUrl+");"+
                "background-repeat: no-repeat; background-size: contain; cursor: hand;";

            var elementText = document.createElement("p");
            elementText.style = "float:left; margin-top:10px; margin-left: 30px; text-align: left; clear:left;";
            elementText.innerText = recordText;

            main.appendChild(mainDiv);
            main.appendChild(elementText);
        } break;

        case "VIDEO":{
        } break;
    }
}

function fixString(text){
    var newStr = "";
    for (i = 0; i < text.length; i++){
        switch (text[i]){
            case "#":{
                newStr+="%23";
            } break;
            case "%":{
                newStr+="%25";
            } break;
            case ";":{
                newStr+="%3B";
            } break;
            case "?":{
                newStr+="%3F";
            } break;
            case "'":{
                newStr+="`";
            } break;
            default:{
                newStr+=text[i];
            } break;
        }
    }
    return newStr;
}

function leaveComment(imageId){
    $.ajax({
        url: "/leaveComment/"+imageId,
        async: false,
        dataType: "json",
        data: {
            text: $("#textAr").val()
        },
        success: function (data) {
            var aHref = document.createElement("a");
            aHref.href = "/user/"+data.id;
            var mainDiv = document.createElement("div");
            mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ data.imageSrc+"); border-radius:50%;";
            aHref.appendChild(mainDiv);
            var textP = document.createElement("p");
            textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
            textP.innerHTML = data.text;
            var clearP = document.createElement("p");
            clearP.style = "clear:left;"

            var first=document.getElementById("comments").childNodes[0];
            document.getElementById("comments").insertBefore(aHref,first);

            var second=document.getElementById("comments").childNodes[1];
            document.getElementById("comments").insertBefore(textP,second);

            var third=document.getElementById("comments").childNodes[2];
            document.getElementById("comments").insertBefore(clearP,third);

            //document.getElementById("comments").appendChild(mainDiv);
            //document.getElementById("comments").appendChild(textP);
            //document.getElementById("comments").appendChild(clearP);

            $("#textAr").val("");
        }
    })
}


function leaveCommentUnderVideo(videoId){
    $.ajax({
        url: "/leaveCommentUnderVideo/"+videoId,
        async: false,
        dataType: "json",
        data: {
            text: $("#videoTextArea").val()
        },
        success: function (data) {
            var aHref = document.createElement("a");
            aHref.href = "/user/"+data.id;
            var mainDiv = document.createElement("div");
            mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ data.imageSrc+"); border-radius:50%;";
            aHref.appendChild(mainDiv);
            var textP = document.createElement("p");
            textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
            textP.innerHTML = data.text;
            var clearP = document.createElement("p");
            clearP.style = "clear:left;"

            var first=document.getElementById("comments").childNodes[0];
            document.getElementById("comments").insertBefore(aHref,first);

            var second=document.getElementById("comments").childNodes[1];
            document.getElementById("comments").insertBefore(textP,second);

            var third=document.getElementById("comments").childNodes[2];
            document.getElementById("comments").insertBefore(clearP,third);


            $("#videoTextArea").val("");
        }
    })
}


function updateCommentsGo(imageId){
    $.ajax({
        url: "/loadCommentsUnderImage",
        async: false,
        method: "get",
        dataType: "json",
        data: {
            id: imageId
        },
        success: function(data){
            $.each(data,function(k,v){
                var aHref = document.createElement("a");
                aHref.href = "/user/"+v.id;
                var mainDiv = document.createElement("div");
                mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ v.userUrlImage+"); border-radius:50%;";
                aHref.appendChild(mainDiv);
                var divP = document.createElement("div");
                divP.style = "float:left; width: 60%;";
                var textP = document.createElement("p");
                textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
                textP.innerHTML = v.text;
                var clearP = document.createElement("p");
                clearP.style = "clear:left;";
                divP.appendChild(textP);

                document.getElementById("comments").appendChild(aHref);
                document.getElementById("comments").appendChild(divP);
                document.getElementById("comments").appendChild(clearP);
            })
        }
    })
}

function loadCountLikes(typeEntity,idEntity){
    //alert(idEntity);
    $.ajax({
        url: "/getCountLikesOfEntity/"+idEntity,
        method: "get",
        dataType: "json",
        async: false,
        data:{
            type: typeEntity
        },
        success: function (data) {
            $("#countLikesUnderPhoto").html(data.countLikes);
            if (!data.liked){
                var image = document.getElementById("likeImg");
                image.src = "/resources/img/icons/likeClear.png";
            } else {
                var image = document.getElementById("likeImg");
                image.src = "/resources/img/icons/like.png";
            }
        }
    })
}


function updateCommentsOfVideo(videoId){
    $.ajax({
        url: "/loadCommentsUnderVideo",
        async: false,
        method: "get",
        dataType: "json",
        data: {
            id: videoId
        },
        success: function(data){
            $.each(data,function(k,v){
                var aHref = document.createElement("a");
                aHref.href = "/user/"+v.id;
                var mainDiv = document.createElement("div");
                mainDiv.style = "float:left; margin-left:0px; margin-top:20px; width:50px; height: 50px; background-size: cover; background-image: url("+ v.userUrlImage+"); border-radius:50%;";
                aHref.appendChild(mainDiv);
                var divP = document.createElement("div");
                divP.style = "float:left; width: 60%;";
                var textP = document.createElement("p");
                textP.style = "float:left; text-align: left; margin-left:10px; margin-top:20px;"
                textP.innerHTML = v.text;
                var clearP = document.createElement("p");
                clearP.style = "clear:left;";
                divP.appendChild(textP);

                document.getElementById("comments").appendChild(aHref);
                document.getElementById("comments").appendChild(divP);
                document.getElementById("comments").appendChild(clearP);
            })
        }
    })
}


function loadUsersThatLeftLikeWithLimit(imageId){
    $.ajax({
        url: "/getUsersThatLikedImageWithLimit/"+imageId,
        dataType: "json",
        method: "get",
        data: ({
            limit: "6"
        }),
        async: false,
        success: function (data){
            $.each(data,function(k,v){
                //alert(v.id);
                $("#windowLikeId").append("" +
                        /*"<a id = '"+ v.id+" a' href='/user/"+ v.id+"'>" +*/
                    "<div style='float:left; height: 60px; width: 50px; margin-left: 20px;' onclick='cancelOpeningBigWindowAndGoToUser("+ v.id+")'>" +
                    "<img src = '"+ v.urlImage+"' width = '50' height='50' style='float: left;'>" +
                    "<font style='float: left; clear:left; font-size: 10px;'>"+ v.details +"</font>" +
                    "</div>");
            })
        }
    })
}

function getDetailsOfPhoto(idPhoto){
    var obj = null;
    $.ajax({
        url: "/getWidth_HeightAndRatioOfPhoto/"+idPhoto,
        method: "get",
        async: false,
        data: ({

        }),
        dataType: "json",
        success: function(data){
            obj = data;
        }
    })
    return obj;
}

function addVideoToUser(idVideo){
    //alert(idVideo);
    $.ajax({
        url: "/addVideoToUser/"+idVideo,
        async: false,
        method: "get",
        data:{
        },
        success: function(data){
            $("#addingVideoButton").css("background-color", "orangered");
            $("#addingVideoButton").css("border-color", "orangered");
            $("#addingVideoButton").html("Delete");
            $("#addingVideoButton").attr("id","removingVideoButton");
            $("#removingVideoButton").prop('onclick',null).off('click');
            $("#removingVideoButton").click(function(){
                deleteVideoFromUserPage(idVideo);
            })
        }
    })
}

function deleteVideoFromUserPage(idVideo){
    $.ajax({
        url: "/deleteVideoFromUserPage/"+idVideo,
        method: "get",
        async: false,
        success: function(data){
            $("#removingVideoButton").css("background-color", "#6ea0ff");
            $("#removingVideoButton").css("border-color", "#6ea0ff");
            $("#removingVideoButton").html("Add");
            $("#removingVideoButton").attr("id","addingVideoButton");
            $("#addingVideoButton").prop('onclick',null).off('click');
            $("#addingVideoButton").click(function(){
                addVideoToUser(idVideo);
            })
        }
    })
}

function checkIfVideoBelongsToAuthUser(idVideo){
    var result = false;
    $.ajax({
        url: "/checkingIfVideoBelongsToAuthUser/"+idVideo,
        method: "get",
        async: false,
        success: function(data){
            if (data=="true"){
                result = true;
            } else {
                result = false;
            }
        }
    })
    return result;
}

function loadCountLikesUnderVideo(idVideo){
    $.ajax({
        url: "/loadCountLikesUnderVideo/"+idVideo,
        async: false,
        method: "get",
        success: function (data) {
            $("#countLikesUnderVideo").html(data);
        }
    })
}
function checkIfUserLikedVideo(idVideo){
    $.ajax({
        url: "/checkIfUserLikedVideo/" + idVideo,
        async: false,
        method: "get",
        success: function (data) {
            if (data=="true"){
                $("#likeImgId").attr("src","/resources/img/icons/like.png");
            } else {
                $("#likeImgId").attr("src","/resources/img/icons/likeClear.png");
            }
        }
    })
}
function leaveLikeUnderVideo(idVideo){
    $.ajax({
        url: "/leaveLikeUnderVideo/" + idVideo,
        async: false,
        method: "get",
        dataType: "json",
        success: function (data) {
            if (data.liked){
                $("#likeImgId").attr("src","/resources/img/icons/like.png");
            } else {
                $("#likeImgId").attr("src","/resources/img/icons/likeClear.png");
            }
            $("#countLikesUnderVideo").html(data.countLikes);
        }
    })
}

function leaveLikeUnderRecord(idRecord){
    $("#"+idRecord+"_div").prop('onclick',null).off('click');
    $.ajax({
        url: "/leaveLikeUnderRecord/"+idRecord,
        method: "get",
        async: false,
        dataType: "json",
        success: function(data){
            if (data.liked){
                $("#likeIconUnderRecordImg_"+idRecord).attr("src","/resources/img/icons/like.png");
            } else {
                $("#likeIconUnderRecordImg_"+idRecord).attr("src","/resources/img/icons/likeClear.png");
            }
            $("#countLikesUnderRecord_"+idRecord).html(data.countLikes);
        }
    })
}

function backOpenRecordFunction(idRecord,type,text,url){
    $("#"+idRecord+"_div").click(function(){
        openRecord(type,text,url);
    })
}