'use strict';

var filters = angular.module('cuduFilters', []);

filters.filter('rama', function() {
  return function(rama) {
    if (rama === 'C') { return 'Castores'; }
    if (rama === 'M') { return 'Lobatos'; }
    if (rama === 'E') { return 'Exploradores'; }
    if (rama === 'P') { return 'Pioneros'; }
    if (rama === 'R') { return 'Compañeros'; }
    if (rama === '') { return '(sin rama)'; }
    return '¿?';
  };
});

filters.filter('tipoAsociado', function() {
  return function(rama) {
    if (rama === 'J') { return 'Joven'; }
    if (rama === 'K') { return 'Kraal'; }
    if (rama === 'C') { return 'Comité'; }
    return '¿?';
  };
});

var app = angular.module('cuduApp');

app.filter('byId', function() {
  return function(objects, id) {
    for(var i = 0; i < objects.length; i++) {
      if(objects[i].id == id)
        return objects[i];
    }

    return null;
  };
});
