$(document).ready(function () {
    newTestDescriptionClick();

    getAllTestsForEdit();
});

var name = '';
var subject = '';
var description = '';

function newTestDescriptionClick() {
    name = $("#newTestName").val();
    subject = $('#newSubject').val();
    description = $('#newDescription').val();
}

function getAllTestsForEdit() {
    var testId = $("#testEditPage").attr("dataEdit_id");
    $.getJSON("/tests/" + testId,
        function (data) {
            $("#newTestName").val(
                data.name
            );
            $("#newSubject").val(
                data.subject
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
                inputRowEdit = {
                    editCount: editCount
                };

                var htmlEdit = Mustache.to_html(templateEdit, inputRowEdit);
                $targetEdit.append(htmlEdit);
                editCount++;

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

                j++;
            });

            // $('.testEditionDiv').on('click', '.deleteTestEdit', function () {
            //$(this).parent().remove();

            $('.deleteTestEdit').on('click', function () {
                var div = $(this).attr('testEditionDivId');
                $targetEdit.find(div).remove();

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
            $("#finishOfEdit").click(function () {

                $('.testEditionDiv').each(function (i) {
                    let index = i + 1;

                    var question = {};
                    var writeQuestion = $("#writeQuestionForEdit_" + index).val();
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
                    question["answers"] = answers;

                    questions.push(question);
                });

                editTestFunc(data.id, name, subject, description, questions);
            });
        }
    );
}

function editTestFunc(id, name, subject, description, questions) {
    if (window.confirm("Do you really want to change test?")) {
        $.ajax({
            url: '/tests/' + id,
            type: 'PUT',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
                subject: subject,
                description: description,
                questions: questions
            }),
            success: function () {
                alert("Test has been successfully changed!");
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





