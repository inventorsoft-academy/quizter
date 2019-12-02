// import {ErrorResponse} from "../model/error-response";

$(document).ready(function () {

    $("#registration-form").submit(function (event) {

        event.preventDefault();

        registration();

    });

});

function registration() {
    const role = $("#role").val();
    alert(role);
    const url = "/registration/" + role;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: url.toString(),
        data: JSON.stringify({
            email: $("#email").val(),
            password: {
                password: $("#password").val(),
                confirmPassword: $("#confirmPassword").val()
            },
            role: $("#role").val()
        }),
        dataType: 'json',
        cache: false,
        success: function (data) {
            location.href = "/active-account-page";
            $("#email-error").attr("hidden", true);
            $("#password-error").attr("hidden", true);
        },
        error: function (xhr, status, errorThrown) {

            if (JSON.parse(JSON.parse(xhr.responseText).message).emailError !== undefined) {
                $("#email-error").removeAttr('hidden');
                $("#email-error").text(JSON.parse(JSON.parse(xhr.responseText).message).emailError);
            }
            if (JSON.parse(JSON.parse(xhr.responseText).message).passwordError !== undefined) {
                $("#password-error").removeAttr('hidden');
                $("#password-error").text(JSON.parse(JSON.parse(xhr.responseText).message).passwordError);
            }
        }
    });
}
