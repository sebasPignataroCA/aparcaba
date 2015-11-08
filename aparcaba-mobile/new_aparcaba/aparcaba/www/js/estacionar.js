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

  //var latitude = position.coords.latitude;
  //var longitude = position.coords.longitude;

  var latitude = -34.5999907;
  var longitude = -58.4211427;
  var center = {lat: latitude, lng: longitude};

  if (window.localStorage.getItem('radius')){
    var radio = window.localStorage.getItem('radius');
  } else {
    var radio = 200;
  }

  var url = dominio + "/" + latitude + "/" +  longitude + "/" + radio;

  var resultado;

  console.log(url);
  
  $.ajax({ 
    type: "GET",
    dataType: "json",
    url: url,
       
    crossDomain: true,
    contentType: "application/javascript; charset=utf-8",
    success: function(data){
      resultado = data;

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

        var address = this.friendlyAddress.split(',');

        marker.addListener('click', function(){
          var text = address[0];
          text = encodeURIComponent(text);
          console.log(text)
          var translateURL = "https://translate.google.com/translate_tts?ie=utf-8&q="+text+"&tl=es"
          $('audio').attr('src', translateURL).get(0).play();
        })
      })

      console.log(resultado)

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

  //navigator.geolocation.getCurrentPosition(onGeoSuccess, onError);

  onGeoSuccess();
    
});
