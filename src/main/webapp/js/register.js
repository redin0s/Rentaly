$(document).ready(
    function() {

        // POST REQUEST
        $("#doRegister").click(function(event) {
            event.preventDefault();
            ajaxLoginPost();
        });

        function ajaxLoginPost() {
			var userData = {
				username : $("#u").val(),
				password : $("#p").val()
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
                        console.log("User " + userData.username + " successfully registered.");
                        console.log("Logging in...");
                        window.location.href = "/account";
                    }
                    else {
                        console.log("Login error " + data + ".");
                        var msg;
                        switch (data) {
                            case "missingusername":
                                msg = "Insert an email.";
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
