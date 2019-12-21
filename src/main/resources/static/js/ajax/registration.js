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
            $("#confirm-password-error").attr("hidden", true);

        },
        error: function (xhr, status, errorThrown) {
            var response = new ErrorResponse(JSON.parse(xhr.responseText));

            if (response.fieldErrors.emailError !== undefined) {
                $("#email-error").removeAttr('hidden');
                $("#email-error").text(response.fieldErrors.emailError);
            }
            if (response.fieldErrors.PasswordError ===  "Password is weak") {
                alert(response.fieldErrors.PasswordError);
                $("#password-error").removeAttr('hidden');
                $("#password-error").text(response.fieldErrors.PasswordError);
            }
            if (response.fieldErrors.PasswordError ===  "Password Mismatch") {
                alert(response.fieldErrors.PasswordError);
                $("#confirm-password-error").removeAttr('hidden');
                $("#confirm-password-error").text(response.fieldErrors.PasswordError);
            }
        }
    });
}
