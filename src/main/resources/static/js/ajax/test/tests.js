$(document).ready(function () {
    window.onload = function () {
        getAllTests()
    };

    $('#delete').click(function (event) {
        event.preventDefault();
        deleteTest();
    });

});

function getAllTests() {
    $.getJSON("/tests/",
        function (data) {
         var tBodyScript = $('#tBodyScript').html();
            $.each(data, function (index, test) {
                $("#tBody").append(
                    Mustache.render(tBodyScript, test)
                );

                $("a.delete").unbind("click", deleteTest).bind("click", deleteTest);
                $("a.view").unbind("click", viewTest).bind("click", viewTest);
                $("a.edit").unbind("click", editTest).bind("click", editTest);
            });
        }
    );
}

function deleteTest(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");

    if (window.confirm("Do you really want to delete this test?")) {
        $.ajax({
            url: "/tests/" + dataId,
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
        url: '/test-view',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/test-view/" + dataId
        }
    })
}

function editTest(clickedElement) {
    var dataId = $(clickedElement.target).attr("data-id");
    $.ajax({
        contentType: "application/json; charset=utf-8",
        type: "GET",
        url: '/test-edit',
        data: JSON.stringify(dataId),
        success: function () {
            location.href = "/test-edit/" + dataId
        }
    })
}