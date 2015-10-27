cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "file": "plugins/cordova-plugin-whitelist/whitelist.js",
        "id": "cordova-plugin-whitelist.whitelist",
        "runs": true
    },
    {
        "file": "plugins/au.id.currie.tts/www/tts.js",
        "id": "au.id.currie.tts.TextToSpeech",
        "clobbers": [
            "window.texttospeech"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.0.0",
    "org.apache.cordova.geolocation": "0.3.12",
    "au.id.currie.tts": "0.0.1"
}
// BOTTOM OF METADATA
});