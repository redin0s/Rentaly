window.onload = function() {
    //prendi lat e lon

    // quando modifichi una realty esistente
    // let pos = [
    //     document.getElementById("latitude").value,
    //     document.getElementById("longitude").value
    // ];
    //inserisci marker, quindi fai la ricerca e prenditi il result
    // addMarker(pos);
    // let realty = reverseGeocode(pos);
    //inserisci le cose mancanti nel form
    // loadRealty(realty);
};


$(document).ready(
    function() {

        // POST REQUESTS

        $("#savedraft").on("click", function(event) {
            event.preventDefault();
            console.log("saving draft");
            ajaxDraftPost();
        });

        $("#save").on("click", function(event) {
            event.preventDefault();
            console.log("saving");
            ajaxSavePost();
        });

        function getFormData() {
            var i = 
                ($("#is_visible").is(':checked') == false && 
                ($("#cost").val() == 0 || $("#cost").val() == null))
                    ? null
                    : {
                        is_visible : $("#is_visible").is(':checked'),
                        description : $("#description").val(),
                        //pictures?
                        cost : $("#cost").val()
                    };
            var r = {
                id : $("#id").val(),
                city : $("#city").val(),
                address : $("#address").val(),
                latitude : $("#latitude").val(),
                longitude : $("#longitude").val(),
                type : $("#type").val(),
                square_meters : $("#square_meters").val(),
                max_holders : ($("#max_holders").val() <= 0) ? 0 : $("#max_holders").val(),
                current_holders : 0,
                insertion : i
            };

            if (r.square_meters == "" || r.square_meters == null) {
                r.square_meters = 1;
            }
            if (r.max_holders == "" || r.max_holders == null) {
                r.max_holders = 0;
            }
            return r;
        }

        function ajaxDraftPost() {

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;


            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "doSaveDraft",
                data : JSON.stringify(getFormData()),
                headers : headers,
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

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;


            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "doSaveRealty",
                data : JSON.stringify(getFormData()),
                headers : headers,
				success : function (data, status, xhr) {
                    console.log(data);
					if (data == "success") {
                        console.log("Realty successfully saved.");
                        window.location.href = "/account";
                    }
                    else {
                        console.log("Error");
                    }
				}
			});

        }
        
    })