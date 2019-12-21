function endQuiz(){
   confirm("Are You sure you want to end quiz?");
   var data = {};
   var answers = [];
   var previous_question = 'empty';
   $('input[name="answersArray"]:checked').each(function(){
   var question =$(this).attr("question");
   if (question === previous_question){
        answers.push($(this).val());
   } else if (previous_question === 'empty'){
        answers = new Array();
        answers.push($(this).val());
        previous_question = question;
   } else if (question !== previous_question){
        data[previous_question] = answers;
        console.log("data1 = " + JSON.stringify(data));
        answers = new Array();
        answers.push($(this).val());
        previous_question = question;
   }
   });
   data[previous_question] = answers;
   console.log("data = " + JSON.stringify(data));
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