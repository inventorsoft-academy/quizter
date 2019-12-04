function resetPassword(){
   var data = {"userEmail":$("input").val()}
   console.log("button pressed");
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: "/resetPassword",
       data: JSON.stringify(data),
       success: function () {
             $('#successMessage').text("Check Your email");
             $('#resetForm').text("");
       }
   })
}