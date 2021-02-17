$(document).ready(
    function() {
        $('.it-date-datepicker').datepicker({
            dateFormat: 'dd/mm/yy'
        });

        $("#addHolder").on("click", function(event) {
            event.preventDefault();
            console.log("adding holder");
            ajaxAddHolderPost();
        });

        function getFormData() {
            return {
                realty_id : window.selected.toString(),
                user_email : $("#rent-user_email").val(),
                cost : $("#rent-cost").val(),
                start : $("#rent-start_date").val(),
                duration : $("#rent-duration").val()
            };
        };

        function ajaxAddHolderPost() {

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

            var d = getFormData();
            console.log(d);

            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/account/doAddHolder",
                data : JSON.stringify(d),
                headers : headers,
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

        $("#remove").on("click", function(event) {
            event.preventDefault();
            console.log("removing rent " + window.selected);
            //TODO ALERT?
            ajaxRemoveRentPost();
        });

        function ajaxRemoveRentPost() {

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/account/doRemoveRent",
                data : ({"selected" : window.selected}),
                headers : headers,
				success : function (data, status, xhr) {
					
                    //TODO CONFIRM TO USER
                    console.log("Successfully removed holder.");
                    
				}
			});

        }

    });

function setSelected(id) {
    window.selected = id;
    console.log(window.selected);
}