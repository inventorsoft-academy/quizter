function endQuiz() {
    confirm("Are You sure you want to end quiz?");
    var data = new Map();
    var answers = [];
    var previous_question = 'empty';
    $('input[name="answersArray"]:checked').each(function () {

        var question = $(this).attr("question");
        if (question === previous_question) {
            answers.push($(this).val());
        } else if (previous_question === 'empty') {
            answers.push(JSON.stringify({
                id : $("#question-id").text(),
                name: $("#question-name").text(),
                answer:$(this).val(),
                answerType : "MULTIVARIANT"
            }));
            previous_question = question;
        } else if (question !== previous_question) {
            console.log(JSON.stringify(data));
            answers.push(JSON.stringify({
                id : $("#question-id").text(),
                name: $("#question-name").text(),
                answer:$(this).val(),
                answerType : "MULTIVARIANT"
            }));
            previous_question = question;
        }
    });
    console.log(answers);
    $("textarea").each(function () {
        console.log($("#coding-id").val());
        var question = $(this).attr("question");
        if (question === previous_question) {
            answers.push($(this).val());
        } else if (previous_question === 'empty') {
            answers.push(JSON.stringify({
                id : $("#coding-id").text(),
                name: $("#coding-name").text(),
                answer:$(this).val(),
                answerType : "CODE"
            }));
            previous_question = question;
        } else if (question !== previous_question) {
            answers.push(JSON.stringify({
                id : $("#coding-id").text(),
                name: $("#coding-name").text(),
                answer:$(this).val(),
                answerType : "CODE"
            }));
            previous_question = question;
        }

    });
    data.set($("#id-test").text(),answers);
    console.log(Object.fromEntries(data));
    var myUrl = document.URL;
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "POST",
        url: myUrl,
        data: JSON.stringify({
            testId: $("#id-test"),
            answers: JSON.stringify(mapToObj(data))
        }),
        success: function (response) {
            alert("Quiz finished with rating " + response.message);
        },
        error: function (xhr, status, errorThrown) {
            console.log(xhr.responseJSON.message);
            alert("ERROR");
        }
    })
}
function mapToObj(inputMap) {
    let obj = {};

    inputMap.forEach(function(value, key){
        obj[key] = value
    });

    return obj;
}

function startQuiz() {

}