$(document).ready(function () {
    $("#btnCreateGroup").click(function () {
        var name = $("#inputGroupSelect option:selected").text();
        createGroup(name);
    });
});

function createGroup(name) {
    if (window.confirm("Do you really want to create group?")) {
        $.ajax({
            url: '/admin/group-create/',
            type: 'POST',
            enctype: 'multipart/form-data',
            data: JSON.stringify({
                name: name,
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
