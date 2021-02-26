$(document).ready(function () {

    $("#content").on("click", '#paybutton', function (event) {
        event.preventDefault();
        ajaxPayCheck($('#owner-check').val() == 1);
    });
});

function ajaxPayCheck(isOwner) {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};

    headers[csrfHeader] = csrfToken;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/account/payCheck",
        data: JSON.stringify({ "selected": window.selected }),
        headers: headers,
        success: function (data, status, xhr) {
            console.log("Successfully paid check.");
            if(isOwner) {
                ajaxGetRealtiesChecks(true);
            }
            else {
                ajaxGetRentsChecks(true);
            }
        }
    });
}

function setSelected(id) {
    window.selected = id;
    console.log(window.selected);
}