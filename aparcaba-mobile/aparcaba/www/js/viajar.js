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
    center: {lat: -34.397, lng: 150.644},
    zoom: 8
  });
}


$(document).ready(function(){

  var origen = GetURLParameter('origen').replace("+", " ");
  var destino = GetURLParameter('destino').replace("+", " ");

  var CABA = "Ciudad de Buenos Aires";
  var dominio = "http://api-aparcaba.rhcloud.com/rest/guidance"
  var radio = "50"

  var url = dominio + "/" + origen + "," + CABA + "/" +  destino + "," + CABA + "/" + radio;

  var resultado;
  
  $.ajax({ 
    type: "GET",
    dataType: "json",
    url: url,
       
    crossDomain: true,
    contentType: "application/javascript; charset=utf-8",
    success: function(data){
      $('#desde').text(data.origin);
      $('#hasta').text(data.destination);

      $.each(data.steps, function(){
        var latitude = "<label> latitud:" + this.destinationCoordinates.latitude + "  </label>"
        var longitude = "<label> longitud:" + this.destinationCoordinates.longitude + "</label><br>"
        $('#pasos').append(latitude + longitude)
      });

      $('#duration').text(data.totalDuration + " seg");


    } 
     
  });

  
    
});
