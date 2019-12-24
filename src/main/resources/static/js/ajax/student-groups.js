$(document).ready(function () {

    getAllStudentsFromGroup()

});

function getAllStudentsFromGroup() {

    $.getJSON("/cabinet/tests/groups",
        function (data) {
            var tBodyGroupScript = $('#tBodyGroupScript').html();

            $.each(data, function (index, group) {
                var data = {
                    "id": group.id,
                    "name": group.name,
                    "countOfMembers": group.students.length
                };
                $("#tBodyGroup").append(
                    Mustache.to_html(tBodyGroupScript, data)
                );

                $("a.inviteStudents").click(function () {
                    var students = group.students;

                    inviteStudentsToTest(students);
                });

                $("a.viewStudents").unbind("click", viewStudentsFromGroup).bind("click", viewStudentsFromGroup);
            });

        });
}

function viewStudentsFromGroup(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");

    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "GET",
        url: '/cabinet/tests/student-groups/students',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/cabinet/tests/student-groups/" + dataId + "/students"
        }
    })
}

function inviteStudentsToTest(students) {
  //  var testId = $(".getAllGroupsDiv").attr("data-id");
   // console.log(testId + "!!!!!");

    if (window.confirm("Invite this group?")) {
        $.ajax({
            url: '/cabinet/rest-tests/invite-group',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                testId: 1,
                students: students
            }),
            success: function () {
                alert("The group has got access to the test");
                $("a.inviteStudents").disable();
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }

}


