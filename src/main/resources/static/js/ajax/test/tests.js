$(document).ready(function () {
    getAllTests()

    $('#delete').click(function (event) {
        event.preventDefault();
        deleteTest();
    });

});

function getAllTests() {
    $.getJSON("/cabinet/rest-tests/",
        function (data) {
            var tBodyScript = $('#tBodyScript').html();
            $.each(data, function (index, test) {
                console.log(data);
                console.log(test);
                $("#tBody").append(
                    Mustache.to_html(tBodyScript, test)
                );

                $("a.delete").unbind("click", deleteTest).bind("click", deleteTest);
                $("a.view").unbind("click", viewTest).bind("click", viewTest);
                $("a.edit").unbind("click", editTest).bind("click", editTest);
                $("a.btnInviteStudentsInTestTable").unbind("click", inviteStudentInTestTable).bind("click", inviteStudentInTestTable);
            });
        }
    );
}

function deleteTest(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");

    if (window.confirm("Do you really want to delete this test?")) {
        $.ajax({
            url: "/cabinet/tests/" + dataId,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            success: function () {
                alert("Test has been successfully deleted!");
                location.reload(true);
            },
        })
    }
}

function viewTest(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "GET",
        url: '/cabinet/test-view',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/cabinet/test-view/" + dataId
        }
    })
}

function editTest(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "GET",
        url: '/cabinet/test-edit',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/cabinet/test-edit/" + dataId
        }
    })
}

function inviteStudentInTestTable(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");

    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "GET",
        url: '/cabinet/tests/student-groups',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/cabinet/tests/student-groups/" + dataId
        }
    })
}
