var iconFeature = new ol.Feature({
  geometry: new ol.geom.Point(ol.proj.fromLonLat([12.4917, 41.8887]))
});

var vectorLayer = new ol.layer.Vector({
        source: new ol.source.Vector({
          features: [iconFeature]
        }),
        style: new ol.style.Style({
          image: new ol.style.Icon({
            anchor: [0.5, 46],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            src: 'https://openlayers.org/en/latest/examples/data/icon.png'
          })
        })
      });
var map = new ol.Map({
    target: 'map',
    layers: [
      new ol.layer.Tile({
        source: new ol.source.OSM(),
      }),
      vectorLayer
    ],
    view: new ol.View({
      center: ol.proj.fromLonLat([12.4917, 41.8887]),
      zoom: 6
    })
  });

map.on('click', function(event) {
    let pos = addMarker(event.coordinates);
    reverseGeocode(pos);
});

function addMarker(coordinates) {
  let pos = ol.proj.transform(coordinates, 'EPSG:3857', 'EPSG:4326');
  vectorLayer.getSource().clear();
  vectorLayer.getSource().addFeature(new ol.Feature ({
    geometry: new ol.geom.Point(ol.proj.fromLonLat(pos))
  }));
  return pos;
}

function reverseGeocode(coords) {
  $.ajax({
      type : "GET",
      contentType : "application/json",
      url : 'https://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1],
      success : function (data, status, xhr) {
          console.log(data);
          $("#address").html(data.display_name);
      },
      error : function () {
          console.log("error");
      }
  });
}

function reverseAddress(fulladdress) {
  $.ajax({
      type : "GET",
      contentType : "application/json",
      url : 'https://nominatim.openstreetmap.org/ui/search.html?q=' + fulladdress,
      success : function (data, status, xhr) {
          console.log(data);
          // $("#address").html(data.display_name);

      },
      error : function () {
          console.log("error");
      }
  });
}
