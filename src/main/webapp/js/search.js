$(document).ready(function () {
    $('.search-panel .dropdown-menu').find('a').click(function(e) {
        e.preventDefault();
        var param = $(this).attr("id");
        var concept = $(this).text();
        $('.search-panel span#search-concept').text(concept);
    });

    $("#search-button").on('click', function () {
        searchPrologue($('#search-query').val());
    });

    $("#content").on('click', '#save-search', function () {
        ajaxSaveSearch();
    });

    $( "#price-slider" ).slider({
        range:true,
        min: 0,
        max: $("#global_maxprice").val(),
        step: 50,
        values: [ $("#min-price").val(),  $("#max-price").val()],
        slide: function( event, ui ) {
           $( "#price" ).html( "€" + ui.values[ 0 ] + " - €" + ui.values[ 1 ] );
        },
        change: function( event, ui ) {
            $("#min-price").val(ui.values[ 0 ]);
            $("#max-price").val(ui.values[ 1 ]);
            searchPrologue($('#search-query').val());
        }
    });
    $( "#price" ).html( "€" + $( "#price-slider" ).slider( "values", 0 ) +
        " - €" + $( "#price-slider" ).slider( "values", 1 ) );

        $( "#distance-slider" ).slider({
            min: 1,
            max: $("#max-distance").val(),
            step: 1,
            values: [ $("#distance").val() ],
            slide: function( event, ui ) {
               $( "#distance-n" ).html(ui.values[ 0 ] + "km");
            },
            change: function( event, ui ) {
                $( "#distance" ).val(ui.values[ 0 ]);
                searchPrologue($('#search-query').val());
            }
        });
        $( "#distance" ).html($( "#distance-slider" ).slider( "values", 0 ) + "km");
      
});

function searchPrologue(locationQuery) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: `https://dev.virtualearth.net/REST/v1/Locations?query=${locationQuery}&maxResults=1&key=AghYmNhfhWJ5VGkmfMOX2eg63AmJNXX6dR4DpC7-q6YjCgNGqrzOzJyPUK3OQbqB&setMkt`,
        success: function (data, status, xhr) {
            let coordinates = data['resourceSets'][0]['resources'][0]['point']['coordinates'];
            let address = data['resourceSets'][0]['resources'][0]['address']['formattedAddress'];
            console.log(coordinates);
            if (coordinates != null) {
                searchEpilogue(coordinates,address);
            }
        }
    });
}

function searchEpilogue(coordinates,address) {

    var d = {
        // "search-query": $("#search-query").val(),
        "search-query": address,
        "latitude": coordinates[0],
        "longitude": coordinates[1],
        "type": $("#search-concept").html(),
        "distance": $("#distance").val(),
        "min_price": $("#min-price").val(),
        "max_price": $("#max-price").val(),
    };

    $('#search-query-hidden').val(address);

    $.ajax({
        type: "GET",
        url: "/search/internal",
        data: d,
        success: function (data, status, xhr) {
            // console.log(data);
            $("#content").html(data);
        },
        error: function (data, status, xhr) {
            console.log(data);
            console.log(status);
        }
    });
}

function ajaxSaveSearch() {
    var s = {
        longitude: $('#latitude').val(),
        latitude: $('#longitude').val(),
        type: $("#search-concept").html(),
        max_distance: $("#distance").val(),
        min_price: $("#min-price").val(),
        max_price: $("#max-price").val(),
        city: $('#search-query-hidden').val()
    };

    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};

    headers[csrfHeader] = csrfToken;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/search/save",
        data: JSON.stringify(s),
        headers: headers,
        success: function (data, status, xhr) {
            //TODO MODAL SAVED SEARCH
        }
    });
}