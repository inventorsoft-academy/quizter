function endQuiz(){
   confirm("Are You sure you want to end quiz?");
   var data = {};
   var questionType;
   var answers = [];
   var previousQuestionId = 'empty';
   var result = {};

   $('.resultAnswer').each(function() {
       questionType = $(this).attr("questionType");

   if("MULTIVARIANT" === questionType) {
   result = {};
   $('input[name="answersArray"]:checked').each(function() {
   var questionId =$(this).attr("questionId");
   if (questionId === previousQuestionId){
        answers.push($(this).val());
   } else if (previousQuestionId === 'empty'){
        answers = new Array();
        answers.push($(this).val());
        previousQuestionId = questionId;
   } else if (questionId !== previousQuestionId){
        result[previousQuestionId] = answers;
        answers = new Array();
        answers.push($(this).val());
        previousQuestionId = questionId;
   }
   result[previousQuestionId] = answers;
   data[questionType] = result;
   });
   }

   if("CODE" === questionType) {
       var questionId =$(this).attr("questionId");
       answers = new Array();
       result = {};
       answers.push($(this).val());
       result[questionId] = answers;
       data[questionType] = result;
   }

  });



   var myUrl = document.URL;
   $.ajax({
       contentType:"application/json; charset=utf-8",
       type: "POST",
       url: myUrl,
       data: JSON.stringify(data),
       success: function (response) {
          alert("Quiz finished with rating " + response.message);
       },
       error: function (xhr, status, errorThrown) {
       console.log(xhr.responseJSON.message);
         alert("ERROR");
       }
   })
}