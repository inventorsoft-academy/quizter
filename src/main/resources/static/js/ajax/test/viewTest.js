$(document).ready(function () {
    getTest()
})

function getTest() {
    var dataId = $("#viewPage").attr("data_id");

    $.getJSON("/tests/" + dataId,
        function (data) {
            $("#testName").append(
                '<h2>' + data.name + '<h2>'
            );
            $("#testSubject").append(
                data.subject
            );
            $("#testDescription").append(
                data.description
            );

            let questionIndex = 1;

            $.each(data.questions, function (index, question) {
                var keys = [];
                $.each(question.answers, function (key, value) {
                    keys.push(key)
                    console.log(value)
                })
                console.log("")

                $("#getQuestions").append(
                    '<li>' +
                    '<div class="block">' +
                    '<div  class="block_content">' +
                    '<h2 id="getQuestion" class="title">' +
                    questionIndex + " - " + '<a> ' + question.name + '</a>' +
                    '</h2>' +
                    '<p class="excerpt">' +
                    '<div id="getAnswers" class="form-group row">' +
                    '<div class="checkbox"><label class="col-md-3 col-sm-3  control-label">' +
                    "- " + keys[0] +
                    '</label>' +
                    '</div>' +
                    '<br>' +
                    '<div class="checkbox"><label class="col-md-3 col-sm-3  control-label">' +
                    "- " + keys[1] +
                    '</label>' +
                    '</div>' +
                    '<br>' +
                    '<div class="checkbox"><label class="col-md-3 col-sm-3  control-label">' +
                    "- " + keys[2] +
                    '</label>' +
                    '</div>' +
                    '<br>' +
                    '<div class="checkbox"><label class="col-md-3 col-sm-3  control-label">' +
                    "- " + keys[3] +
                    '</label>' +
                    '</div>' +
                    '<br>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</li>'
                ),
                    questionIndex++;
            })
        }
    );
}


