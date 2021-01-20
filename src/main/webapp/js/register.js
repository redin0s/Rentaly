$(document).ready(
    function() {

        // POST REQUEST
        $("#register").on("submit", function(event) {
            event.preventDefault();
            console.log("registering");
            ajaxLoginPost();
        });

        function ajaxLoginPost() {
			var userData = {
				email : $("#email").val(),
				password : $("#password").val()
			}
        
        	// DO POST
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "doRegister",
				data : JSON.stringify(userData),
				dataType : 'json',
				success : function (data, status, xhr) {
					if (data == "success") {
                        console.log("User " + userData.email + " successfully registered.");
                        console.log("Logging in...");
                        window.location.href = "/account";
                    }
                    else {
                        console.log("Login error " + data + ".");
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
				}
			});
        }
    })
