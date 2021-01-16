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
				success : function (result) {
					if (result.status == "success") {
						
					}
				}
			});
        }
    })
