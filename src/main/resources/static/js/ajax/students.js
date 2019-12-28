$(document).ready(function () {
    getStudentsFromGroup()

    $("#btnBackToGroups").click(function () {
        location.href = "/cabinet/tests"
    })
});

function getStudentsFromGroup() {
    var dataId = $(".getStudentsFromGroupDiv").attr("data_id");
    $.getJSON("/admin/tests/groups/" + dataId + "/students-from-group",
        function (data) {
            var studentsTBodyScript = $('#studentsTBodyScript').html();
            let i = 1;

            $.each(data, function (index, value) {
                var student = {
                    "quantity": i,
                    "student": value
                };

                $("#studentsTBody").append(
                    Mustache.to_html(studentsTBodyScript, student)
                );

                i++;
            });

        }
    )
}


