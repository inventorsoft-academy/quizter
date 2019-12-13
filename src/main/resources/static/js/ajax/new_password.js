function saveNewPassword(){
   var data = {"password":$("#password").val(), "confirmPassword":$("#confirmPassword").val()}
   var myUrl = document.URL;
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: myUrl,
       data: JSON.stringify(data),
       success: function (response) {
          $('#newPasswordForm').text(" ");
          $('#messageSuccess').text("New password successfully saved");
          $('#messageLogin').text("Login");
       },
       error: function (xhr, status, errorThrown) {
       console.log(xhr.responseJSON.message);
         if(xhr.responseJSON.message === "Validation error"){
            $('#mismatchMessage').text(xhr.responseJSON.fieldErrors.PasswordError);
         } else if(xhr.responseJSON.message === "Token not valid"){
            $('#newPasswordForm').text(" ");
            $('#messageWrong').text("something wrong, try again");
         }
       }
   })
}