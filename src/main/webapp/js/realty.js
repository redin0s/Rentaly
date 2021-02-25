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

        $("#account").on("click", function(event) {
            event.preventDefault();
            window.location.href = "/account";
        });

        $("#undo").on("click", function(event) {
            event.preventDefault();
            $('#undoModal').modal('show');
        });

        $('#deleteImage').on("click", function(event) {
            event.preventDefault();
            deleteImage();
        });

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
            // var form = new FormData();
            // // form.append("files", $("#files")[0].files);
            // let files = $("#files")[0].files;
            // for(var i=0; i < files.length; i++){
            //     form.append('files[' + i + ']', files[i]);
            // }

            var formData = new FormData($('#realty')[0]);

            var i = 
                (($("#description").val() == "" || $("#description").val() == null) && 
                ($("#cost").val() == 0 || $("#cost").val() == null))
                    ? null
                    : {
                        is_visible : $("#is_visible").is(':checked'),
                        description : $("#description").val(),
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
                insertion : i
            };

            if (r.square_meters == "" || r.square_meters == null) {
                r.square_meters = 1;
            }
            if (r.max_holders == "" || r.max_holders == null) {
                r.max_holders = 0;
            }

            let data = JSON.stringify(r);
            formData.append('realty', new Blob([data], {
                type: "application/json"
            } ));
            return formData;
        }

        function ajaxDraftPost() {

            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;


            $.ajax({
				type : "POST",
				// contentType : "application/json",
				url : "doSaveDraft",
                enctype: 'multipart/form-data',
                data : getFormData(),
                processData: false,
                contentType: false,
                cache: false,
                headers : headers,
				success : function (data, status, xhr) {
                    console.log("Draft successfully saved.");
 //TODO TOGGLE MODAL WITH PARAM DRAFT                   window.location.href = "/account";
                    $("#infoContent").html("Bozza salvata con successo.");
                    $('#saveModal').modal('show');

                    
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
				// contentType : "application/json",
                enctype: 'multipart/form-data',
                data : getFormData(),
                processData: false,
                contentType: false,
                cache: false,
				url : "doSaveRealty",
                headers : headers,
				success : function (data, status, xhr) {
                    console.log("Realty successfully saved.");
  //TODO TOGGLE MODAL WITH PARAM SAVED                     window.location.href = "/account";
                    $("#infoContent").html("Immobile salvato con successo.");
                    $('#saveModal').modal('show');

				}
			});

        }

        function deleteImage() {
            let activecar = $('.carousel-item.active');
            let image = activecar.find('img').attr('src');
            //ajax delete image at /delete?
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var headers = {};

            headers[csrfHeader] = csrfToken;

            $.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/delete",
                data : JSON.stringify({
                    image: image
                }),
                headers : headers,
				success : function (data, status, xhr) {
                    activecar.remove();
                    var next = $('.carousel-item');
                    if(next.length) {
                        next.first().addClass('active');
                    } else {
                        $('#carouselImages').remove();
                        $('#deleteImage').remove();
                    }
				}
			});
        }
        
    });