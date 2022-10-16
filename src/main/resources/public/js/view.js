'use strict';

window.onload = function() {
    $('#form-signup').submit(function(e) {
        e.preventDefault();
        console.log("hello");
        window.location.href = "/main";
    })
};
