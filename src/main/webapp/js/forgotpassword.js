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
            $.ajax({
				type : "POST",
				url : "forgotPassword",
                data : $('#email').val(),
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