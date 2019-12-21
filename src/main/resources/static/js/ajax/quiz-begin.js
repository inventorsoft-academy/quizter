function beginQuiz(id) {
    window.location.href = "/desk/quiz/" + id;

}

function start(){
var myUrl = document.URL;
       $.ajax({
           contentType:"application/json; charset=utf-8",
           type: "POST",
           url: myUrl,
           success: function (response) {
              beginQuiz(response.message);
           },
           error: function (xhr, status, errorThrown) {
           console.log(xhr.responseJSON.message);
             alert("ERROR");
           }
       })
}