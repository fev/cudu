/* jslint node: true */
'use strict';

var express = require('express');
var morgan = require('morgan');
var proxy = require('express-http-proxy');
var url = require('url');

var app = express();
app.use(morgan('dev'));

var serveStatic = express.static('app');
var tmpl = {
	'es': express.static('app/views_i18n/es'),
	'ca': express.static('app/views_i18n/ca')
};

app.use('/i18n/views', function(req, res, next) {	
	var lang = req.acceptsLanguages('es', 'ca');
	if (!lang) { lang = 'es'; }
	tmpl[lang](req, res, next);
});

app.use(serveStatic);

app.use('/api', proxy('http://localhost:8080', {
  forwardPath: function(req) {
    return url.parse(req.url).path;
  }
}));

app.listen(3000, function () {
  console.log('Escuchando en http://localhost:3000');
});
