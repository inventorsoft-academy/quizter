$(document).ready(function () {

    $("#btnSubjectCreation").click(function () {
        var name = $("#inputSubject").val();
        createSubject(name);
    });

    getAllSubjectForGroupCreation();

    setTeacherFunc();
});

function getAllSubjects() {
    $('#inputGroupSelect')
        .append($("<option></option>")
            .attr("value", "")
            .text(""));
    $.getJSON("/subjects",
        function (data) {
            $.each(data, function (key, value) {
                $('#inputGroupSelect')
                    .append($("<option></option>")
                        .attr("value", key)
                        .text(value.name));
            });
        });
}

function getAllSubjectForGroupCreation() {
    getAllSubjects();

    var subjectName;

    var count = 1;
    $("#btnShowStudents").click(function () {
        subjectName = $("#inputGroupSelect option:selected").text();

        if (subjectName !== undefined && subjectName !== "") {
            $.getJSON("/admin/students/subject/" + subjectName,
                function (data) {
                    viewStudentsInTable(data, count);
                });
        } else {
            $.getJSON("/admin/students",
                function (data) {
                    viewStudentsInTable(data, count);
                });
        }


        $('#showStudents').on('hide.bs.collapse', function (e) {
            e.preventDefault();
        })
    });

    var students = [];

    var index = 1;

    $("#loadCheckedStudents").click(function () {

        $(".row-select").each(function () {
            if ($("#selectStudent_" + index).is(":checked")) {
                var email = $('#studentEmail_' + index).text().replace(/\s/g, '');
                $.getJSON("/admin/students/" + email,
                    function (data) {
                        students.push(data);
                    }
                );
            }

            index++;
        });
    });

    $("#btnCreateGroup").click(function () {
        if (students.length !== 0) {
            var name = $("#inputGroupName").val();

            createGroup(name, subjectName, students);
        }
    });

}

function viewStudentsInTable(data, count) {
    var studentsInGroupTBodyScript = $('#studentsInGroupTBodyScript').html();

    $.each(data, function (index, value) {
        var student = {
            "student": value,
            "count": count
        };

        $("#studentsInGroupTBody").append(
            Mustache.to_html(studentsInGroupTBodyScript, student),
        );

        count++;
    });

}

function createGroup(name, subjectName, students) {
    if (window.confirm("Do you really want to create a group?")) {
        $.ajax({
            url: '/admin/group-create/',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
                subjectName: subjectName,
                students: students
            }),
            success: function () {
                alert("Group has been successfully created!");
                location.reload();
            },
            error: function (xhr, status, errorThrown) {
                var response = new ErrorResponse(JSON.parse(xhr.responseText));

                if (response.fieldErrors.nameError) {
                    alert(response.fieldErrors.nameError);
                }
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }

}

function createSubject(name) {
    if (window.confirm("Do you really want to create a subject?")) {
        $.ajax({
            url: '/subjects/create',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
            }),
            success: function () {
                alert("Subject has been successfully created!");
                getAllSubjects();
                $("#inputSubject").val("");
            },
            error: function (xhr, status, errorThrown) {
                var response = new ErrorResponse(JSON.parse(xhr.responseText));

                if (response.fieldErrors.nameError) {
                    alert(response.fieldErrors.nameError);
                }
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }

}

function setTeacherFunc() {
    var secondCount = 1;

    $("#btnShowTeacherCreateForm").click(function () {
        var candidatesTBodyScript = $('#candidatesTBodyScript').html();

        $.getJSON("/admin/students",
            function (data) {
                $.each(data, function (index, value) {
                    var student = {
                        "student": value,
                        "secondCount": secondCount
                    };

                    $("#candidatesTBody").append(
                        Mustache.to_html(candidatesTBodyScript, student),
                    );

                    $("a.btnSetTeacher").unbind("click", setTeacher).bind("click", setTeacher);

                    secondCount++;
                });
            });
    });
}

function setTeacher(clickedElement) {
    var email = $(clickedElement.target).attr("data-id");

    if (window.confirm("Are you sure that you want to set Teacher rights to this user?")) {
        $.ajax({
            url: '/admin/teacher-create',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                email: email,
            }),
            success: function () {
                alert("Success!");
                location.reload();
            },
            error: function (xhr, status, errorThrown) {
                var response = new ErrorResponse(JSON.parse(xhr.responseText));

                if (response.fieldErrors.nameError) {
                    alert(response.fieldErrors.nameError);
                }
            },
            processData: false,
            contentType: 'application/json; charset=utf-8;',
            dataType: 'json',
            cache: false,
            timeout: 1000000,
        });
    }

}
