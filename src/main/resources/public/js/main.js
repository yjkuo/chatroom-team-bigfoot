'use strict';

import {chatroomsListElement, userListElement} from './components.js';

const webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chatapp");
// const webSocket = new WebSocket("wss://" + "alg23-ex7-chat.herokuapp.com" + "/chatapp");

let username = localStorage.getItem('username');
let openChatroom = "";

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
};

function sendMsg(msg) {
    if (msg != '') {
        let sendto = $('#send-to-list').find(":selected").val();
        let payload = {
            sender: username,
            chatroomName: openChatroom,
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

function chatRoomOpen(chatroomName) {
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

        $.get("/chatroom/getMessages", payload, function(data) {
            data = JSON.parse(data);
            console.log(data);
        });

        openChatroom = chatroomName;
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
            var isOpen = item === openChatroom;
            var html = chatroomsListElement(item, isOpen);
            $(html).appendTo($('#div-chatrooms-list'));
        });
        $(".btn-open-chatroom").click(function() {
            chatRoomOpen($(this).parent().children('label').text());
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
        charRoomName: $('#create-roomname').val(),
        size: $('#create-roomname').val(),
        type: $('input[name=roomTypeRadio]:checked', '#form-create').val()
    };

    $.post("/chatroom/createChatroom", payload, function(data) {
        console.log(data);
    }, "json");
}
