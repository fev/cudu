'use strict';

var filters = angular.module('cuduFilters', []);

filters.filter('rama', function(Traducciones, Ramas) {
  return function(a, fallback) {
    if (!a) {
      return '¿?';
    }
    Ramas.get(a, fallback);
  };
});

filters.filter('edad', function(Usuario) {
  return function(fechaNacimiento) {
    return Usuario.calcularEdad(fechaNacimiento);
  };
});

filters.filter('fechaArray', function() {
  return function(fecha) {
    return _.foldr(fecha, function(t, q) {
      return t + "/" + q;
    });
  };
});

filters.filter('desdeHace', function(Traducciones) {
  return function(fecha) {
    var m = moment(fecha);
    if (!m.isValid()) {
      return Traducciones.texto('nunca');
    }
    return m.fromNow();
  };
});

filters.filter('i18n', function(Traducciones) {
  return function(value, base) {
    if (_.isUndefined(value) || _.isNull(value)) {
      return '¿?';
    }
    return Traducciones.texto(base + '.' + value);
  };
});

filters.filter('cargo', function(Traducciones) {
  return function(cargo) {
    if (_.isUndefined(cargo) || _.isNull(cargo)) {
      return '';
    }
    if (cargo.ambito === 'P') {
      return cargo.etiqueta;
    }
    return Traducciones.texto('cargo.' + cargo.etiqueta);
  };
});

