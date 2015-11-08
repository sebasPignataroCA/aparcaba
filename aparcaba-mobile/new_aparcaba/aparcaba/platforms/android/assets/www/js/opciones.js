
$(document).ready(function(){

    var primerIngreso = window.localStorage.getItem('primerIngreso');

    if (primerIngreso == null) {

        window.localStorage.setItem('primerIngreso', true);
        window.location.replace("bienvenido.html");
    }

});