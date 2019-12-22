(document).ready(function () {
    window.onload = function () {
        getAllTests()
    };

});

function getAllTests() {
    $.getJSON("/cabinet/tests/groups/",
        function (data) {
            var tBodyGroupScript = $('#tBodyGroupScript').html();
            $.each(data, function (index, group) {
                var data = {
                    "name": group.name,
                    "countOfMembers": group.students.length
                };
                $("#tBodyGroup").append(
                    Mustache.to_html(tBodyGroupScript, data)
                );

            });
        }
    );
}