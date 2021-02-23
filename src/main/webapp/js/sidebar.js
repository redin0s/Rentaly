$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
        $('#overlay').toggleClass('active');
    });

    $('#dismiss, #overlay').on('click', function () {
        $('#overlay').removeClass('active');
        $('#sidebar').removeClass('active');
    });

    $('#problemSubmit').click(function (e) {
        e.preventDefault();
        $('#problemsModal').text($('#segnalation-form').serialize());
        $('#problemsModal').modal('hide');
        /*
        $.post('http://path/to/post', 
           $('#myForm').serialize(), 
           function(data, status, xhr){
             // do something here with response;
           });
        */
    });
    
    $('.inactive').on('click', function () {
        $('.active').attr("class", "inactive");
        $(this).attr("class", "active");
    });

    $('#body').on('click', '#confirm-email', function () { 
        ajaxSendNewConfirmationEmail();
    });

    $('#sidebar').on('click' , '#account', function () {
        ajaxGetAccountData();
    });

    $('#content').on('click', "#changeEmail", function () {
        ajaxGetChangeEmail();
    });

    $('#content').on('click', "#changePassword", function () {
        ajaxGetChangePassword();
    });

    $('#sidebar, #content').on('click', '#realties', function () {
       ajaxGetRealties(false);
    });

    $('#sidebar').on('click', '#drafts', function () {
        ajaxGetRealties(true);
    });

    $('#sidebar').on('click', '#own-ongoing', function () {
        ajaxGetRealtiesRents(false);
    });

    $('#sidebar').on('click', '#own-ended', function () {
        ajaxGetRealtiesRents(true);
    });

    $('#sidebar').on('click', '#own-checks-r', function () {
        ajaxGetRealtiesChecks(true);
    });

    $('#sidebar').on('click', '#own-checks-notr', function () {
        ajaxGetRealtiesChecks(false);
    });

    $('#sidebar').on('click', '#rents-ongoing', function () {
        ajaxGetRents(false);
    });

    $('#sidebar').on('click', '#rents-ended', function () {
        ajaxGetRents(true);
    });

    $('#sidebar').on('click', '#rents-checks-r', function () {
        ajaxGetRentsChecks(true);
    });

    $('#sidebar').on('click', '#rents-checks-notr', function () {
        ajaxGetRentsChecks(false);
    });

    $('#sidebar').on('click', '#saved-searches', function () {
        ajaxGetSavedSearches();
    });

    ajaxGetAccountData();
});

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

function ajaxGetSavedSearches() {
    $.ajax({
        type: "GET",
        url: "/account/savedsearches",
        headers: {"AJAX": true},
        success : function(data) {
            $('#content').html(data);
        }
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
        headers: {"AJAX": true},
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
        headers: {"AJAX": true},
        success: function (data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetRealtiesChecks(paid) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/account/realties-checks",
        data: ({"isPaid" : paid}),
        headers: {"AJAX": true},
        success: function (data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetRents(ended) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/account/rents",
        data: ({"isEnded" : ended}),
        headers: {"AJAX": true},
        success: function (data) {
            $('#content').html(data);
        }
    });
}

function ajaxGetRentsChecks(paid) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/account/rents-checks",
        data: ({"isPaid" : paid}),
        headers: {"AJAX": true},
        success: function (data) {
            $('#content').html(data);
        }
    });
}