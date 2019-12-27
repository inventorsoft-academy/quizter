$(document).ready(function () {

    getAllStudentsFromGroup()

});

function getAllStudentsFromGroup() {

    $.getJSON("/admin/tests/groups",
        function (data) {
            var tBodyGroupScript = $('#tBodyGroupScript').html();
            var count = 1;
            $.each(data, function (index, group) {
                var data = {
                    "id": group.id,
                    "name": group.name,
                    "countOfMembers": group.students.length,
                    "count": count
                };

                $("#tBodyGroup").append(
                    Mustache.to_html(tBodyGroupScript, data)
                );

                $("#inviteStudents_" + count).click(function () {
                    var students = group.students;

                    inviteStudentsToTest(students);
                });


                $("a.viewStudents").unbind("click", viewStudentsFromGroup).bind("click", viewStudentsFromGroup);

                count++
            });

        });
}

function viewStudentsFromGroup(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");

    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "GET",
        url: '/admin/tests/student-groups/students',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/admin/tests/student-groups/" + dataId + "/students"
        }
    })
}

function inviteStudentsToTest(students) {
    var testId = $("#getAllGroupsDiv").attr("dataTest_id");

    if (window.confirm("Open access to pass group?")) {
        $.ajax({
            url: '/admin/tests/invite-group',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                testId: testId,
                students: students
            }),
            success: function () {
                alert("The group has got access to the test");
                //  $("a.inviteStudents").disable();
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }

}


