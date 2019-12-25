$(document).ready(function () {
    newTestDescriptionClick();

    getAllTestsForEdit();
});

var version = '';
var description = '';
var duration = '';

function newTestDescriptionClick() {
    version = $("#version").val();
    description = $('#newDescription').val();
    duration = $('#editTestDuration').val();
}

function getAllTestsForEdit() {
    var testId = $("#testEditPage").attr("dataEdit_id");
    $.getJSON("/cabinet/rest-tests/" + testId,
        function (data) {
            $("#editTestDuration").val(
                data.duration
            );

            $("#version").val(
                data.version
            );

            $("#newDescription").val(
                data.description
            );

            var templateEdit = $("#testEditionFormScript").html(),
                $targetEdit = $(".testEditionForm"),
                $btnAddEdit = $('#addOneMoreQuestionToEdit'),
                editCount = 1,
                inputRowEdit = [];

            var j = 1;

            $.each(data.questions, function (index, question) {
                if (question.questionType === "MULTIVARIANT") {

                    inputRowEdit = {
                        editCount: editCount
                    };

                    var htmlEdit = Mustache.to_html(templateEdit, inputRowEdit);
                    $targetEdit.append(htmlEdit);
                    editCount++;

                    $("#questionMarkForEdit_" + j).val(
                        question.price,
                    );

                    $("#writeQuestionForEdit_" + j).append(
                        question.name
                    );

                    var keys = [];
                    var values = [];
                    $.each(question.answers, function (key, value) {
                        keys.push(key);
                        values.push(value);
                    });

                    $("#writeFirstAnswerForEdit_" + j).val(keys[0]);
                    $("#writeSecondAnswerForEdit_" + j).val(keys[1]);
                    $("#writeThirdAnswerForEdit_" + j).val(keys[2]);
                    $("#writeFourthAnswerForEdit_" + j).val(keys[3]);

                    values[0] ? $("#firstRadioEdit_" + j).prop('checked', true) : $("#firstRadioEdit_" + j).prop('checked', false);
                    values[1] ? $("#secondRadioEdit_" + j).prop("checked", true) : $("#secondRadioEdit_" + j).prop("checked", false);
                    values[2] ? $("#thirdRadioEdit_" + j).prop("checked", true) : $("#thirdRadioEdit_" + j).prop("checked", false);
                    values[3] ? $("#fourthRadioEdit_" + j).prop("checked", true) : $("#fourthRadioEdit_" + j).prop("checked", false);

                } else {
                    $("#editTask").val(
                        question.name
                    );
                    $("#editCodeMark").val(
                        question.price
                    );
                    $("#editCode").val(
                        question.codeTask
                    );
                    $("#editUnitTest").val(
                        question.unitTest
                    );
                }

                j++;
            });

            $('.testEditionDiv').on('click', '.deleteTestEdit', function () {
                $(this).parent().remove();

                if (editCount <= 1) {
                    editCount = 1;
                } else {
                    editCount--;
                }
            });

            $btnAddEdit.click(function () {
                inputRowEdit = {
                    editCount: editCount
                };
                var html = Mustache.to_html(templateEdit, inputRowEdit);
                $targetEdit.append(html);
                editCount++;
            });

            var questions = [];

            $("#edit-question-part-creation").click(function () {
                questions = [];

                var form = $(this).closest(".setup-content");
                var curInputs = form.find("input[type='radio,number'], textarea"),
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

                $('.testEditionDiv').each(function (i) {
                    let index = i + 1;

                    var question = {};
                    var writeQuestion = $("#writeQuestionForEdit_" + index).val();
                    var questionMark = $("#questionMarkForEdit_" + index).val();
                    var writeFirstAnswer = $("#writeFirstAnswerForEdit_" + index).val();
                    var writeSecondAnswer = $("#writeSecondAnswerForEdit_" + index).val();
                    var writeThirdAnswer = $("#writeThirdAnswerForEdit_" + index).val();
                    var writeFourthAnswer = $("#writeFourthAnswerForEdit_" + index).val();

                    var answers = {};
                    answers[writeFirstAnswer] = $('#firstRadioEdit_' + index).is(":checked") ? "true" : "false";
                    answers[writeSecondAnswer] = $('#secondRadioEdit_' + index).is(":checked") ? "true" : "false";
                    answers[writeThirdAnswer] = $('#thirdRadioEdit_' + index).is(":checked") ? "true" : "false";
                    answers[writeFourthAnswer] = $('#fourthRadioEdit_' + index).is(":checked") ? "true" : "false";

                    question["name"] = writeQuestion;
                    question["questionType"] = "MULTIVARIANT";
                    question["price"] = questionMark;
                    question["answers"] = answers;

                    questions.push(question);
                });

            });

            $("#edit-coding-part-creation").click(function () {
                var question = {};

                var task = $("#editTask").val();
                var code = $("#editCode").val();
                var test = $("#editUnitTest").val();
                var codeMark = $("#editCodeMark").val();

                question["name"] = task;
                question["price"] = codeMark;
                question["questionType"] = "CODE";
                question["unitTest"] = test;
                question["codeTask"] = code;

                questions.push(question);

                editTestFunc(data.id, data.name, data.subject, duration, version, description, questions);
            });
        }
    );
}

function editTestFunc(id, name, subject, duration, version, description, questions) {
    if (window.confirm("Do you really want to change test?")) {
        $.ajax({
            url: '/cabinet/rest-tests/' + id,
            type: 'PUT',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
                subject: subject,
                description: description,
                version: version,
                questions: questions,
                duration: duration
            }),
            success: function () {
                alert("Test has been successfully changed!");
                location.href = "/cabinet/tests"
            },
            error: function (xhr, status, errorThrown) {
                var response = new ErrorResponse(JSON.parse(xhr.responseText));

                if (response.fieldErrors.TestCreationFormError == "Please set new version") {
                    alert(response.fieldErrors.TestCreationFormError);
                    $("#newVersionLabel").append(" - " + response.fieldErrors.TestCreationFormError);
                    $("#newVersionLabel").css("color", "red");
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



