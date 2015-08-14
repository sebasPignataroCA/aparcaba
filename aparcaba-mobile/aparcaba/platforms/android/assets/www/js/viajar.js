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


$(document).ready(function(){
    console.log($(location).attr('href'))

    var origen = GetURLParameter('origen').replace("+", " ");
    var destino = GetURLParameter('destino').replace("+", " ");

    console.log(origen, destino)
    var CABA = "Ciudad de Buenos Aires";
    var dominio = "http://api-aparcaba.rhcloud.com/api-1.0/rest/guidance"

    var url = dominio + "/" + origen + "," + CABA + "/" +  destino + "," + CABA;

    $.get(url, function(data){
    	$("#resultados").html(data);
    })
    
});