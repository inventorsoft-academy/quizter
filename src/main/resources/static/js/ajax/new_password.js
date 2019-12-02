function saveNewPassword(){
   var data = {"password":$("#password").val(), "confirmPassword":$("#confirmPassword").val()}
   var myUrl = document.URL;
   console.log("button pressed");
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: myUrl,
       data: JSON.stringify(data),
       success: function (result) {
       console.log(result);
          if(result.message === "newPasswordSaved"){
          $('#newPasswordForm').text(" ");
          $('#messageSuccess').text("New password successfully saved");
          $('#messageLogin').text("Login");
          } else if (result.message === "passwordsMismatch"){
             $('#mismatchMessage').text("Passwords mismatch");
          } else {
          $('#newPasswordForm').text(" ");
          $('#messageWrong').text("something wrong, try again");
          }
       }
   })
}