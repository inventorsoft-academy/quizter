var name = '';
var subject = '';
var description = '';

$(document).ready(function () {
    testDescriptionClick();

    var template = $("#testCreationFormScript").html(),
        $target = $(".testCreationForm"),
        $btnAdd = $('#addOneMoreQuestion'),
        $btnRemove = $('#deleteQuestion'),
        count = 1,
        inputRow = [];

    $btnAdd.click(function () {
        inputRow = {
            count: count
        };
        var html = Mustache.to_html(template, inputRow);
        $target.append(html);
        count++;

    });

   $btnRemove.click(function (e) {
        e.preventDefault();
        $target.find('.testCreationDiv').last().remove();
        if (count <= 1) {
            count = 1;
        } else {
            count--;
        }
    });

    var questions = [];

    $("#finishOfCreation").click(function () {

        $('.testCreationDiv').each(function (i) {
            let index = i + 1;
            var question = {};

            var writeQuestion = $("#writeQuestion_" + index).val();
            var writeFirstAnswer = $("#writeFirstAnswer_" + index).val();
            var writeSecondAnswer = $("#writeSecondAnswer_" + index).val();
            var writeThirdAnswer = $("#writeThirdAnswer_" + index).val();
            var writeFourthAnswer = $("#writeFourthAnswer_" + index).val();

            question["name"] = writeQuestion;

            var answers = {};
            answers[writeFirstAnswer] = $('#firstRadio_' + index).is(":checked") ? "true" : "false";
            answers[writeSecondAnswer] = $('#secondRadio_' + index).is(":checked") ? "true" : "false";
            answers[writeThirdAnswer] = $('#thirdRadio_' + index).is(":checked") ? "true" : "false";
            answers[writeFourthAnswer] = $('#fourthRadio_' + index).is(":checked") ? "true" : "false";

            question["answers"] = answers;

            questions.push(question);
        });

        createTest(name, subject, description, questions);
    });

});

function testDescriptionClick() {
    name = $("#testName").val();
    subject = $('#subject').val();
    description = $('#description').val();
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
                alert("Test has been successfully created!");
                location.href = "/tests"
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }
}





