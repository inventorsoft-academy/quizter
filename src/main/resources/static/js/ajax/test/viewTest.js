$(document).ready(function () {
    getTest()
});

function getTest() {
    var dataId = $("#viewPage").attr("data_id");

    $.getJSON("/tests/" + dataId,
        function (data) {
            var testName = Mustache.render("{{name}}", data);
            $("#testName").html(testName);
            var testSubject = Mustache.render("{{subject}}", data);
            $("#testSubject").html(testSubject);
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
                };

                $("#getQuestions").append(
                    Mustache.render(getQuestionsScript, questionWithAnswer)
                );

                questionIndex++;
            })
        }
    );
}


