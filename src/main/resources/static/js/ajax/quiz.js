function endQuiz(){
   confirm("Are You sure you want to end quiz?");
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
              alert("Quiz finished with rating " + response.message);
           },
           error: function (xhr, status, errorThrown) {
           console.log(xhr.responseJSON.message);
             alert("ERROR");
           }
       })
}

$(document).ready(function () {
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
          console.log('saved = ' + response);
       },
       error: function (xhr, status, errorThrown) {
       console.log(xhr.responseJSON.message);
         alert("ERROR");
       }
   })
}