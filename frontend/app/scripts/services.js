(function() {
'use strict';

// TODO Add ngCookies here
var cuduServices = angular.module('cuduServices', ['ngResource']);

var metodos = {
  'crear': { method: 'POST' },
  'actualizar': { method: 'PUT' },
  'query': { method: 'GET', params: { size: 200 }, isArray: false },
  'queryAll': { method: 'GET', params: { }, isArray: true }
};

cuduServices.factory('Asociado', ['$resource',
  function($resource) {
    return $resource('/api/asociado/:id', {}, metodos);
  }]);

cuduServices.factory('Grupo', ['$resource',
  function($resource) {
    return $resource('/api/grupo/:id', {}, metodos);
  }]);


cuduServices.factory('Actividad', ['$resource',
  function($resource) {
    return $resource('/api/actividad/:id', {}, {
      'crear'      : { method: 'POST' },
      'actualizar' : { method: 'PUT' },
      'queryAll'   : { method: 'GET', params: { }, isArray: true },
      'afegirAssistent' : { url: '/api/actividad/:id/asociado/:asociadoId', method: 'POST' },
      'llevarAssistent' : { url: '/api/actividad/:id/asociado/:asociadoId', method: 'DELETE' },
      'canviarEstat'    : { url: '/api/actividad/:id/asociado/:asociadoId/estado', method: 'POST' }
    });
  }]);

cuduServices.factory('Graficas', ['$http', function($http) {
  return {
    login: function() {
      return $http.get('/api/graficas/login');
    }
  };
}]);

cuduServices.factory('Usuario', ['$http', '$cookies', '$q', function($http, $cookies, $q) {
  var svc = { usuario: null };

  svc.obtenerActual = function() {
    var respuesta = $http.get('/api/usuario/actual');
    respuesta.success(function(data, status) { svc.usuario = data; });
    return respuesta;
  };

  svc.autenticar = function(email, password, captcha) {
    delete $cookies['JSESSIONID'];
    var respuesta = $http.post('/api/usuario/autenticar', { 'email': email, 'password': password, 'captcha': captcha });
    respuesta.success(function(data, status) { svc.usuario = data; });
    return respuesta;
  };

  svc.desautenticar = function() {
    var respuesta = $http.post('/api/usuario/desautenticar', {});
    var limpiar = function() {
      delete $cookies['JSESSIONID'];
      svc.usuario = null;
    };
    respuesta.success(limpiar).error(limpiar);
    return respuesta;
  };

  svc.activar = function(id, email) {
    return $http.post('/api/usuario/activar/' + id, email);
  };

  svc.desactivar = function(id) {
    return $http.post('/api/usuario/desactivar/' + id);
  };

  svc.calcularEdad = function(valor) {
    var fechaNacimiento = new Date(valor);
    if (isNaN(fechaNacimiento.valueOf()))
      return '¿?';
    var hoy = new Date();
    return hoy.getYear() - fechaNacimiento.getYear();
  };

  return svc;
}]);

// Servicio de manipulación del DOM fuera del scope de angular, para cambiar
// entre el modo de login y el de APP y renderizar algunos estáticos adicionales.
// No muy elegante, pero es rápido y evita bindings en el rootScope.
angular.module('cuduDom', []).factory('Dom', ['RolesMenu', function(RolesMenu) {
  return {
    loginCompleto: function(usuario) {
      $('#lnkUsuarioActual').text(usuario.nombreCompleto);
      $('#cuduNav, #cuduNavBg').removeClass('hidden');

      var rolMenu = RolesMenu.ASOCIADO;
      if ((usuario.tipo === 'T') && (usuario.ambitoEdicion === 'E')) {
        rolMenu = RolesMenu.LLUERNA;
      } else if  ((usuario.tipo === 'T') && (usuario.ambitoEdicion === 'F' || usuario.ambitoEdicion === 'A')) {
        rolMenu = RolesMenu.TECNICO;
      }
      $('body').addClass(rolMenu);
    }
  };
}]);

}());
