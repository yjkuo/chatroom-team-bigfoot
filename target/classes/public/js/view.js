'use strict';

window.onload = function() {
    $('#form-login').submit(function(e) {
        e.preventDefault();
        console.log("hello");
        window.location.href = "/main.html";
    })

    $('#form-signup').submit(function(e) {
        e.preventDefault();
        window.location.href = "/main.html";
    })
};
