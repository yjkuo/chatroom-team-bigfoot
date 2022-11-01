'use strict';

const webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chatapp");
// const webSocket = new WebSocket("wss://" + "alg23-ex7-chat.herokuapp.com" + "/chatapp");

/**
 * Entry point into chat room
 */
window.onload = function() {
    let username = localStorage.getItem('username');
    if (!username) window.location.href = "/";
    $(".chat-element").hide();
    let msg = {
        "type" : "initialize",
        "username" : username
    }
    webSocket.onopen = () => webSocket.send(JSON.stringify(msg));

    webSocket.onclose = () => alert("WebSocket connection closed");
    // webSocket.onmessage = (msg) => updateChatRoom(msg);

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
};

function sendMsg(msg) {
    if (msg != '') {
        // check if msg have url
        let urlRegex = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/
        if (msg.match(urlRegex)) {
            let url = msg.match(urlRegex);
            msg = msg.replace(url[0], "<a href=\"" + url[0] + "\" class=\"link-primary\">" + url[0] + "</a>");
        }

        let sendto = $('#send-to-list').find(":selected").val();
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
/**
 * Send a message to the server.
 * @param msg  The message to send to the server.
 */
// function sendMessage(msg) {
//     if (msg !== "") {
//         webSocket.send(msg);
//         $("#message").val("");
//     }
// }

function signUp() {
    console.log("sign_up");
    console.log($("input[type='checkbox']").serialize());
    let payload = {
        username: $('#in-username').val(),
        password: $('#in-password').val(),
        age: $('#in-age').val(),
        school: $('#in-school').val(),
        interests: "fas"
    }
    $.post("/register", payload);
}

function addEmoji() {
    console.log($("this").text());
    $("#txt-message").val($(this).attr("value"));
}

/**
 * Update the chat room with a message.
 * @param message  The message to update the chat room with.
 */
// function updateChatRoom(message) {
//     let data = JSON.parse(message.data);
//     $("#chatArea").append(data["userMessage"]);
// }

function logout() {
    localStorage.removeItem('username');
    window.location.href = "/";
}
