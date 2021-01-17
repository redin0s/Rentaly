$(document).ready(
    function() {

        // POST REQUEST
        $("#doLogin").click(function(event) {
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
				url : "doLogin",
				data : JSON.stringify(userData),
				dataType : 'json',
				success : function (data, status, xhr) {
					if (data == "success") {
                        console.log("User " + userData.username + " successfully logged in.");
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
