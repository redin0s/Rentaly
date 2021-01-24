$(document).ready(
    function() {

        // POST REQUEST
        // $("#login").on("submit", function(event) {
        //     event.preventDefault();
        //     console.log("logging in");
        //     ajaxLoginPost();
        // });

        function ajaxLoginPost() {
			var userData = {
                //id : 0,
				email : $("#email").val(),
				password : $("#password").val()
			}
		
        	// DO POST
			$.ajax({
				type : "POST",
				contentType : "application/json",
                url : "login",
				data : JSON.stringify(userData),
                dataType : 'json',
                // crossDomain: true,
				success : function (data, status, xhr) {
                    console.log(data);
					if (data.body == "success") {
                        console.log("User " + userData.email + " successfully logged in.");
                        window.location.href = "/account";
                    }
                },
                error : function (data, status, xhr) {
                    console.log("Login error " + data.body + ".");
                    console.log(data);
                    var msg;
                    switch (data.body) {
                        case "missingemail":
                            msg = "Insert a valid email.";
                            break;
                        case "missingpassword":
                            msg = "Insert a valid password.";
                            break;
                        case "notexisting":
                            msg = "This account does not exist.";
                            break;
                        default:
                            msg = "Server error. Please try again.";
                            break;
                    }
                    $("#error").html("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\"> <p>" + msg + "</p> <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                }
			});
        }
    })
