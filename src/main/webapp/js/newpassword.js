$(document).ready(
    function() {
        $("#new-password-form").on("submit", function(event) {
            event.preventDefault();
            if($("#new-password").val() === $("#confirm-password").val()) {
                $("#err").html("");
                console.log("changing password");
                ajaxChangePassword();
            }
            else {
                $("#err").html("Le due password non corrispondono.");
            }
        });

        function getData() {
            return {
                email : $("#email").val(),
                newPassword : $("#new-password").val()
            };
        }

        function ajaxChangePassword() {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;
            
            $.ajax({
				type : "POST",
				url : "/newPassword",
				contentType : "application/json; charset=utf-8",
                data : JSON.stringify(getData()),
                headers : headers,
				success : function (data, status, xhr) {
                    console.log("Password successfully changed.");
                    window.location.href = "/login";
                },
                error : function (data, status, xhr) {
                    $("#err").html("Hai inserito l'indirizzo email sbagliato.");
                }
			});
        }
        
});