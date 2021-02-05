$(document).ready(
    function() {

        // POST REQUEST
        $("#register-form").on("submit", function(event) {
            event.preventDefault();
            console.log("registering");
            ajaxRegisterPost();
        });

        function ajaxRegisterPost() {
            // var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

			var userData = {
				email : $("#email").val(),
                password : $("#password").val()
                // email: document.getElementById("email").value(),
                // password: document.getElementById("password").value()
			}
        
        	// DO POST
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "register",
                data : JSON.stringify(userData),
                headers: headers,
				// dataType : 'json',
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
                            msg = "Insert a valid email.";
                            break;
                        case "missingpassword":
                            msg = "Insert a password.";
                            break;
                        case "existing":
                            msg = "This account already exists.";
                            break;
                        default:
                            msg = "Server error. Please try again.";
                            break;
                    }
                    $("#errorMessage").html(msg);

                }
			});
        }
    })
