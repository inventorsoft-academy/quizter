$(document).ready(function () {
    $("#step-3").find('textarea').text("   ");
    testDescriptionClick()
});

var name = '';
var subject = '';
var description = '';

function testDescriptionClick() {
    name = $("#testName").val();
    subject = $('#subject').val();
    description = $('#description').val();
}

function numbersOfQuestion() {
    var numbers = $('#numberOfQuestions').val();
    createQuestion(name, subject, description, numbers);
}

function createQuestion(name, subject, description, numbers) {
    var questions = [];
    var writeQuestion = '';
    var writeFirstAnswer = '';
    var writeSecondAnswer = '';
    var writeThirdAnswer = '';
    var writeFourthAnswer = '';

    var i = 1;
    $("#finishOfCreation").click(function () {
        var question = {};
        writeQuestion = $('#writeQuestion').val();
        writeFirstAnswer = $('#writeFirstAnswer').val();
        writeSecondAnswer = $('#writeSecondAnswer').val();
        writeThirdAnswer = $('#writeThirdAnswer').val();
        writeFourthAnswer = $('#writeFourthAnswer').val();

        var answers = {};
        answers[writeFirstAnswer] = $('#firstRadio').is(":checked") ? "true" : "false";
        answers[writeSecondAnswer] = $('#secondRadio').is(":checked") ? "true" : "false";
        answers[writeThirdAnswer] = $('#thirdRadio').is(":checked") ? "true" : "false";
        answers[writeFourthAnswer] = $('#fourthRadio').is(":checked") ? "true" : "false";

        question["name"] = writeQuestion;
        question["answers"] = answers;

        questions.push(question);

        $('#writeQuestion').val('');
        $('#writeFirstAnswer').val('');
        $('#writeSecondAnswer').val('');
        $('#writeThirdAnswer').val('');
        $('#writeFourthAnswer').val('');
        $('#firstRadio').prop("checked", false);
        $('#secondRadio').prop("checked", false);
        $('#thirdRadio').prop("checked", false);
        $('#fourthRadio').prop("checked", false);

        if (i < numbers) {
            i++;
            $("#indexOfQuestion").text(i);
        } else {
            createTest(name, subject, description, questions);
        }

    });

}

function createQuestionForm() {


}

function createTest(name, subject, description, questions) {
    if (window.confirm("Do you really want to create test?")) {
        $.ajax({
            url: '/tests/',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
                subject: subject,
                description: description,
                questions: questions
            }),
            success: function () {
                $("#to-coding-test-part").removeAttr('hidden');
                $("#finishOfCreation").hide();
                console.log($("#step-3").find('textarea'));
                $("#step-3").find('textarea').text("  sas");

            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });

    }
}




