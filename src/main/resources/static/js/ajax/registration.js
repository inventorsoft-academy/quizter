import {ErrorResponse} from '/js/model/error-response';

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
            location.href = "/active-account-page"
        },
        error: function (xhr, status, errorThrown) {
            const response = new ErrorResponse();
            // if (response.message.has("emailError")){
            //     alert(response.message.get("emailError"));
            // }
            $("#email-error").removeAttr('hidden');
            alert(JSON.parse(JSON.parse(xhr.responseText).message).emailError);
            $("#email-error").text();
        }
    });
}
