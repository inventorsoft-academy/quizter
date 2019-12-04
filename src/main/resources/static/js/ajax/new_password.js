function saveNewPassword(){
   var data = {"password":$("#password").val(), "confirmPassword":$("#confirmPassword").val()}
   var myUrl = document.URL;
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: myUrl,
       data: JSON.stringify(data),
       success: function (response) {
       console.log(response);
          if(response.message === "newPasswordSaved"){
          $('#newPasswordForm').text(" ");
          $('#messageSuccess').text("New password successfully saved");
          $('#messageLogin').text("Login");
          } else if (response.message === "messageWrong"){
          $('#newPasswordForm').text(" ");
          $('#messageWrong').text("something wrong, try again");
          }
       },
         error: function (errorResponse) {
         $('#mismatchMessage').text(errorResponse.responseJSON.fieldErrors.PasswordError);
         }
   })
}