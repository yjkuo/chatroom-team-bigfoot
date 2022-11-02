'use strict';

import {chatroomsListElement, userListElement, leftMsgHtml, rightMsgHtml, publicRoomListElement, privateRoomListElement} from './components.js';

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
    loadInvitedToList();

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

    $('#btn-invite').click(() => inviteUser($('#inp-invite-user').val()));
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

        var msgHtml = `
                        <div class="container pb-3">
                            <div class="chat-message-right">
                                <div class="flex-shrink-1 rounded py-2 px-3 mr-3"> 
                                    <div class="d-flex flex-row">
                                        <div class="d-flex flex-column px-2">           
                                            <div class="fw-bold mb-1">You to ${sendto}</div>
                                            ${msg}
                                        </div>
                                        <div class="d-flex flex-column">
                                            <div><button class="btn btn-warning btn-sm w-100">Edit</button></div>
                                            <div><button class="btn btn-danger btn-sm w-100">Del</button></div>
                                        </div> 
                                    </div>
                                </div>                                                    
                            </div>
                            <div class="float-end">
                                <span style="font-size:10.0pt">Delivered</span>
                            </div>   
                        </div>
    `;
        $(".chat-messages").append(msgHtml);
        $("#txt-message").val(''); 
        let chat = $('.chatroom');
        chat.scrollTop(chat.prop("scrollHeight"));
    }
}

function inviteUser(user) {
    console.log("here");
    let payload = {
        sender: username,
        receiver: user,
        chatroomName: currentChatroom,
    };
    $.post("/chatroom/inviteToChatroom", payload, function(data) {
        console.log(data);
        data = JSON.parse(data);
        console.log(data);
        if (data) {
            $('#inp-invite-user').val("");
            $('#alert-invite-user').hide();
        }
        else {
            $('#alert-invite-user').show();
        }
    });
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

function joinPublicChatroom(chatroomName) {
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
        $(".btn-join-public-chatroom").click(function() {
            joinPublicChatroom($(this).parent().children('label').text());
        });
    });
}

function joinPrivateChatroom(chatroomName) {
    let payload = {
        username: username,
        chatroomName: chatroomName,
    };
    $.post("/chatroom/joinChatroom", payload, function(data) {
        data = JSON.parse(data);
        if (data.roomName === "") {
            $('#alert-invited-room-full').show();
        }
        else {
            loadInvitedToList();
            loadChatRoomList();
            $('#alert-invited-room-full').hide();
        }
    });
}

function loadInvitedToList() {
    let payload = {
        username: username
    }
    $.get("/chatroom/getInvitedRoomList", payload, function(data) {
        let chatroomNames = JSON.parse(data);
        $('#div-invited-to-list').empty();
        chatroomNames.forEach(item => {
            var html = privateRoomListElement(item);
            $(html).appendTo($('#div-invited-to-list'));
        });
        $(".btn-join-private-chatroom").click(function() {
            joinPrivateChatroom($(this).parent().children('label').text());
        });
    });
}

function handleWebsocketMessage(message) {
    console.log(message.data);
    if (message.data == "updateInvites") loadInvitedToList();
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

function convertMsgToHtml(msgId, sender, receiver, content) {
    let msg;
    receiver = receiver === username ? "You" : receiver;
    if (sender == username) {
        msg = rightMsgHtml(receiver, content);
    } else {
        msg = leftMsgHtml(sender, receiver, content);
    }
    return msg;
}