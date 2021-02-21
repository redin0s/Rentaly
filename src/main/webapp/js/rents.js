$(document).ready(function () {
    $('.it-date-datepicker').datepicker({
        dateFormat: 'dd/mm/yy'
    });

    $('#deleteRent').on('click', function () {
        ajaxDeleteRent();
    });
    $('#saveCheck').on('click', function () {
        ajaxAddCheck();
    });
});

function ajaxDeleteRent() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};

    headers[csrfHeader] = csrfToken;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/removeRent",
        data: ({ "selected": window.selected }),
        headers: headers,
        success: function (data, status, xhr) {
            console.log(data);
            if (data == "success") {
                console.log("Successfully removed rent.");
                location.reload();
            }
            else {
                console.log("Error");
            }
        }
    });
}

function ajaxAddCheck() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};

    headers[csrfHeader] = csrfToken;

    var postData = {
        rent : window.selected.toString(),
        check_type : $("#check-type").val(),
        check_cost : $("#check-cost").val(),
        paid : $("#check-paid").val(),
        expire : $("#check-end_date").val()
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/createCheck",
        data: JSON.stringify(postData),
        headers: headers,
        success: function (data, status, xhr) {
            console.log(data);
            if (data == "success") {
                console.log("Successfully added check.");
                window.location.reload();
            }
            else {
                console.log("Error");
            }
        }
    });
}

function setSelected(id) {
    window.selected = id;
    console.log(window.selected);
}