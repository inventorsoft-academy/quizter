$(document).ready(function () {

    $("#registration-form").submit(function (event) {

        event.preventDefault();

        registration();

    });

});

function registration() {
    const role = $("#role").val();

    const url = "/registration/" + role;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url.toString(),
        data: JSON.stringify({
            email : $("#email").val(),
            password : $("#password").val(),
            confirmPassword: $("#confirmPassword").val(),
            role : $("#role").val()
        }),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            alert(data)
        },
        error: function (e) {
            alert(e)
        }
    });
}
