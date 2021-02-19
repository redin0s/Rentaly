$(document).ready(
    function() {
        $("#change-email-form").on("submit", function(event) {
            event.preventDefault();
            if($("#email").val() === $("#confirm-email").val()) {
                $("#err").html("");
                console.log("changing email");
                ajaxChangeEmail();
            }
            else {
                $("#err").html("Le due email non corrispondono.");
            }
        });

        function ajaxChangeEmail() {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

            $.ajax({
				type : "POST",
				url : "/account/changeEmail",
				contentType : "application/json; charset=utf-8",
                data : JSON.stringify({"newemail" : $("#email").val()}),
                headers : headers,
				success : function (data, status, xhr) {
                    console.log("Email change request successfully sent.");
                    window.location.href = "/account";
                }
			});
        }
        
});