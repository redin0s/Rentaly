$(document).ready(
    function() {

        // POST REQUEST
        $("#forgot-form").on("submit", function(event) {
            $('#err').html("");
            event.preventDefault();
            console.log("sending request");
            ajaxForgotPost();
        });

        function ajaxForgotPost() {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;
            
            $.ajax({
				type : "POST",
				url : "/forgotPassword",
				contentType : "application/json; charset=utf-8",
                data : JSON.stringify({
                    email :$('#email').val()}),
                headers : headers,
				success : function () {
                    console.log("done");
                    window.location.href = "/";
                },
                error : function () {
                    $('#err').html(
                        '<div class="alert alert-danger alert-dismissible fade show" role="alert">\
                        <p id="error">Questo account non esiste! <a href="/register">Vuoi registrarti?</a> </p>\
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>\
                        </div>');
                }
            });
        }

    });