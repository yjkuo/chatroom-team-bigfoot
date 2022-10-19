'use strict';

const webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/chatapp");
// const webSocket = new WebSocket("wss://" + "alg23-ex7-chat.herokuapp.com" + "/chatapp");

/**
 * Entry point into chat room
 */
window.onload = function() {

    webSocket.onclose = () => alert("WebSocket connection closed");
    webSocket.onmessage = (msg) => updateChatRoom(msg);

    $('#form-login').submit(function(e) {
        e.preventDefault();
        console.log("hello");
        window.location.href = "/main.html";
    })

    $('#form-signup').submit(function(e) {
        e.preventDefault();
        window.location.href = "/main.html";
    })


    $("#btn-msg").click(() => sendMessage($("#message").val()));
    $(".emoji-btn").click(function() {
        var oldText = $("#txt-message").val();
        $("#txt-message").val(oldText + $(this).text());
        $("#txt-message").focus();
    });
    $("#btn-send").click(() => sendMsg($("#txt-message").val()));
};

function sendMsg(msg) {
    if (msg != '') {
        let sendto = $('#send-to-list').find(":selected").val();
        var msgHtml = `
                                    <li class="list-group-item">
                                    From Me to ${sendto} : ${msg}
                                    <div class="float-end">
                                        <span style="font-size:10.0pt">Sending</span>
                                        <button class="btn btn-warning btn-sm">Edit</button>
                                        <button class="btn btn-danger btn-sm">Delete</button>
                                    </div>
                                </li>
    `;
        $("#lst-messages").append(msgHtml);
        $("#txt-message").val('');
    }
}
/**
 * Send a message to the server.
 * @param msg  The message to send to the server.
 */
function sendMessage(msg) {
    if (msg !== "") {
        webSocket.send(msg);
        $("#message").val("");
    }
}

function signUp() {
    console.log("sign_up");
}

function addEmoji() {
    console.log($("this").text());
    $("#txt-message").val($(this).attr("value"));
}

/**
 * Update the chat room with a message.
 * @param message  The message to update the chat room with.
 */
function updateChatRoom(message) {
    // TODO: convert the data to JSON and use .append(text) on a html element to append the message to the chat area
    let data = JSON.parse(message.data);
    $("#chatArea").append(data["userMessage"]);
}
