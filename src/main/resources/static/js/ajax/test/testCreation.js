$(document).ready(function () {
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

    createQuestionForm();
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
    $("#step-3").append(
        '<div class="col-xs-12">' +
        '<div class="col-md-12">' +
        '<h3> Step 3</h3>' +
        '<small>Create questions</small>' +
        '<div class="ln_solid"></div>' +
        '<div><h2>Question # <span id="indexOfQuestion">1</span> </h2> <br/></div>' +

        '<div class="form-group">' +
        '<textarea maxlength="1000" minlength="3" rows="2" id="writeQuestion" required="required" class="form-control" placeholder="Write some question"></textarea>' +
        '</div>' +

        '<div class="ln_solid"></div>' +

        '<h2>Answers <h2 class="pull-right">Right</h2></h2><br/><br/><br/>' +

        '<div class="form-group">' +

        '<form  id="addQuestion" class="form-horizontal form-label-left">' +
        '<div class="form-group row">' +
        '<label class="control-label col-md-3" for="writeFirstAnswer">First answer <span class="required">*</span></label>' +
        '<div class="col-md-8">' +
        '<textarea maxlength="300" minlength="1" rows="1" id="writeFirstAnswer" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea>' +
        '</div> <div class="radio pull-right"> <label> <input id="firstRadio" type="radio" class="flat" name="iCheck"> </label> </div>' +
        '</div> ' +
        '<div class="form-group row">' +
        '<label class="control-label col-md-3 pull-left" for="writeSecondAnswer">Second answer <span class="required">*</span> </label>' +
        '<div class="col-md-8">' +
        ' <textarea maxlength="300" minlength="3" rows="1" id="writeSecondAnswer" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea>' +
        '</div>' +
        '<div class="radio pull-right"> <label> <input id="secondRadio" type="radio" class="flat" name="iCheck"> </label> </div>' +
        '</div>' +
        '<div class="form-group row">' +
        '<label class="control-label col-md-3" for="writeThirdAnswer">Third answer <span class="required">*</span> </label> <div class="col-md-8">' +
        '<textarea maxlength="300" minlength="3" rows="1" id="writeThirdAnswer" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea>' +
        '</div>' +
        '<div class="radio pull-right"> <label> <input id="thirdRadio" type="radio" class="flat" name="iCheck"> </label> </div>' +
        '</div>' +
        '<div class="form-group row">' +
        '<label class="control-label col-md-3" for="writeFourthAnswer">Fourth answer <span class="required">*</span> </label> <div class="col-md-8">' +
        ' <textarea maxlength="300" minlength="3" rows="1" id="writeFourthAnswer" required="required" class="form-control col-md-8" placeholder="Write option of answer"></textarea> </div>' +
        ' <div class="radio pull-right"> <label>  <input id="fourthRadio" type="radio" class="flat" name="iCheck"> </label> </div> ' +
        '</div>' +
        ' <button id="finishOfCreation" class="btn btn-success btn-lg pull-right" value="Next"  type="button"><i class="fa fa-save"></i>  Save  </button>' +
        ' </form>' +
        '</div>' +
        '</div>' +

        '</div>'
    )
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
                $("#next-button").removeAttr('hidden');
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }
}




