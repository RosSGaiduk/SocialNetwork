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

function openRecord(recordType,recordText,recordUrl,recordName){
    //alert("clicked");
    header();
    //alert("TYPE: "+recordType+"\nTEXT: "+recordText+"\nURL: "+recordUrl+"\nNAME: "+recordName);

    var main = document.getElementById("popupWin");
    while (main.firstChild) (main.removeChild(main.firstChild));

    switch (recordType){
        case "TEXT": {
            var element = document.createElement("p");
            element.style = "margin-top: 25%; width: 60%; margin-left:20%;";
            element.innerText = recordText;
            document.getElementById("popupWin").appendChild(element);
        } break;

        case "AUDIO": {
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
            var mainDiv = document.createElement("div");
            mainDiv.style = "width: 75%; height: 64%; float: left; margin-top: 20px; margin-left: 30px;" +
                "background-image: url("+recordUrl+");"+
                "background-repeat: no-repeat; background-size: cover; cursor: hand;";

            var elementText = document.createElement("p");
            elementText.style = "float:left; margin-top:10px; margin-left: 30px; text-align: left; clear:left;";
            elementText.innerText = recordText;

            main.appendChild(mainDiv);
            main.appendChild(elementText);
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


