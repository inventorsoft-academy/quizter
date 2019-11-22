$(document).ready(function(){
    $('#createTest').click(function(event){
        event.preventDefault();
        ajaxPost();
    });

});
function ajaxPost(){
    $.ajax({
        url: '/testWizard/createTest',
        type: 'POST',
        enctype: 'multipart/form-data',
        data: JSON.stringify({name : $('#name').val(),
            subject : $('#subject').val(),
            description : $('#description').val(),
            questions : [
                {
                    name: "sample",
                    answers: {
                        Answer: true
                    }
                }
            ]
        }),
        processData: false,
        contentType: 'application/json; charset=utf-8;',
        dataType: 'json',
        cache: false,
        timeout: 1000000,
    });
}
