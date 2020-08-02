$(document).ready(function () {
    $('#logout').delay(2000).fadeOut(2000, function () {
        $(this).remove();
    });
    $('#error').delay(2000).fadeOut(2000, function () {
        $(this).remove();
    });
})
