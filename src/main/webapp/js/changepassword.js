$(document).ready(
    function() {
        $("#change-password-form").on("submit", function(event) {
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

        function getPasswords() {
            return {
                oldPassword : $("#old-password").val(),
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
				url : "/account/changePassword",
				contentType : "application/json; charset=utf-8",
                data : JSON.stringify(getPasswords()),
                headers : headers,
				success : function (data, status, xhr) {
                    console.log("Password successfully changed.");
                    window.location.href = "/account";
                },
                error : function (data, status, xhr) {
                    $("#err").html("Hai inserito la password sbagliata.");
                }
			});
        }
        
});