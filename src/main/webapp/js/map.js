var map, searchManager, selected;

/*
selected.location => lat e lon
selected.address => oggetto address
address.locality => nostra city
address.postalcode
address.addressline => via
*/

function GetMap() {
    // map = new Microsoft.Maps.Map('#map', {});
    map = new Microsoft.Maps.Map('#map');

    Microsoft.Maps.Events.addHandler(map, 'click', changeMarker)
    let location = new Microsoft.Maps.Location(document.getElementById('latitude').value, document.getElementById('longitude').value);
    console.log(location);
    pinMap(location);
    reverseGeocode(location);
}

function changeMarker(e) {
    let point = new Microsoft.Maps.Point(e.getX(), e.getY());
    let temp = e.target.tryPixelToLocation(point);
    let location = new Microsoft.Maps.Location(temp.latitude, temp.longitude);
    pinMap(location);
    reverseGeocode(location);
    // updateForm();
}

function updateForm() {
    document.getElementById('address').value = selected.address.addressLine;
    document.getElementById('city').value = selected.address.locality;
    document.getElementById('cap').value = selected.address.postalCode;
    document.getElementById('latitude').value = selected.location.latitude;
    document.getElementById('longitude').value = selected.location.longitude;
}

function pinMap(location) {
    var pin = new Microsoft.Maps.Pushpin(location, { 'draggable': false });
    map.entities.clear()
    map.entities.push(pin);
}

function geocodeQuery(query) {
    //If search manager is not defined, load the search module.
    if (!searchManager) {
        //Create an instance of the search manager and call the geocodeQuery function again.
        Microsoft.Maps.loadModule('Microsoft.Maps.Search', function () {
            searchManager = new Microsoft.Maps.Search.SearchManager(map);
            geocodeQuery(query);
        });
    } else {
        var searchRequest = {
            where: query,
            callback: function (r) {
                //Add the first result to the map and zoom into it.
                if (r && r.results && r.results.length > 0) {
                    pinMap(r.results[0].location);
                    map.setView({ bounds: r.results[0].bestView });
                }
            },
            errorCallback: function (e) {
                //If there is an error, alert the user about it.
                alert("No results found.");
            }
        };

        //Make the geocode request.
        searchManager.geocode(searchRequest);
    }
}

function reverseGeocode(location) {
    //If search manager is not defined, load the search module.
    if (!searchManager) {
        //Create an instance of the search manager and call the reverseGeocode function again.
        Microsoft.Maps.loadModule('Microsoft.Maps.Search', function () {
            searchManager = new Microsoft.Maps.Search.SearchManager(map);
            reverseGeocode(location);
        });
    } else {
        var searchRequest = {
            location: location,
            callback: function (r) {
                //Tell the user the name of the result.
                selected = r;
                updateForm();
                map.setView({
                    center: location,
                    zoom: 15
                });
            },
            errorCallback: function (e) {
                //If there is an error, alert the user about it.
                // alert("Unable to reverse geocode location.");
            }
        };

        //Make the reverse geocode request.
        searchManager.reverseGeocode(searchRequest);
    }
}  


window.onload = GetMap();