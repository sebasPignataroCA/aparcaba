
var ubicacion;

function viajar(){
  console.log(this);
  var text = $(this).parent().find("input").val();
  console.log(text);

  var origen = encodeURIComponent(ubicacion);
  var destino = encodeURIComponent(text)

  var url = "viajar.html?origen="+origen+"&destino="+destino;
  console.log(url);
  window.location.replace("viajar.html?origen="+origen+"&destino="+destino);
}

function borrar(event) {

  var id = $(event.currentTarget).parent().attr('favId');

  var favoritos = JSON.parse(window.localStorage.getItem('favoritos'));

  var filtrados = favoritos.filter(function(elem){
    return elem.id != id;
  });

  window.localStorage.setItem('favoritos', JSON.stringify(filtrados));

  $("[favId="+id+"]").remove();

}

function agregaFavoritos() {
  var closeDiv = '</div>';
  var roadButton = '<a class="btn viajar" id="viajar"><span id="config-icon" class="glyphicon glyphicon-road" aria-hidden="true"></span></a>';
  var deleteButton = '<a class="btn borrar" id="borrar"><span id="config-icon" class="glyphicon glyphicon-remove" aria-hidden="true"></span></a>';

  if (window.localStorage.getItem('favoritos')){
    var favoritos = JSON.parse(window.localStorage.getItem('favoritos'));
  } else {
    var favoritos = [];
  }

  /*var favoritos = [
    {id:1, text: "Medrano 1000"},
    {id:2, text: "Medrano 233"}
  ]*/
  $.each(favoritos, function(){
    var openDiv = '<div class="form-group col-xs-11" favId="'+this.id+'">';
    var input = '<input id="'+this.id+'" class="form-control input-xl" type="text" value="'+this.text+'" disabled>';

    var html = openDiv + input + roadButton + deleteButton + closeDiv;

    $("form").append(html);

    $(document).on('click', "[favId="+this.id+"] .viajar", viajar);
    $(document).on('click', "[favId="+this.id+"] .borrar", borrar);
  })
}



function onSuccess(position){
  var element = $('div#geolocation');
    element.text('Latitude: '           + position.coords.latitude              + '<br />' +
                        'Longitude: '          + position.coords.longitude             + '<br />' +
                        'Altitude: '           + position.coords.altitude              + '<br />' +
                        'Accuracy: '           + position.coords.accuracy              + '<br />' +
                        'Altitude Accuracy: '  + position.coords.altitudeAccuracy      + '<br />' +
                        'Heading: '            + position.coords.heading               + '<br />' +
                        'Speed: '              + position.coords.speed                 + '<br />' +
                        'Timestamp: '          + position.timestamp                    + '<br />');
    
  var url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+position.coords.latitude+","+position.coords.longitude+"&sensor=true"
    
  $.ajax({
      type: "GET",
      dataType: "json",
      url: url,
      success: function(data){
        console.log(data)
        var results = data.results;
        var address_components = results[0].address_components;
        var number = address_components[0].long_name;
        var address = address_components[1].long_name;

        ubicacion = address + " " + number;
      }
  })
}

function onError(){
  alert("Error");
}

$(document).ready(function(){
  
  navigator.geolocation.getCurrentPosition(onSuccess, onError);

  agregaFavoritos();


});

$("#viajar").click(viajar);