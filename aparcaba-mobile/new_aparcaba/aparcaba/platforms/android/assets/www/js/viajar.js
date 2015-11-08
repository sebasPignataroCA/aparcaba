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
function initMap(center) {
  map = new google.maps.Map(document.getElementById('Gmap'), {
    center: {lat: center.latitude, lng: center.longitude},
    zoom: 16
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

function estacionar(destino, duracionViaje) {
  var CABA = "Ciudad de Buenos Aires";
  var dominio = "http://api-aparcaba.rhcloud.com/rest/park";

  
  var latitude = destino.lat;
  var longitude = destino.lng;
  var center = {lat: latitude, lng: longitude};

  if (window.localStorage.getItem('radius')){
    var radio = window.localStorage.getItem('radius');
  } else {
    var radio = 300;
  }

  var url = dominio + "/" + latitude + "/" +  longitude + "/" + radio;

  console.log(url)

  var resultado;

  $.ajax({ 
    type: "GET",
    dataType: "json",
    url: url,
       
    crossDomain: true,
    contentType: "application/javascript; charset=utf-8",
    success: function(data){
      resultado = data;
      var sensors = resultado.sensors;

      var pinColor = "36cf5c";
      var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
        new google.maps.Size(21, 34),
        new google.maps.Point(0,0),
        new google.maps.Point(10, 34));

      $.each(sensors, function(){
        var marker = new google.maps.Marker({
          position: {lat: this.coordinates.latitude, lng: this.coordinates.longitude},
          map: map,
          title: 'Estacionar!',
          icon: pinImage
        });
      });

      var tiempoTotal = duracionViaje + data.eta;

      $("#eta").text(tiempoTotal + " mins");
      $("#eta-container").show();

    }
  });
}

$(document).ready(function(){

  var origen = GetURLParameter('origen').replace("+", " ");
  var destino = GetURLParameter('destino').replace("+", " ");

  var CABA = "Ciudad de Buenos Aires";
  var dominio = "http://api-aparcaba.rhcloud.com/rest/guidance"
  if (window.localStorage.getItem('radius')){
    var radio = window.localStorage.getItem('radius');
  } else {
    var radio = 300;
  }

  var url = dominio + "/" + origen + "," + CABA + "/" +  destino + "," + CABA + "/" + radio;

  console.log(url)

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

      var duracionViaje = Math.trunc(data.totalDuration / 60);

      var center = {
        latitude: data.steps[0].originCoordinates.latitude,
        longitude: data.steps[0].originCoordinates.longitude,
      }

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

      initMap(center);
      camino.setMap(map);

      var marker1 = new google.maps.Marker({
          position: {lat: coordenadas[0].lat, lng: coordenadas[0].lng},
          map: map,
          title: 'Origen'
        });
      var marker2 = new google.maps.Marker({
          position: {lat: coordenadas[coordenadas.length-1].lat, lng: coordenadas[coordenadas.length-1].lng},
          map: map,
          title: 'Destino'
        });

      estacionar(coordenadas[coordenadas.length-1], duracionViaje);
    } 
     
  });

  
    
});
