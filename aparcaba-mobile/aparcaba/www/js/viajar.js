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
    var dominio = "http://api-aparcaba.rhcloud.com/rest/guidance"

    var url = dominio + "/" + origen + "," + CABA + "/" +  destino + "," + CABA;
/*
    $.get(url, function(data){
    	$("#resultados").html(data);
    },"jsonp").fail(function() {
	    alert( "error" );
	  })
*/

/*
var jqxhr = $.getJSON( url, function() {
  console.log( "success" );
})
  .done(function() {
    console.log( "second success" );
  })
  .fail(function() {
    console.log( "error" );
  })
  .always(function() {
    console.log( "complete" );
  });
 
// Perform other work here ...
 
// Set another completion function for the request above
jqxhr.complete(function() {
  console.log( "second complete" );
});
*/

	$.ajax({
		url:url,
		type:'GET',
		dataType:'json',
		error:function(jqXHR,text_status,strError){
			alert("no connection");
    },
    data: {  // the parameters to add to the request
        format: 'json',
        action: 'query',
        titles: 'test'
    },
		timeout:60000,
		success:function(data){
			alert("todo bien");
		}
	});
    
});
