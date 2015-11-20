var mapa = {

  map: null,
  markers: [],
  ubicacionActual: {lat:null,lng:null},
  radio: 300,

  parkApiUrl: "http://api-aparcaba.rhcloud.com/rest/park",
  CABA: "Ciudad de Buenos Aires",

  updateRadio: function() {
    if (window.localStorage.getItem('radius')){
      mapa.radio = parseInt(window.localStorage.getItem('radius'));
    }
  },

  decirTexto: function(text) {
    TTS
        .speak({
            text: text,
            locale: 'es-ES',
            rate: 0.75
        }, function () {
            //alert('success');
        }, function (reason) {
            alert(reason);
        });
  }

  getUbicacion: function() {

  }



},