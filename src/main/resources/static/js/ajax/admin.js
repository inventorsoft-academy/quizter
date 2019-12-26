$(document).ready(function () {
    $("#btnSubjectCreation").click(function () {
        var name = $("#inputSubject").val();
        createSubject(name);
    });

    getAllSubjectForGroupCreation();

});

var students = [];

function getAllSubjectForGroupCreation() {

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

    var subjectName;

    var count = 1;
    $("#btnShowStudents").click(function () {
        subjectName = $("#inputGroupSelect option:selected").text();

        if (subjectName !== undefined) {
            $.getJSON("/admin/students?" + subjectName,
                function (data) {
                    viewStudentsInTable(data, count);
                });
        } else {
            $.getJSON("/admin/students",
                function (data) {
                    viewStudentsInTable(data, count);
                });
        }
    });

    $("#btnCreateGroup").click(function () {
        var name = $("#inputGroupName").val();

        var index;

        $('#studentsInGroupTBody').each(function (i) {
            index = i + 1;
            console.log("Hey");

            var isSelected = $("#selectStudent_" + index).is(":checked") ? "true" : "false";

            if (isSelected) {
                console.log("Hey@@@@");
                var v = $('#studentEmail_' + index).val();
                console.log(v + "SS");
                $.getJSON("/admin/students/" + v,
                    function (data) {
                        console.log(data.email + data.profile)
                        students.push(data)
                    }
                );
            }

            index++;
        });

        createGroup(name, subjectName, students);
    });

}

function viewStudentsInTable(data, count) {
    var studentsInGroupTBodyScript = $('#studentsInGroupTBodyScript').html();

    // $("#studentsInGroupTBody").html("");

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
