var map, searchManager;

function GetMap() {
    map = new Microsoft.Maps.Map('#map', {});

    //Make a request to geocode New York, NY.
    geocodeQuery("New York, NY");
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
                    var pin = new Microsoft.Maps.Pushpin(r.results[0].location);
                    map.entities.push(pin);

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

function reverseGeocode() {
    //If search manager is not defined, load the search module.
    if (!searchManager) {
        //Create an instance of the search manager and call the reverseGeocode function again.
        Microsoft.Maps.loadModule('Microsoft.Maps.Search', function () {
            searchManager = new Microsoft.Maps.Search.SearchManager(map);
            reverseGeocode();
        });
    } else {
        var searchRequest = {
            location: map.getCenter(),
            callback: function (r) {
                //Tell the user the name of the result.
                alert(r.name);
            },
            errorCallback: function (e) {
                //If there is an error, alert the user about it.
                alert("Unable to reverse geocode location.");
            }
        };

        //Make the reverse geocode request.
        searchManager.reverseGeocode(searchRequest);
    }
  }
    
    
  window.onload = GetMap();