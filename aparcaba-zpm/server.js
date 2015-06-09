

var express = require('express');
var directory = require('serve-index');
var http = require('http');
var util = require('util');
var morgan = require('morgan');
var bodyParser = require('body-parser');

var service = require('./services/service1');

var app = express();

var port = process.env.OPENSHIFT_NODEJS_PORT || 8080;
var homeDirectory = __dirname;
var httpDirectory = __dirname + '/public';

app.use(morgan());
app.use(bodyParser());

// starting the server

var ipaddress = process.env.OPENSHIFT_NODEJS_IP;

if (typeof ipaddress === "undefined") {

	console.warn('No OPENSHIFT_NODEJS_IP var, using 127.0.0.1');
	ipaddress = "127.0.0.1";
}

app.listen(port, ipaddress,function() {
    console.log('Starting Node.js');
    console.log('Server directory: ' + homeDirectory);
    console.log('Working directory: ' + httpDirectory);
    console.log('Listening on port: ' + port);
    service.initialize();
});

/*
 * Servicios REST
 */


app.post('/services/nodes', function(request, response) {
    console.log('posting a node');
    console.log(request.body);
    service.persistence.createNode(request.body, function(err, node) {
        console.log(util.inspect(node));
        response.send(200);
    });
});

app.get('/services/nodes', function(request, response) {
    service.getAllNodes(function(err, nodes) {
        if (err) {
            console.log(err);
            throw err;
        }
        console.log(nodes);
        response.send(200, nodes);
    });
});

app.get('/services/nodes/:nodeId', function(request, response) {
    service.persistence.getNode(request.params.nodeId, function(err, node) {
        if (err) {
            console.log(err);
            throw err;
        }
        console.log(node);
        response.send(200, node);
    });
});

app.use(express.static(httpDirectory));
app.use(directory(httpDirectory, {
    'icons': true,
    view: 'details'
}));


