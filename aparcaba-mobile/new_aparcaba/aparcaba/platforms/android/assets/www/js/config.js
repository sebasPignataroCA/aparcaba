 // Wait for PhoneGap to load
//
document.addEventListener("deviceready", onDeviceReady, false);

// PhoneGap is ready
//
function onDeviceReady() {
    /*
    window.localStorage.setItem("key", "value");
    var keyname = window.localStorage.key(i);
    // keyname is now equal to "key"
    var value = window.localStorage.getItem("key");
    // value is now equal to "value"
    window.localStorage.removeItem("key");
    window.localStorage.setItem("key2", "value2");
    window.localStorage.clear();
    // localStorage is now empty
    */

    var radius = window.localStorage.getItem('radius');
    if (radius) {
        $('input[name=destino]').val(radius);
    } else {
        window.localStorage.setItem('radius', 300);
    }

    $('form').submit(function(){
        window.localStorage.setItem('radius', $(this).find('input[name=destino]').val());
        window.history.back();
    })
}
/*
$('form').submit(function(e){
    e.preventDefault();
    console.log($(this).find('input[name=destino]').val())

})
*/