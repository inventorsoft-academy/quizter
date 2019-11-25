function resetPassword(){
   var data = {"userEmail":$("input").val()}
   console.log("button pressed");
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: "/resetPassword",
       data: JSON.stringify(data),
       success: function (result) {
          if(result === "success"){
             location.href = "/resetPassword/success"
          } else {
             location.href = "/resetPassword/userNotFound"
          }
       }
   })
}

function saveNewPassword(){
   var data = {"password":$("#password").val(), "confirmPassword":$("#confirmPassword").val()}
   console.log("button pressed");
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: "/newPassword",
       data: JSON.stringify(data),
       success: function (result) {
          if(result === "newPasswordSaved"){
          alert("New password successfully saved");
             location.href = "/login";
          } else {
             location.href = "/newPassword/passwordsMismatch";
          }
       }
   })
}