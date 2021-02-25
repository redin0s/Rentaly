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

        $("#deleteRealty").on("click", function (event) {     
            $("#infoModalContentDelete").html("Vuoi davvero eliminare l'immobile?");
            $("#deleteConfirm").unbind();
            $("#deleteConfirm").on("click", function (event) {
                ajaxDoRemoveRealty();
            });
            $('#deleteModal').modal('show');
        });

        $("#deleteInsertion").on("click", function () {
            $("#infoModalContentDelete").html("Vuoi davvero eliminare l'inserzione?");
            $("#deleteConfirm").unbind();
            $("#deleteConfirm").on("click", function () {
                ajaxDoRemoveInsertion();
            });
            $('#deleteModal').modal('show');
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
                    console.log("Successfully added holder.");

                    $("#infoModalContent").html("Abbiamo inviato una email a " + $("#rent-user_email").val());
                    $("#infoModal").modal('show');

                    $("#rent-user_email").val("");
                    $("#rent-cost").val("");
                    $("#rent-start_date").val("");
                    $("#rent-duration").val("");

				}
			});

        }
        
        function ajaxDoRemoveRealty() {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

            $.ajax({
				type : "POST",
				url : "/account/doRemoveRealty",
                data : {"id": window.selected},
                headers : headers,
				success : function (data, status, xhr) {
                    //$("#okModalContent").html("Immobile rimosso con successo.");
                    //$("#okModal").modal('show');
                    console.log("test2");

				}
			});

        }
        
        function ajaxDoRemoveInsertion() {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

            $.ajax({
				type : "POST",
				url : "/account/doRemoveInsertion",
                data : {"realtyID": window.selected},
                headers : headers,
				success : function (data, status, xhr) {
                    $("#okModalContent").html("Inserzione rimossa con successo.");
                    $("#okModal").modal('show');

				}
            });
            $.ajax({
                type: "POST",
				url : "/account/doRemoveRealty",
                data : {"realtyID": window.selected},
                headers : headers,
				success : function (data, status, xhr) {
                    //TODO
                    console.log("test1");
				}
			});

        }

    });

function setSelected(id) {
    window.selected = id;
}