'use strict';
var _ = require('lodash');
var properties = require ('properties');
var fs = require('fs');
var path = require('path');
require('colors');

var baseDir = 'app/views';
var outputDir = 'app/views_i18n';
var langs = ['es', 'ca'];
var templates = [
  'app/views/asociado.html',
  'app/views/grupo.html',
  'app/views/login.html',
  'app/views/permisos.html',
  'app/views/tecnico_fev.html',
  'app/views/actividades/detalle.html',
  'app/views/actividades/listado.html',
  'app/views/lluerna/miembro.html',
  'app/views/lluerna/curso.html',
  'app/views/curso.html'
];

function leerTemplate(traducciones, template, lang) {
  return function(err, data) {
    if (err) { 
      return console.log(err); 
    }
    var compiled = _.template(data);
    var traducido = compiled(traducciones);
    var destino = path.join(outputDir, lang, template.replace(baseDir, ''));
    var carpetaDestino = path.dirname(destino);    
    if (!fs.existsSync(carpetaDestino)) {
      console.log('MKDIR'.cyan, carpetaDestino);
      fs.mkdirSync(carpetaDestino);
    }
    fs.writeFile(destino, traducido, function(err) {
      if (!err) { 
        console.warn('OK'.green, destino); 
        return;        
      }
      console.log('ERROR'.red, destino);
      console.error(err);
    });
  };
}

function leerPropiedades(lang) {
  return function(error, traducciones) {
    if (error) { return console.error(error.red); }    
    for (var j = 0; j < templates.length; j++) {
      var tmpl = templates[j];
      fs.readFile(tmpl, 'utf-8', leerTemplate(traducciones, tmpl, lang));
    }
  };
}

if (!fs.existsSync(outputDir)) {
  console.log('MKDIR'.cyan, outputDir);
  fs.mkdirSync(outputDir);
}

for (var i = 0; i < langs.length; i++) {
  var lang = langs[i];
  var f = 'i18n/messages_' + lang + '.properties';
  properties.parse(f, { path: true, namespaces: true }, leerPropiedades(lang));
}
