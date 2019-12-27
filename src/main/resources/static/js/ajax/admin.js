$(document).ready(function () {

    $("#btnSubjectCreation").click(function () {
        var name = $("#inputSubject").val();
        createSubject(name);
    });

    getAllSubjectForGroupCreation();

});

function getAllSubjects() {
    $.getJSON("/admin/subjects",
        function (data) {
            $('#inputGroupSelect')
                .append($("<option></option>")
                    .attr("value", "")
                    .text(""));
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

    $("#btnShowStudents").click(function () {


        $(".row-select").each(function () {
            console.log("ХУЙ");
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
        console.log(students);

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
            "quantity": count,
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
    if (window.confirm("Do you really want to create group?")) {
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
    if (window.confirm("Do you really want to create group?")) {
        $.ajax({
            url: '/admin/subject/create',
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