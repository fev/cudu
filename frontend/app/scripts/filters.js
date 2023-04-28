'use strict';

var filters = angular.module('cuduFilters', []);

filters.filter('rama', function(Traducciones) {
  return function(a, fallback) {
    if (!a) {
      return '¿?';
    }
    var rama = [];
    if (a.ramaColonia || a.colonia) { rama.push(Traducciones.texto('rama.colonia')); }
    if (a.ramaManada || a.manada) { rama.push(Traducciones.texto('rama.manada')); }
    if (a.ramaExploradores || a.exploradores) { rama.push(Traducciones.texto('rama.exploradores')); }
    if (a.ramaExpedicion || a.expedicion) { rama.push(Traducciones.texto('rama.expedicion')); }
    if (a.ramaRuta || a.ruta) { rama.push(Traducciones.texto('rama.ruta')); }

    if (rama.length === 0) {
      var clave = fallback || 'rama.ninguna';
      return Traducciones.texto(clave);
    }
    return rama.join(', ');
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

filters.filter('timeStamp', function(Traducciones) {
  return function (n) {
    var fecha = moment(n);
    if (!fecha.isValid()) {
      return Traducciones.texto('fechaIncorrecta');
    }
    return fecha.format('DD/MM/YYYY, hh:mm:ss');
  }
});

filters.filter('desdeHace', function(Traducciones) {
  return function(fecha) {
    if (Object.prototype.toString.call(fecha) === '[object Array]') {
      fecha = fecha.slice(0, 6);
      fecha[1] = fecha[1] - 1;
    }
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

filters.filter('coalesce', function() {
  return function(valor, textoCuandoEsNulo) {
    if (_.isUndefined(valor) || _.isNull(valor)) {
      return textoCuandoEsNulo;
    }
    return valor;
  };
});
