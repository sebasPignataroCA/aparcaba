
function onSuccess(position) {
    var element = document.getElementById('geolocation');

    var url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+position.coords.latitude+","+position.coords.longitude+"&sensor=true"

    $.ajax({
        type: "GET",
        dataType: "json",
        url: url,
        success: function(data){
          console.log(data)

          if (data.error_message === 'undefined') {
            var results = data.results;
            var address_components = results[0].address_components;
            var number = address_components[0].long_name;
            var address = address_components[1].long_name;
            $("#origen").val(address + " " + number);
          }
        }
    });
}

// onError Callback receives a PositionError object
//
function onError(error) {
    console.log(error);
}

$(document).ready(function(){
  navigator.geolocation.getCurrentPosition(onSuccess, onError);

});

$("#favorito").click(function(e){
    e.preventDefault();

    var value = {
      text: $("#destino").val()
    };

    if (value.text) {

      if (window.localStorage.getItem('favoritos')){
        var favoritos = JSON.parse(window.localStorage.getItem('favoritos'));
      } else {
        var favoritos = [];
      }


      if(favoritos.length > 0) {
        value.id = favoritos[favoritos.length-1].id + 1;
        favoritos.push(value);
      } else {
        favoritos = [];
        value.id = 1;
        favoritos.push(value);
      }

      window.localStorage.setItem('favoritos', JSON.stringify(favoritos));

      navigator.notification.alert(value.text + ", agregado a favoritos", null, "Aviso", null);
    }

  });
