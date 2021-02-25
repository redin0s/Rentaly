$(document).ready(function () {
    $('.it-date-datepicker').datepicker({
        dateFormat: 'dd/mm/yy'
    });

    $('#terminateRent').on('click', function () {
        $("#deleteConfirm").unbind();
        $("#deleteConfirm").on("click", function (event) {
            ajaxDoTerminateRent();
        });
        $('#deleteModal').modal('show');
    });
    $('#saveCheck').on('click', function () {
        ajaxAddCheck();
    });
});

function ajaxDoTerminateRent() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};

    headers[csrfHeader] = csrfToken;
    $.ajax({
        type: "POST",
        url: "/account/doTerminateRent",
        data: { "id": window.selected },
        headers: headers,
        success: function (data, status, xhr) {
            $("#okModalContent").html("Affitto terminato con successo.");
            $("#okModal").modal('show');
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
            console.log("Successfully added check.");

            $("#check-type").val("Luce");
            $("#check-cost").val("");
            $("#check-end_date").val("");

            ajaxGetRealtiesChecks($("#check-paid").val() == "Si")
            
            $('.active').attr("class", "inactive");

            if($("#check-paid").val() == "Si")
                $('#own-checks-r').parent().attr("class", "active");
            else
                $('#own-checks-notr').parent().attr("class", "active");

            $("#check-paid").val("No");

        }
    });
}

function setSelected(id) {
    window.selected = id;
    console.log(window.selected);
}