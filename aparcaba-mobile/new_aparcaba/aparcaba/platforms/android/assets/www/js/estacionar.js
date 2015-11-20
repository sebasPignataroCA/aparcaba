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
var markers = [];
var centroOriginal;

function initMap(center) {
  map = new google.maps.Map(document.getElementById('Gmap'), {
    center: center,
    zoom: 16
  });
}

function onError() {
  alert("error");
}

function onGeoSuccess(position){
  var CABA = "Ciudad de Buenos Aires";
  var dominio = "http://api-aparcaba.rhcloud.com/rest/park";

  var latitude = position.coords.latitude;
  var longitude = position.coords.longitude;

  //var latitude = -34.6028;
  //var longitude = -58.417968;
  var center = {lat: latitude, lng: longitude};
  centroOriginal = center

  if (window.localStorage.getItem('radius')){
    var radio = parseInt(window.localStorage.getItem('radius'));
  } else {
    var radio = 200;
  }

  var url = dominio + "/" + latitude + "/" +  longitude + "/" + radio;

  var resultado;
  var textoParaDecir = "";

  $.ajax({
    type: "GET",
    dataType: "json",
    url: url,

    crossDomain: true,
    contentType: "application/javascript; charset=utf-8",
    success: function(data){
      resultado = data;

      if (data.sensors.length == 0){
        window.localStorage.setItem('radius', radio+50);
        onGeoSuccess(position);
      }

      var sensors = resultado.sensors;

      initMap(center);

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

        markers.push(marker);

        var address = this.friendlyAddress.split(',');
        textoParaDecir += address[0] + ", ";

        marker.addListener('click', function(){
          var text = address[0];
          text = encodeURIComponent(text);
          console.log(text)
          var translateURL = "https://translate.google.com/translate_tts?ie=utf-8&q="+text+"&tl=es"
          $('audio').attr('src', translateURL).get(0).play();
        });
      });

      TTS
        .speak({
            text: textoParaDecir,
            locale: 'es-ES',
            rate: 0.75
        }, function () {
            //alert('success');
        }, function (reason) {
            alert(reason);
        });

      $("#eta").text(resultado.eta + " mins");
      $("#eta-container").show();

      var currentLocation = new google.maps.Marker({
        position: {lat: center.lat, lng: center.lng},
        map: map,
        icon: "img/car-marker.png"
      })

      var cityCircle = new google.maps.Circle({
        strokeColor: '#36cf5c',
        strokeOpacity: 0.8,
        strokeWeight: 5,
        fillColor: '#FF0000',
        fillOpacity: 0,
        map: map,
        center: center,
        radius: radio
      });


    }
  });
}


$(document).ready(function(){

  navigator.geolocation.getCurrentPosition(onGeoSuccess, onError);

  setInterval(refrescarMapa, 15000);

});

function refrescarMapa() {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(null);
  }
  markers = [];
  
  var CABA = "Ciudad de Buenos Aires";
  var dominio = "http://api-aparcaba.rhcloud.com/rest/park";

  var latitude = centroOriginal.lat;
  var longitude = centroOriginal.lng;

  //var latitude = -34.6028;
  //var longitude = -58.417968;
  var center = {lat: latitude, lng: longitude};

  if (window.localStorage.getItem('radius')){
    var radio = parseInt(window.localStorage.getItem('radius'));
  } else {
    var radio = 200;
  }

  var url = dominio + "/" + latitude + "/" +  longitude + "/" + radio;

  var resultado;
  var textoParaDecir = "";

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

        markers.push(marker);

        var address = this.friendlyAddress.split(',');
        textoParaDecir += address[0] + ", ";

        marker.addListener('click', function(){
          var text = address[0];
          text = encodeURIComponent(text);
          console.log(text)
          var translateURL = "https://translate.google.com/translate_tts?ie=utf-8&q="+text+"&tl=es"
          $('audio').attr('src', translateURL).get(0).play();
        })
      })      

      var cityCircle = new google.maps.Circle({
        strokeColor: '#36cf5c',
        strokeOpacity: 0.8,
        strokeWeight: 5,
        fillColor: '#FF0000',
        fillOpacity: 0,
        map: map,
        center: center,
        radius: radio
      });
    }

  });

}
