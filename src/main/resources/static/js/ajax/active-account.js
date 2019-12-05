$(document).ready(function () {
    $("#button-layout").click(function (event) {
        alert("$(.href.val()");
        event.preventDefault();

        var url = $("#link").val().toString();
        if (url.startsWith("http://localhost:8080/active-account")) {
            alert("true");
            activeAccount(url);
        }
    });

});

function activeAccount(url) {

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url,
        cache: false,
        success: function (data) {
            location.href = "/login"
        },
        error: function (e) {
        }
    });
}
