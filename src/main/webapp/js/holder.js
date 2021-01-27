$(document).ready(
    function() {
        $("#addHolder").on("click", function(event) {
            event.preventDefault();
            console.log("adding holder");
            ajaxAddHolderPost();
        });

        function getFormData() {
            return {
                realty_id : $("#rent-realty").val(),
                user_email : $("#rent-user_email").val(),
                cost : $("#rent-cost").val(),
                start : $("#rent-start_date").val(),
                duration : $("#rent-duration").val()
            };
        };

        function ajaxAddHolderPost() {

            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/doAddHolder",
				data : JSON.stringify(getFormData()),
				success : function (data, status, xhr) {
                    console.log(data);
					if (data == "success") {
                        console.log("Successfully added holder.");
                    }
                    else {
                        console.log("Error");
                    }
				}
			});

        }

    });