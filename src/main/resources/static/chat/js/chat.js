$(function () {
    var websocket = new WebSocket(encodeURI('ws://localhost:9999'));
    var userName = "";
    websocket.onopen = function () {
        login();
    };

    function login() {
        websocket.send("login:" + $("#userName").val());
    }

    websocket.onmessage = function (evt) {
        if (evt.data.indexOf("nameList:") != -1) {
            getNameList(evt.data);
        } else {
            console.log("收到消息");
            getText(evt.data);
        }
        ;
        console.log("Received Message: " + evt.data);
    };

    //更新聊天室人数
    function getNameList(nameListStr) {
        nameListStr = nameListStr.replace("nameList:", "");
        var nameList = nameListStr.split(";");
        $("#nameSize").text(nameList.length - 1);
        $("#userList").html("");
        console.log(nameList);
        $("#pagemenu",window.parent.document).html("");
        for (var i = 0; i < nameList.length - 1; i++) {
            $("#pagemenu",window.parent.document).append("<dd><a href='javascript:;' id='"+nameList[i]+"'><i class='fa fa-share-square' aria-hidden='true'></i> <span>"+nameList[i]+"</span></a></dd>");
        }
        ;
    }

    //发送消息
    $("#sendMsg").on("click",function(){
        websocket.send("message:" + $("#msgText").val());
    })
    function sendText() {

        $("#sayFcuk").html($("#sayFcuk").html() + '<div class="say-row"><div class="say-my"><span style="color:#00a1d6;">' + $("#myName").val() + '</span><br><div class="say-text">' + $("#out-text").val() + '</div></div><div style="clear:both;"></div></div>');
        $("#out-text").val("");
        document.getElementById("sayFcuk").scrollTop = document.getElementById("sayFcuk").scrollHeight;
    }

    //获得消息
    function getText(message) {
        var messageTeam = message.split(":");
        var messageName = messageTeam[0];
        var messageText = "";
        for (var i = 1; i < messageTeam.length; i++) {
            messageText += messageTeam[i];
        }
        $("#msgContent").html($("#msgContent").html() + '<div class="say-row"><div class="say-other"><span style="color:#00a1d6;">' + messageName + '</span><br><div class="say-text">' + messageText + '</div></div><div style="clear:both;"></div></div>');
        document.getElementById("msgContent").scrollTop = document.getElementById("msgContent").scrollHeight;
    }
})
