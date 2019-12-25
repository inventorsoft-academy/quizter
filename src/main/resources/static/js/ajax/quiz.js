
function endQuiz(){
    document.getElementById('modal').style.display = "block";
}

function proceed(){
    document.getElementById('modal').style.display = "none";
}

function finishQuiz(){
    document.getElementById('modal').style.display = "none";
   var questionType;
   var questionId;
   var answers = [];
   var result = [];
   var previousQuestionId = 'empty';

   $('.resultAnswer').each(function() {
       questionType = $(this).attr("questionType");

   if("MULTIVARIANT" === questionType) {
   questionId =$(this).attr("questionId");
   if (questionId !== previousQuestionId){
   previousQuestionId = questionId;
   $('input[questionId='+questionId+']:checked').each(function() {
        answers.push($(this).val());
    });
    var data ={};
    data.questionType = questionType;
    data.questionId = questionId;
    data.answers = answers;
    result.push(data);
    answers = [];
    }
   }

   if("CODE" === questionType) {
       questionId =$(this).attr("questionId");
       answers = [];
       answers.push($(this).val());
       data ={};
       data.questionType = questionType;
       data.questionId = questionId;
       data.answers = answers;
       result.push(data);
   }

  });

    var myUrl = document.URL;
       $.ajax({
           contentType:"application/json; charset=utf-8",
           type: "POST",
           url: myUrl,
           data: JSON.stringify(result),
           success: function (response) {
              clearInterval(countdownTimer);
              document.getElementById('endId').style.display = "none";
              document.getElementById('rowId').style.display = "none";
              document.getElementById('time').style.display = "none";
              alert("Quiz finished with rating " + response.message);
              window.location.href = "/desk/";
           },
           error: function (xhr, status, errorThrown) {
           console.log(xhr.responseJSON.message);
             alert("ERROR");
           }
       })
}

$(document).ready(function () {
    duration = parseInt($('#duration').text());
    startTimer();
    var ckbox = $('#checkbox');

    $('input[name="answersArray"]').on('click',function () {
       var answers = [];
       var questionType;
       var questionId;
       questionId =$(this).attr("questionId");
       questionType =$(this).attr("questionType");
        if (this.checked) {
            $('input[questionId='+questionId+']').each(function(){
            if(this.checked) {
                answers.push($(this).val());
            }
            });
            saveResult(questionId, questionType, answers);
        } else {
            $('input[questionId='+questionId+']').each(function(){
                        if(this.checked) {
                            answers.push($(this).val());
                        }
                        });
            saveResult(questionId, questionType, answers);
        }
    });
});

function saveResult(questionId, questionType, answers){
   var result = [];
   var data = {};
   data.questionType = questionType;
   data.questionId = questionId;
   data.answers = answers;
   result.push(data);
   putToBack(result);

}

function putToBack(data){
 var myUrl = document.URL;
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "PUT",
       url: myUrl,
       data: JSON.stringify(data),
       success: function (response) {
            clearInterval(countdownTimer);
            duration = parseInt(response.message);
            startTimer();
       },
       error: function (xhr, status, errorThrown) {
       console.log(xhr.responseJSON.message);
         alert("ERROR");
       }
   })
}

var countdownTimer;
var duration;

function startTimer(){
var timer = duration, minutes, seconds;
countdownTimer = setInterval(function () {
            minutes = parseInt(timer / 60, 10)
            seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            document.getElementById('time').textContent = minutes + ":" + seconds;

            if (--timer < 60) {
                $('#time').css({"color": "red"})
            }
            if (timer <= 0) {
            document.getElementById('time').textContent = "00:00";
            clearInterval(countdownTimer);
            finishQuiz();
            }
        }, 1000);
}