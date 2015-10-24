// Wait for device API libraries to load
//
document.addEventListener("deviceready", onDeviceReady, false);

// device APIs are available
//
function onDeviceReady() {
    navigator.geolocation.getCurrentPosition(onSuccess, onError);
}

// onSuccess Geolocation
//
function onSuccess(position) {
    var element = document.getElementById('geolocation');
    element.innerHTML = 'Latitude: '           + position.coords.latitude              + '<br />' +
                        'Longitude: '          + position.coords.longitude             + '<br />' +
                        'Altitude: '           + position.coords.altitude              + '<br />' +
                        'Accuracy: '           + position.coords.accuracy              + '<br />' +
                        'Altitude Accuracy: '  + position.coords.altitudeAccuracy      + '<br />' +
                        'Heading: '            + position.coords.heading               + '<br />' +
                        'Speed: '              + position.coords.speed                 + '<br />' +
                        'Timestamp: '          + position.timestamp                    + '<br />';

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
          $("#origen").val(address + " " + number);
        }
    })
}

// onError Callback receives a PositionError object
//
function onError(error) {
    var element = document.getElementById('geolocation');
    element.innerHTML = 'code: '    + error.code    + '\n' +
          'message: ' + error.message + '\n';
}

$(document).ready(function(){
  navigator.geolocation.getCurrentPosition(onSuccess, onError); 
});