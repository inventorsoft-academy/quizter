$(document).ready(function () {

    getTest();

});

function getTest() {
    var dataId = $("#viewPage").attr("data_id");

    $.getJSON("/cabinet/rest-tests/" + dataId,
        function (data) {
            var testName = Mustache.render("{{name}}", data);
            $("#testName").html(testName);
            var testSubject = Mustache.render("{{subject.name}}", data);
            $("#testSubject").html(testSubject);
            var testAuthor = Mustache.render("{{author.userEmail}}", data);
            $("#testAuthor").html(testAuthor);
            var testDescription = Mustache.render("{{description}}", data);
            $("#testDescription").html(testDescription);

            let questionIndex = 1;


            var getQuestionsScript = $('#getQuestionsScript').html();
            $.each(data.questions, function (index, question) {
                var keys = [];
                $.each(question.answers, function (key, value) {
                    keys.push(key);
                });

                var questionWithAnswer = {
                    "name": question.name,
                    "questionIndex": questionIndex,
                    "firstAnswer": keys[0],
                    "secondAnswer": keys[1],
                    "thirdAnswer": keys[2],
                    "fourthAnswer": keys[3],
                    "codeTask": question.codeTask
                };

                $("#getQuestions").append(
                    Mustache.render(getQuestionsScript, questionWithAnswer)
                );

                if (question.questionType === "CODE") {
                    $("#getCodeQuestion_" + questionIndex).show();
                    $("#getAnswers_" + questionIndex).hide();
                } else {
                    $("#getAnswers_" + questionIndex).show();
                    $("#getCodeQuestion_" + questionIndex).hide()
                }

                questionIndex++;
            })
        }
    );
}


