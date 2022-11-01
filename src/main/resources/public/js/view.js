'use strict';

window.onload = function() {
    $('#form-login').submit(function(e) {
        e.preventDefault();
        logIn();
    })

    $('#form-signup').submit(function(e) {
        e.preventDefault();
        signUp();
    })
};

function signUp() {
    let interests = "";
    if ($('div.checkbox-group :checkbox:checked').length === 0) return;
    $('div.checkbox-group :checkbox:checked').each(function(i){
        if (i != 0) interests += "&";
        interests += $(this).val();
    });
    console.log(interests);
    let payload = {
        username: $('#in-username').val(),
        password: $('#in-password').val(),
        age: $('#in-age').val(),
        school: $('#in-school').val(),
        interests: interests
    };
    $.post("/register", payload, function(data) {
        console.log(data.username);
        if (data.username !== "") {
            window.location.href = "/main.html";
        }
        else {
            $('#alert-signup').show();
        }
    }, "json");
}

function logIn() {
    let payload = {
        username: $('#login-username').val(),
        password: $('#login-password').val()
    };
    $.post("/login", payload, function(data) {
        if (data.username !== "") {
            window.location.href = "/main.html";
        }
        else {
            $('#alert-login').show();
        }
    }, "json");
}