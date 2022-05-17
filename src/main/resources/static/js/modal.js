$("#editUser").on('show.bs.modal', function(e) {
    var userId = $(e.relatedTarget).data('id');

    var cols = $('#' + userId + ' td');
    var userName = $(cols[0]).text();
    var firstName = $(cols[1]).text();
    var lastName = $(cols[2]).text();
    var age = $(cols[3]).text();

    $('#username').val(userName);
    $('#firstName').val(firstName);
    $('#lastName').val(lastName);
    $('#age').val(age);
});

$("#editUser").on('hidden.bs.modal', function() {
    var form = $(this).find('form');
    form[0].reset();
});