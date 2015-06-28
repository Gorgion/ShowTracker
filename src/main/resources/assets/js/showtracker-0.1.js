//$('#confirm-delete').on('show.bs.modal', function(e) {
//            $(this).find('.danger').attr('href', $(e.relatedTarget).data('href'));                        
//        })

$('button[name = "delete"]').on('click', function (e) {
    var $form = $(this).closest('form');
    e.preventDefault();
    $('#confirm-delete').modal({backdrop: 'static'})
            .one('click', '#confirm-btn', function (e) {
                $form.trigger('submit');
            });
});

function episodeAutoReset() {
    $("#series").bind("change paste keyup", function () {
        var episode = 1;
        $("#episode").val(episode);
        $("#finale").attr("checked",false);
    });
}