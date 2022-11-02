'use strict';

import {chatroomsListElement, userListElement, leftMsgHtml, rightMsgHtml, publicRoomListElement} from './components.js';

const webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chatapp");
// const webSocket = new WebSocket("wss://" + "alg23-ex7-chat.herokuapp.com" + "/chatapp");

let username = localStorage.getItem('username');

let currentChatroom = "";


/**
 * Entry point into chat room
 */
window.onload = function() {
    if (!username) window.location.href = "/";
    $(".chat-element").hide();
    let msg = {
        "type" : "initialize",
        "username" : username
    }
    webSocket.onopen = () => webSocket.send(JSON.stringify(msg));

    loadChatRoomList();

    loadPublicRoomList();

    webSocket.onclose = () => alert("WebSocket connection closed");
    webSocket.onmessage = (msg) => handleWebsocketMessage(msg);

    $(".chatroom").scrollTop($(".chatroom")[0].scrollHeight);


    $("#btn-msg").click(() => sendMessage($("#message").val()));
    $(".emoji-btn").click(function() {
        var oldText = $("#txt-message").val();
        $("#txt-message").val(oldText + $(this).text());
        $("#txt-message").focus();
    });
    $("#btn-send").click(() => sendMsg($("#txt-message").val()));

    $('.lst-rooms').on('click', function() {
        let $this = $(this); 
        $("#room-name").html($this.data('alias'));
    })

    $("#btn-logout").click(() => logout());

    $('#form-create').submit(function(e) {
        e.preventDefault();
        createChatRoom();
    })

    $(document).on('click', '.delete-btn', function(){
        let id = $(this).closest(".container").attr("id").slice(-1);
        $(this).closest(".container").remove();
        deleteMsg(id);
    });

    $(document).on('click', '.edit-btn', function(){
        let id = $(this).closest(".container").attr("id").slice(-1);
        editMsg(id, $(this));
    });
};

function sendMsg(msg) {
    if (msg != '') {
        let sendto = $('#send-to-list').find(":selected").val();
        let payload = {
            sender: username,
            chatroomName: currentChatroom,
            content: msg,
            receiver: sendto
        };
        $.post("/chatroom/sendMessage", payload, function(data) {});

        // check if msg have url
        let urlRegex = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/
        if (msg.match(urlRegex)) {
            let url = msg.match(urlRegex);
            msg = msg.replace(url[0], "<a href=\"" + url[0] + "\" class=\"link-primary\">" + url[0] + "</a>");
        }

        let msgHtml = convertMsgToHtml(-1, username, sendto, msg);
        $(".chat-messages").append(msgHtml);
        $("#txt-message").val('');
        let chat = $('.chatroom');
        chat.scrollTop(chat.prop("scrollHeight"));
    }
}

function loadMessages() {
    let payload = {
        username: username,
        chatroomName: currentChatroom,
    };
    $.get("/chatroom/getMessages", payload, function(data) {
        data = JSON.parse(data);
        console.log(data);
        $('#div-msg-list').empty();
        data.forEach(message => {
            let msgHtml = convertMsgToHtml(message.messageID, message.sender, message.receiver, message.content);
            $(msgHtml).appendTo($('#div-msg-list'));
        });
    });
}

function openChatroom(chatroomName) {
    let payload = {
        username: username,
        chatroomName: chatroomName,
    };
    $.post("/chatroom/connectToChatroom", payload, function(data) {
        data = JSON.parse(data);
        console.log(data);
        if (data.type === "public") {
            $('#div-invite-box').hide();
        }
        else $('#div-invite-box').show();

        currentChatroom = chatroomName;
        $("#room-name").text(chatroomName);

        $('#div-user-list').empty();
        data.users.forEach(user => {
            var userHtml = userListElement(user, username === user, data.admin === user);
            $(userHtml).appendTo($('#div-user-list'));
        })

        $(".btn-ban").click(function() {
            console.log($(this).parent().children('label').text());
        });

        $(".btn-report").click(function() {
            console.log($(this).parent().children('label').text());
        });


        loadMessages();


        $(".chat-element").show();
        loadChatRoomList();
    });
}

function loadChatRoomList() {
    let payload = {
        username: username
    }
    $.get("/chatroom/getChatroomList", payload, function(data) {
        let chatroomNames = JSON.parse(data);
        $('#div-chatrooms-list').empty();
        chatroomNames.forEach(item => {
            if (currentChatroom === "") {
                openChatroom(item);
                return;
            }
            var isOpen = item === currentChatroom;
            var html = chatroomsListElement(item, isOpen);
            $(html).appendTo($('#div-chatrooms-list'));
        });
        $(".btn-open-chatroom").click(function() {
            openChatroom($(this).parent().children('label').text());
        });
    });
}

function joinChatroom(chatroomName) {
    let payload = {
        username: username,
        chatroomName: chatroomName,
    };
    $.post("/chatroom/joinChatroom", payload, function(data) {
        data = JSON.parse(data);
        if (data.roomName === "") {
            $('#alert-public-room-full').show();
        }
        else {
            loadPublicRoomList();
            loadChatRoomList();
            $('#alert-public-room-full').hide();
        }
    });
}

function loadPublicRoomList() {
    let payload = {
        username: username
    }
    $.get("/chatroom/getPublicRoomList", payload, function(data) {
        let chatroomNames = JSON.parse(data);
        $('#div-public-room-list').empty();
        chatroomNames.forEach(item => {
            var html = publicRoomListElement(item);
            $(html).appendTo($('#div-public-room-list'));
        });
        $(".btn-join-chatroom").click(function() {
            console.log($(this).parent().children('label').text());
            joinChatroom($(this).parent().children('label').text());
        });
    });
}

function joinChatroom(chatroomName) {
    let payload = {
        username: username,
        chatroomName: chatroomName,
    };
    $.post("/chatroom/joinChatroom", payload, function(data) {
        data = JSON.parse(data);
        if (data.roomName === "") {
            $('#alert-public-room-full').show();
        }
        else {
            loadPublicRoomList();
            loadChatRoomList();
            $('#alert-public-room-full').hide();
        }
    });
}

function loadPublicRoomList() {
    let payload = {
        username: username
    }
    $.get("/chatroom/getPublicRoomList", payload, function(data) {
        let chatroomNames = JSON.parse(data);
        $('#div-public-room-list').empty();
        chatroomNames.forEach(item => {
            var html = publicRoomListElement(item);
            $(html).appendTo($('#div-public-room-list'));
        });
        $(".btn-join-chatroom").click(function() {
            console.log($(this).parent().children('label').text());
            joinChatroom($(this).parent().children('label').text());
        });
    });
}


function handleWebsocketMessage(message) {
    console.log(message.data);
}

function logout() {
    localStorage.removeItem('username');
    window.location.href = "/";
}

function createChatRoom() {
    let payload = {
        username: username,
        chatroomName: $('#create-roomname').val(),
        size: $('#create-roomsize').val(),
        type: $('input[name=roomTypeRadio]:checked', '#form-create').val()
    };

    $.post("/chatroom/createChatroom", payload, function(data) {
        if (data.roomName !== "") {
            $('#alert-room-create').hide();
            $('#create-roomname').val("");
            $('#create-roomsize').val("");
            loadChatRoomList();
        }
        else {
            $('#alert-room-create').show();
        }
    }, "json");
}

function editMsg(id, element) {
    if (editMsgID == 0) {
        let inputHtml = `<input type="text" id="in-edit"/>`;
        element.parent().parent().children().children('span').replaceWith(inputHtml);
        editMsgID = id;
    } else {
        let content = $("#in-edit").val();
        let spanHtml = `<span>${content}</span>`;
        let payload = {
            id: id,
            chatroomName: openChatroom,
            content: content
        }
        $.post("/chatroom/editMessage", payload);
        $("#in-edit").replaceWith(spanHtml);
        editMsgID = 0;
    }

}

function deleteMsg(id) {
    let payload = {
        id: id,
        chatroomName: openChatroom
    }
    $.post("/chatroom/deleteMessage", payload);
}

function convertMsgToHtml(msgId, sender, receiver, content) {
    let msg;
    receiver = receiver === username ? "You" : receiver;
    if (sender == username) {
        msg = rightMsgHtml(receiver, content, msgId);
    } else {
        msg = leftMsgHtml(sender, receiver, content, msgId);
    }
    return msg;
}