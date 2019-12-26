$(document).ready(function () {

    $.ajax({
           contentType:'application/json; charset=utf-8',
           type: 'GET',
           url: '/profile/bar',
           success: function (response) {
           localStorage.setItem('avatar', response.photo);
           if (localStorage.getItem('avatar') !== null && localStorage.getItem('avatar') !== 'undefined'){
                var avatar = localStorage.getItem('avatar');
                document.getElementById('photoHeader').src = "data:image/jpg;base64," + avatar;
                document.getElementById('photoSideBar').src = "data:image/jpg;base64," + avatar;
                if(document.getElementById("userPhoto") !== null){
                    document.getElementById("userPhoto").src = "data:image/jpg;base64," + avatar;
                }
           }
           if (response.firstName !== null && response.lastName !== null) {
                document.getElementById('userMailSideBar').innerHTML  = response.firstName + ' ' + response.lastName;
                document.getElementById('userMailHeader').innerHTML  = response.firstName + ' ' + response.lastName;
           } else if (response.firstName !== null && response.lastName === null) {
                 document.getElementById('userMailSideBar').innerHTML  = response.firstName;
                 document.getElementById('userMailHeader').innerHTML  = response.firstName;
           } else if (response.firstName === null && response.lastName !== null) {
                  document.getElementById('userMailSideBar').innerHTML  = response.lastName;
                  document.getElementById('userMailHeader').innerHTML  = response.lastName;
           }
           },
           error: function (xhr, status, errorThrown) {
           console.log("profile data error");
           }
    })

});

window.onbeforeunload = function(){
   localStorage.removeItem('avatar');
}