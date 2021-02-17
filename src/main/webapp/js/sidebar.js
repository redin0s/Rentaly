$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $('#overlay').toggleClass('active');
    });

    $('#dismiss, #overlay').on('click', function () {
        $('#overlay').removeClass('active');
        $('#sidebar').removeClass('active');
    });

    $('#realties').on('click', function () {
       ajaxGetRealties(false);
    });

    $('#drafts').on('click', function () {
        ajaxGetRealties(true);
    });

    $('#own-ongoing').on('click', function () {
        ajaxGetRealtiesRents(false);
    });

    $('#own-ended').on('click', function () {
        ajaxGetRealtiesRents(true);
    });

    $('#own-expenses').on('click', function () {
        ajaxGetRealtiesChecks();
    });
});

function ajaxGetRealties(draft) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/account/realties",
        data: ({"isDraft" : draft}),
        success: function (data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetRealtiesRents(ended) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/account/realties-rents",
        data: ({"isEnded" : ended}),
        success: function (data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetRealtiesChecks() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/account/realties-checks",
        data: ({}),
        success: function (data) {
            $('#content').html(data);
        }
    });
}