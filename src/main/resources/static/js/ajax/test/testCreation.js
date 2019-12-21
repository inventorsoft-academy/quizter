var name = '';
var subject = '';
var description = '';
var duration = '';

$(document).ready(function () {
    testDescriptionClick();

    var template = $("#testCreationFormScript").html(),
        $target = $(".testCreationForm"),
        $btnAdd = $('#addOneMoreQuestion'),
        $btnRemove = $('#deleteQuestion'),
        count = 1,
        inputRow = [];

    if (count === 1) {
        $btnRemove.hide();
    }

    $btnAdd.click(function () {
        $btnRemove.show();

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

        if (count === 1) {
            $btnRemove.hide();
        }
    });

    var questions = [];

    $("#question-part-creation").click(function () {
        questions = [];

        var form = $(this).closest(".setup-content");
        var curInputs = form.find("input[type='radio'], input[type='number'], textarea"),
            isValid = true;

        $(".form-group").removeClass("has-error");
        for (var i = 0; i < curInputs.length; i++) {
            if (!curInputs[i].validity.valid) {
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
            }
        }

        if (isValid) {
            $(".form-group").removeClass("has-error");
        }

        $('.testCreationDiv').each(function (i) {
            let index = i + 1;
            var question = {};

            var writeQuestion = $("#writeQuestion_" + index).val();
            var questionMark = $("#questionMark_" + index).val();

            var writeFirstAnswer = $("#writeFirstAnswer_" + index).val();
            var writeSecondAnswer = $("#writeSecondAnswer_" + index).val();
            var writeThirdAnswer = $("#writeThirdAnswer_" + index).val();
            var writeFourthAnswer = $("#writeFourthAnswer_" + index).val();

            question["name"] = writeQuestion;
            question["price"] = questionMark;
            question["questionType"] = "MULTIVARIANT";

            var answers = {};
            answers[writeFirstAnswer] = $('#firstRadio_' + index).is(":checked") ? "true" : "false";
            answers[writeSecondAnswer] = $('#secondRadio_' + index).is(":checked") ? "true" : "false";
            answers[writeThirdAnswer] = $('#thirdRadio_' + index).is(":checked") ? "true" : "false";
            answers[writeFourthAnswer] = $('#fourthRadio_' + index).is(":checked") ? "true" : "false";

            question["answers"] = answers;

            questions.push(question);
        });

    });

    $("#coding-part-creation").click(function () {
        var question = {};

        var task = $("#task").val();
        var code = $("#code").val();
        var test = $("#test").val();
        var codeMark = $("#codeMark").val();

        question["name"] = task;
        question["price"] = codeMark;
        question["questionType"] = "CODE";
        question["unitTest"] = test;
        question["codeTask"] = code;

        questions.push(question);

        createTest(name, subject, description, questions, duration);
    });

});

function testDescriptionClick() {
    name = $("#testName").val();
    subject = $('#subject').val();
    description = $('#description').val();
    duration = $('#testDuration').val();
}

function createTest(name, subject, description, questions, duration) {
    if (window.confirm("Do you really want to create test?")) {
        $.ajax({
            url: '/cabinet/tests/',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
                subject: subject,
                description: description,
                questions: questions,
                duration: duration
            }),
            success: function () {
                alert("Test has been successfully created!");
                location.href = "/tests"
            },
            error: function (xhr, status, errorThrown) {
                var response = new ErrorResponse(JSON.parse(xhr.responseText));

                if (response.fieldErrors.nameError) {
                    alert(response.fieldErrors.nameError);
                    $("#testNameLabel").append(" - " + response.fieldErrors.nameError);
                    $("#testNameLabel").css("color", "red");
                } else if (response.fieldErrors.subjectError) {
                    alert(response.fieldErrors.subjectError);
                    $("#testSubjectLabel").append(" - " + response.fieldErrors.subjectError);
                    $("#testSubjectLabel").css("color", "red");
                } else if (response.fieldErrors.descriptionError) {
                    alert(response.fieldErrors.descriptionError);
                    $("#testDescriptionLabel").append(" - " + response.fieldErrors.descriptionError);
                    $("#testDescriptionLabel").css("color", "red");
                } else if (response.fieldErrors.durationError) {
                    alert(response.fieldErrors.durationError);
                    $("#testDurationLabel").append(" - " + response.fieldErrors.durationError);
                    $("#testDurationLabel").css("color", "red");
                } else alert(response.fieldErrors.TestCreationFormError);

            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }

}
