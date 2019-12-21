function saveProfile(){
   var data = {"firstName":$("#firstName").val(), "lastName":$("#lastName").val(),
   "sphere":$("#sphere").val(), "phoneNumber":$("#phoneNumber").val()}
   var myUrl = "/profile/edit";
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: myUrl,
       data: JSON.stringify(data),
       success: function () {
          window.location.replace("/");
       },
       error: function (xhr, status, errorThrown) {
       console.log(xhr.responseJSON.message);
       }
   })
}