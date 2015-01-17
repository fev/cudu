'use strict';

var filters = angular.module('cuduFilters', []);

filters.filter('rama', function() {
  return function(a) {
    if (!a) {
      return '¿?';
    }
    var rama = [];
    if (a.ramaColonia) { rama.push('Castores'); }
    if (a.ramaManada) { rama.push('Lobatos'); }
    if (a.ramaExploradores) { rama.push('Exploradores'); }
    if (a.ramaExpedicion) { rama.push('Pioneros'); }
    if (a.ramaRuta) { rama.push('Compañeros'); }

    if (rama.length === 0) {
      return '(sin rama)';
    }
    return rama.join(', ');
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

filters.filter('edad', function() {
  return function(fechaNacimiento) {
    fechaNacimiento = new Date(fechaNacimiento);
    var hoy = new Date();
    return hoy.getYear() - fechaNacimiento.getYear();
  };
});
