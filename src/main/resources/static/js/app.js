$(document).ready(function () {

    $.ajax({
           contentType:'application/json; charset=utf-8',
           type: 'GET',
           url: '/profile/bar',
           success: function (response) {
           console.log('response ===' + JSON.stringify(response));
//           if (response.photoUrl !== null && response.photoUrl !== 'none'){
//                document.getElementById('photoHeader').setAttribute('src', response.photoUrl);
//                document.getElementById('photoSideBar').setAttribute('src', response.photoUrl);
//           }
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