var GetURLParameter = function(sParam) {

    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');

    for (var i = 0; i < sURLVariables.length; i++) {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) {
            return sParameterName[1];
        }
    }
}

var map;
function initMap() {
  map = new google.maps.Map(document.getElementById('Gmap'), {
    center: {lat: -34.6158238, lng: -58.4332985},
    zoom: 12
  });  
}

var onSuccess = function(position) {
    alert('Latitude: '          + position.coords.latitude          + '\n' +
          'Longitude: '         + position.coords.longitude         + '\n' +
          'Altitude: '          + position.coords.altitude          + '\n' +
          'Accuracy: '          + position.coords.accuracy          + '\n' +
          'Altitude Accuracy: ' + position.coords.altitudeAccuracy  + '\n' +
          'Heading: '           + position.coords.heading           + '\n' +
          'Speed: '             + position.coords.speed             + '\n' +
          'Timestamp: '         + position.timestamp                + '\n');
};

// onError Callback receives a PositionError object
//
function onError(error) {
    alert('code: '    + error.code    + '\n' +
          'message: ' + error.message + '\n');
}

$(document).ready(function(){

  var origen = GetURLParameter('origen').replace("+", " ");
  var destino = GetURLParameter('destino').replace("+", " ");

  var CABA = "Ciudad de Buenos Aires";
  var dominio = "http://api-aparcaba.rhcloud.com/rest/guidance"
  var radio = "50"

  var url = dominio + "/" + origen + "," + CABA + "/" +  destino + "," + CABA + "/" + radio;

  var resultado;

  var coordenadas = [];

  //navigator.geolocation.getCurrentPosition(onSuccess, onError);
  
  $.ajax({ 
    type: "GET",
    dataType: "json",
    url: url,
       
    crossDomain: true,
    contentType: "application/javascript; charset=utf-8",
    success: function(data){
      console.log(data);

      coordenadas.push({
        lat: data.steps[0].originCoordinates.latitude,
        lng: data.steps[0].originCoordinates.longitude
      })

      $.each(data.steps, function(){
        coordenadas.push({
          lat: this.destinationCoordinates.latitude,
          lng: this.destinationCoordinates.longitude
        })
      });

      var camino = new google.maps.Polyline({
        path: coordenadas,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
      });

      initMap();
      camino.setMap(map);
    } 
     
  });

  
    
});
