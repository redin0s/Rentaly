$(document).ready(function () {

    $('#paybutton').on('click', function (e) {
        ajaxPayCheck($('#owner-check').val() == 1);
        //aggiungi 
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
            console.log(data);
            if (data == "success") {
                console.log("Successfully paid check.");
                if(isOwner) {
                    ajaxGetRealtiesChecks(true);
                }
                else {
                    ajaxGetRentsChecks(true);
                }
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