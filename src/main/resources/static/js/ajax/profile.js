var myUrl = document.URL;

function saveProfile(){
   var data = {"firstName":$("#firstName").val(), "lastName":$("#lastName").val(),
   "sphere":$("#sphere").val(), "phoneNumber":$("#phoneNumber").val()}
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: myUrl,
       data: JSON.stringify(data),
       success: function () {
          window.location.replace("/profile");
       },
       error: function (xhr, status, errorThrown) {
       console.log(JSON.stringify(xhr));
       if(xhr.responseJSON.message === "Validation error"){
           $('#firstNameError').text(xhr.responseJSON.fieldErrors.firstNameError);
           $('#lastNameError').text(xhr.responseJSON.fieldErrors.lastNameError);
           $('#phoneNumberError').text(xhr.responseJSON.fieldErrors.phoneNumberError);
           $('#sphereError').text(xhr.responseJSON.fieldErrors.sphereError);
       }
       }
   })
}

function addPhoto(){
    document.getElementById('modal').style.display = "block";
}

function cancelPhoto(){
    document.getElementById('modal').style.display = "none";
}

$(document).ready(function () {
    $("#btnSubmit").click(function (event) {
        event.preventDefault();
        fire_ajax_submit();
    });
});

function fire_ajax_submit() {
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    $("#btnSubmit").prop("disabled", true);
    $.ajax({
        type: "PUT",
        enctype: 'multipart/form-data',
        url: myUrl,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            localStorage.setItem('avatar', data);
            var avatar = localStorage.getItem('avatar');
            document.getElementById("userPhoto").src = "data:image/jpg;base64," + avatar;
            document.getElementById('modal').style.display = "none";
        },
        error: function (xhr, status, errorThrown) {
        console.log(JSON.stringify(xhr));
            if(xhr.responseJSON.message === "Validation error") {
                var data = xhr.responseJSON.fieldErrors;
                $('#uploadError').text(data['file.uploadError']);
            }
        }
    });

}

window.onbeforeunload = function(){
   localStorage.removeItem('avatar');
}
