$(document).ready(function () {
    newTestDescriptionClick()
    getTestForEdit()

});

var name = '';
var subject = '';
var description = '';

function newTestDescriptionClick() {
    name = $("#newTestName").val();
    subject = $('#newSubject').val();
    description = $('#newDescription').val();
}

function getTestForEdit() {
    var testId = $("#testEditPage").attr("dataEdit_id");

    $.getJSON("/tests/" + testId,
        function (data) {
            $("#newTestName").val(
                data.name
            );
            $("#newSubject").val(
                data.subject
            );
            $("#newDescription").append(
                data.description
            );

            var questionsFromBD = data.questions;

            var i = 0;

            createQuestionFormForEdit();

            if (i === 0) {
                var keys = []
                var values = []
                $.each(questionsFromBD[0].answers, function (key, value) {
                    keys.push(key);
                    values.push(value);
                })

                setInputs(questionsFromBD[0].name, keys, values)
            }

            var questions = []

            $("#finishOfEdit").click(function () {

                var question = {};
                var writeQuestion = $('#writeQuestionEdit').val();
                var writeFirstAnswer = $('#writeFirstAnswerEdit').val();
                var writeSecondAnswer = $('#writeSecondAnswerEdit').val();
                var writeThirdAnswer = $('#writeThirdAnswerEdit').val();
                var writeFourthAnswer = $('#writeFourthAnswerEdit').val();

                var answers = {};
                answers[writeFirstAnswer] = $('#firstRadioEdit').is(":checked") ? "true" : "false";
                answers[writeSecondAnswer] = $('#secondRadioEdit').is(":checked") ? "true" : "false";
                answers[writeThirdAnswer] = $('#thirdRadioEdit').is(":checked") ? "true" : "false";
                answers[writeFourthAnswer] = $('#fourthRadioEdit').is(":checked") ? "true" : "false";

                question["name"] = writeQuestion;
                question["answers"] = answers;

                questions.push(question);

                if (i < questionsFromBD.length - 1) {
                    i++;
                    $("#indexOfQuestion").text(i + 1);

                    var keys = []
                    var values = []

                    $.each(questionsFromBD[i].answers, function (key, value) {
                        keys.push(key);
                        values.push(value);
                    })

                    setInputs(questionsFromBD[i].name, keys, values)
                } else {
                    editTest(testId, name, subject, description, questions);
                }

            })

        })

    function setInputs(name, keys, values) {
        $('#writeQuestionEdit').val(name);

        $('#writeFirstAnswerEdit').val(keys[0]);
        $('#writeSecondAnswerEdit').val(keys[1]);
        $('#writeThirdAnswerEdit').val(keys[2]);
        $('#writeFourthAnswerEdit').val(keys[3]);

        values[0] ? $('#firstRadioEdit').prop('checked', true) : $('#firstRadioEdit').prop('checked', false);
        values[1] ? $('#secondRadioEdit').prop("checked", true) : $('#secondRadioEdit').prop("checked", false);
        values[2] ? $('#thirdRadioEdit').prop("checked", true) : $('#thirdRadioEdit').prop("checked", false);
        values[3] ? $('#fourthRadioEdit').prop("checked", true) : $('#fourthRadioEdit').prop("checked", false);
    }

    function createQuestionFormForEdit() {
        $("#step-3Edit").append(
            '<div class="col-xs-12">' +
            '<div class="col-md-12">' +
            '<h3> Step 3</h3>' +
            '<small>Change questions</small>' +
            '<div class="ln_solid"></div>' +
            '<div><h2>Question # <span id="indexOfQuestion">1</span> </h2> <br/></div>' +

            '<div class="form-group">' +
            '<textarea maxlength="1000" minlength="3" rows="2" id="writeQuestionEdit" required="required" class="form-control" placeholder="Write some question"></textarea>' +
            '</div>' +

            '<div class="ln_solid"></div>' +

            '<h2>Answers <h2 class="pull-right">Right</h2></h2><br/><br/><br/>' +

            '<div class="form-group">' +

            '<form  class="form-horizontal form-label-left">' +
            '<div class="form-group row">' +
            '<label class="control-label col-md-3" for="writeFirstAnswerEdit">First answer <span class="required">*</span></label>' +
            '<div class="col-md-8">' +
            '<textarea maxlength="300" minlength="1" rows="1" id="writeFirstAnswerEdit" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea>' +
            '</div> <div class="radio pull-right"> <label> <input id="firstRadioEdit" type="radio" class="flat" name="iEditCheck"> </label> </div>' +
            '</div> ' +
            '<div class="form-group row">' +
            '<label class="control-label col-md-3 pull-left" for="writeSecondAnswerEdit">Second answer <span class="required">*</span> </label>' +
            '<div class="col-md-8">' +
            ' <textarea maxlength="300" minlength="3" rows="1" id="writeSecondAnswerEdit" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea>' +
            '</div>' +
            '<div class="radio pull-right"> <label> <input id="secondRadioEdit" type="radio" class="flat" name="iEditCheck"> </label> </div>' +
            '</div>' +
            '<div class="form-group row">' +
            '<label class="control-label col-md-3" for="writeThirdAnswerEdit">Third answer <span class="required">*</span> </label> <div class="col-md-8">' +
            '<textarea maxlength="300" minlength="3" rows="1" id="writeThirdAnswerEdit" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea>' +
            '</div>' +
            '<div class="radio pull-right"> <label> <input id="thirdRadioEdit" type="radio" class="flat" name="iEditCheck"> </label> </div>' +
            '</div>' +
            '<div class="form-group row">' +
            '<label class="control-label col-md-3" for="writeFourthAnswerEdit">Fourth answer <span class="required">*</span> </label> <div class="col-md-8">' +
            ' <textarea maxlength="300" minlength="3" rows="1" id="writeFourthAnswerEdit" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea> </div>' +
            ' <div class="radio pull-right"> <label>  <input id="fourthRadioEdit" type="radio" class="flat" name="iEditCheck"> </label> </div> ' +
            '</div>' +

            '<div class="ln_solid"></div>' +
            ' <button id="finishOfEdit" class="btn btn-success btn-lg pull-right" value="Next"  type="button"><i class="fa fa-save"></i>  Next  </button>' +
            ' </form>' +
            '</div>' +
            '</div>' +

            '</div>'
        )
    }

    function editTest(testId, name, subject, description, questions) {
        if (window.confirm("Do you really want to change test?")) {
            $.ajax({
                url: '/tests/' + testId,
                type: 'PUT',
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
        } else {
            location.reload();
        }
    }

}




