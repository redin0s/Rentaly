$(document).ready(
    function() {

        // POST REQUEST
        $("#login").on("submit", function(event) {
            event.preventDefault();
            console.log("logging in");
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
				url : "doLogin",
				data : JSON.stringify(userData),
				dataType : 'json',
				success : function (data, status, xhr) {
					if (data == "success") {
                        console.log("User " + userData.email + " successfully logged in.");
                        window.location.href = "/account";
                    }
                    else {
                        console.log("Login error " + data + ".");
                        var msg;
                        switch (data) {
                            case "missingemail":
                                msg = "Insert an email.";
                                break;
                            case "missingpassword":
                                msg = "Insert a password.";
                                break;
                            case "notexisting":
                                msg = "This account does not exist.";
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
