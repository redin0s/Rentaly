// https://www.bing.com/api/maps/mapcontrol?callback=GetMap&key=[YOUR_BING_MAPS_KEY]

var map, searchManager;

    function GetMap() {
        map = new Microsoft.Maps.Map('#map', {
            center: new Microsoft.Maps.Location(47.678, -122.133),
            zoom: 11
        });

        //Make a request to reverse geocode the center of the map.
        reverseGeocode();
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