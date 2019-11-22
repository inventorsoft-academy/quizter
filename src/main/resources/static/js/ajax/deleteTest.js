$(document).ready(


$("tBody").on("click", ".deleteTest", function(e) {
    var id = $(e.target).attr("data-id");
    $.ajax({
        url: "/testWizard/deleteTest/" + id,
        type: 'DELETE',
        success: function (){
            alert('This test has been deleted');
        }
    });
})
)