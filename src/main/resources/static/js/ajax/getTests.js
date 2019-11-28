GET: $(document).ready(
    function ajaxGet() {
        $.getJSON("/testWizard/getAllTests",
            function (data) {
                var successButton = '<td><button type="button" class="btn btn-success btn-xs">Success</button></td>';

                var editButtons = '<td> <a href="#" class="btn btn-primary btn-xs"><i class="fa fa-folder"></i> View </a>' +
                    '<a href="#" class="btn btn-info btn-xs"><i class="fa fa-pencil"></i> Edit </a>' +
                    ' <a href="#" class="btn btn-danger btn-xs deleteTest" data-id="${test.id}" ><i class="fa fa-trash-o"></i> Delete </a></td>';

                $.each(data, function (index, test) {
                    $("#tBody").append(
                        "<tr>" +
                        "<td>" + test.id + "</td>" +
                        "<td>" +
                        "<a>" + test.subject + "</a>" +
                        "</td>" +
                        "<td>" +
                        "<a>" + test.name + "</a>" +
                        "</td>"
                        + successButton + editButtons +
                        "</tr>"
                    );
                });

            });


    })



