$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $('#overlay').toggleClass('active');
    });

    $('#dismiss, #overlay').on('click', function () {
        $('#overlay').removeClass('active');
        $('#sidebar').removeClass('active');
    });

    $('.inactive').on('click', function () {
        $('.active').attr("class", "inactive");
        $(this).attr("class", "active");
    });

    $('#confirm-email').on('click', function () {
        ajaxSendNewConfirmationEmail();
    });

    $('#content').on('DOMSubtreeModified', function() {
        init();
    });
    
    ajaxGetAccountData();
});

function init() {

    $('#account').on('click', function () {
        ajaxGetAccountData();
    });

    $('#changeEmail').on('click', function () {
        ajaxGetChangeEmail();
    });

    $('#changePassword').on('click', function () {
        ajaxGetChangePassword();
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

}

function ajaxSendNewConfirmationEmail() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};

    headers[csrfHeader] = csrfToken;
    $.ajax({
        type: "POST",
        url: "/account/sendNewConfirmationEmail",
        headers: headers
    });
}

function ajaxGetAccountData() {
    $.ajax({
        type: "GET",
        url: "/account/data",
        headers: {"AJAX": true},
        success : function(data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetChangeEmail() {
    $.ajax({
        type: "GET",
        url: "/account/changeEmail",
        headers: {"AJAX": true},
        success : function(data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetChangePassword() {
    $.ajax({
        type: "GET",
        url: "/account/changePassword",
        headers: {"AJAX": true},
        success : function(data) {
            $('#content').html(data);
        }
    });
}

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