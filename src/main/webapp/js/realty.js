window.onload(function() {
    // let realty = ${realty};
    // $("#id").html(realty.id);
    // $("#owner").html(realty.owner);
    // $("#display_name").html(realty.display_name);
    // $("#latitude").html(realty.latitude;
    // $("#longitude").html(realty.longitude);
    // $("#type").html(realty.type);
    // $("#sqm").html(realty.square_meters);
    // $("#inq").html(realty.max_holders);
});


$(document).ready(
    function() {

        // POST REQUESTS
        $("#undo").on("submit", function(event) {
            event.preventDefault();
            if(confirm("E se poi te ne penti?")) {
                console.log("undone changes");
            } 
        });

        $("#draft").on("submit", function(event) {
            event.preventDefault();
            console.log("saving draft");
            ajaxDraftPost();
        });

        $("#save").on("submit", function(event) {
            event.preventDefault();
            console.log("saving");
            ajaxSavePost();
        });

        function getFormData() {
            return {
                id : $("#id").val(),
                display_name : $("#display_name").val(),
                latitude : $("#latitude").val(),
                longitude : $("#longitude").val(),
                type : $("#type").val(),
                square_meters : $("#sqm").val(),
                max_holders : ($("#inq").val() <= 0) ? 0 : $("#inq").val(),
                owner : $("owner").val()
            };
        }

        function ajaxDraftPost() {

            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "doSaveDraft",
				data : JSON.stringify(getFormData()),
                dataType : 'json',
                crossDomain: true,
				success : function (data, status, xhr) {
                    console.log(data);
					if (data == "success") {
                        console.log("Draft successfully saved.");
                        window.location.href = "/myRealties";
                    }
                    else {
                        console.log("Error");
                    }
				}
			});

        }

        function ajaxSavePost() {

            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "doSaveRealty",
				data : JSON.stringify(getFormData()),
                dataType : 'json',
                crossDomain: true,
				success : function (data, status, xhr) {
                    console.log(data);
					if (data == "success") {
                        console.log("Realty successfully saved.");
                        window.location.href = "/myRealties";
                    }
                    else {
                        console.log("Error");
                    }
				}
			});

        }
        
    })