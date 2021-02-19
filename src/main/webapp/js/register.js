$(document).ready(
    function() {

        // POST REQUEST
        $("#register-form").on("submit", function(event) {
            event.preventDefault();
            console.log("registering");
            ajaxRegisterPost();
        });

        function ajaxRegisterPost() {
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

			var userData = {
				email : $("#email").val(),
                password : $("#password").val()
			}
        
        	// DO POST
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "register",
                data : JSON.stringify(userData),
                headers: headers,
				success : function (data, status, xhr) {
					if (data == "success") {
                        console.log("User " + userData.email + " successfully registered.");
                        console.log("Logging in...");
                        window.location.href = "/account";
                    }
                },
                error : function (data, status, xhr) {
                    console.log("Login error.");
                    console.log(data);
                    var msg;
                    switch (data) {
                        case "invalidemail":
                            msg = "Inserisci una email valida.";
                            break;
                        case "missingpassword":
                            msg = "Inserisci una password.";
                            break;
                        case "existing":
                            msg = "Account già esistente.";
                            break;
                        default:
                            msg = "Errore interno, riprova tra poco.";
                            break;
                    }
                    $("#errorMessage").html(msg);

                }
			});
        }
    })
