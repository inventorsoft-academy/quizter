$(document).ready(function () {
    $("#btnSubjectCreation").click(function () {
        var name = $("#inputSubject").val();
        createSubject(name);
    });

    getAllSubjectForGroupCreation();

});

function getAllSubjectForGroupCreation() {
    $.getJSON("/admin/subjects",
        function (data) {
            $.each(data.name, function (key, value) {
                $('#inputGroupSelect')
                    .append($("<option></option>")
                        .attr("value", key)
                        .text(value));
            });
        });

    $("#btnCreateGroup").click(function () {
        var name = $("#inputGroupName");

        var subject = $("#inputGroupSelect option:selected").text();

        createGroup(name, subject);
    });


};


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

function createGroup(name, subject) {
    if (window.confirm("Do you really want to create group?")) {
        $.ajax({
            url: '/admin/group-create/',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
                subject: subject
            }),
            success: function () {
                alert("Group has been successfully created!");
                $("#inputGroupSelect").val("");
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
