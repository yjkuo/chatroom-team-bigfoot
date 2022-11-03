'use strict';

import {chatroomsListElement, userListElement, leftMsgHtml, rightMsgHtml, publicRoomListElement, privateRoomListElement} from './components.js';

const webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chatapp");
// const webSocket = new WebSocket("wss://" + "chatapp-final-team-bigfoot.herokuapp.com" + "/chatapp");

let username = localStorage.getItem('username');

let currentChatroom = "";
let editMsgID = 0;
let isAdmin = false;

/**
 * Entry point into chat room
 */
window.onload = function() {
    if (!username) window.location.href = "/";
    $('#div-right-menu').prepend(`<div class="row text-center fw-bold"><h2>${username}</h2></div>`);
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


    $(".emoji-btn").click(function() {
        var oldText = $("#txt-message").val();
        $("#txt-message").val(oldText + $(this).text());
        $("#txt-message").focus();
    });
    $("#btn-send").click(() => sendMsg($("#txt-message").val()));

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

    $('#btn-invite').click(() => inviteUser($('#inp-invite-user').val()));

    $('#btn-leave').click(() => leaveChatRoom(currentChatroom));
    $('#btn-leave-all').click(() => leaveAllChatRooms());

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
        $.post("/chatroom/sendMessage", payload, function(data) {
            $("#txt-message").val('');
            let chat = $('.chatroom');
            chat.scrollTop(chat.prop("scrollHeight"));
        });

        // check if msg have url
        // let urlRegex = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/
        // if (msg.match(urlRegex)) {
        //     let url = msg.match(urlRegex);
        //     msg = msg.replace(url[0], "<a href=\"" + url[0] + "\" class=\"link-primary\">" + url[0] + "</a>");
        // }

        // let msgHtml = convertMsgToHtml(-1, username, sendto, msg);
        // $(".chat-messages").append(msgHtml);

        // $("#txt-message").val('');
        // let chat = $('.chatroom');
        // chat.scrollTop(chat.prop("scrollHeight"));
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
        $('#div-msg-list').empty();
        data.forEach(message => {
            let msgHtml = convertMsgToHtml(message.messageID, message.sender, message.receiver, message.content, isAdmin);
            $(msgHtml).appendTo($('#div-msg-list'));
        });
        let chat = $('.chatroom');
        chat.scrollTop(chat.prop("scrollHeight"));

    });
}

function loadUserList() {
    $('#div-user-list').empty();
    $.post("chatroom/userList", {chatroomName: currentChatroom}, function(data) {
        data = JSON.parse(data);
        data.forEach(user => {
            var userHtml = userListElement(user, username === user, false);
            $(userHtml).appendTo($('#div-user-list'));
        });
    });
}

function loadUserListFromChatroomData(chatroomData) {
    let data = chatroomData;
    $('#div-user-list').empty();
    let $send_to_list = $('#send-to-list');
    $send_to_list.empty();
    $send_to_list.append('<option selected>Everyone</option>');
    data.users.forEach(user => {
        var userHtml = userListElement(username, user, data.admin);
        $(userHtml).appendTo($('#div-user-list'));
        if (user !== username) $send_to_list.append(`<option>${user}</option>`);
    })

    $(".btn-ban").click(function() {
        let user = $(this).parent().children('label').text();
        let payload = {
            user: user,
            chatroomName: currentChatroom,
        };
        $.post("/chatroom/removeUser", payload);
        console.log(user);
    });

    $(".btn-report").click(function() {
        let user = $(this).parent().children('label').text();
        let payload = {
            user: user,
            chatroomName: currentChatroom,
        };
        $.post("/chatroom/report", payload);
        console.log(user);
    });
}

function updateUserList() {
    if (currentChatroom === "") return;
    let payload = {
        username: username,
        chatroomName: currentChatroom,
    };
    $.post("/chatroom/connectToChatroom", payload, function(data) {
        data = JSON.parse(data);
        loadUserListFromChatroomData(data);
    });
}

function openChatroom(chatroomName) {
    let payload = {
        username: username,
        chatroomName: chatroomName,
    };
    $.post("/chatroom/connectToChatroom", payload, function(data) {
        $(".chat-element").show();
        data = JSON.parse(data);
        if (data.type === "public") {
            $('#div-invite-box').hide();
        }
        else $('#div-invite-box').show();

        currentChatroom = chatroomName;
        $("#room-name").text(chatroomName);
        isAdmin = username === data.admin;

        loadUserListFromChatroomData(data);

        loadMessages();
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
    else if (message.data == "updateUsers") updateUserList();
    else if (message.data == "updateMessages") loadMessages();
    else if (message.data == "updatePublicRooms") loadPublicRoomList();
    else if (message.data == "connectNow") console.log("connectNow");
    else if (message.data.includes("ban")) {
        let msg = message.data.split("&");
        if (currentChatroom == msg[1]) {
            currentChatroom = "";
        }
        loadChatRoomList();
    }
    else {
        let msg = JSON.parse(message.data);
        if (msg.chatRoomName == currentChatroom) {
            let msgHtml = convertMsgToHtml(msg.messageID, msg.sender, msg.receiver, msg.content, isAdmin);
            $(msgHtml).appendTo($('#div-msg-list'));
        }
    }
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
            chatroomName: currentChatroom,
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
        chatroomName: currentChatroom
    }
    $.post("/chatroom/deleteMessage", payload);
}

function leaveAllChatRooms() {
    let payload = {
        username: username
    }
    $.post("/chatroom/leaveAllChatroom", payload, function(data) {
        loadChatRoomList();
    });
}

function leaveChatRoom(roomName) {
    let payload = {
        username: username,
        chatroomName: currentChatroom
    }

    $.post("/chatroom/leaveChatroom", payload, function (data) {
        currentChatroom = "";
        $(".chat-element").hide();
        loadPublicRoomList();
        loadChatRoomList();
    });
}

function convertMsgToHtml(msgId, sender, receiver, content, isAdmin) {
    let urlRegex = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/
    if (content.match(urlRegex)) {
        let url = content.match(urlRegex);
        content = msg.replace(url[0], "<a href=\"" + url[0] + "\" class=\"link-primary\">" + url[0] + "</a>");
    }
    let msg;
    receiver = receiver === username ? "You" : receiver;
    if (sender == username) {
        msg = rightMsgHtml(receiver, content, msgId);
    } else {
        msg = leftMsgHtml(sender, receiver, content, msgId, isAdmin);
    }
    return msg;
}