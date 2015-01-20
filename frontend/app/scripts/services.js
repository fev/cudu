(function() {
'use strict';

// TODO Add ngCookies here
var cuduServices = angular.module('cuduServices', ['ngResource']);

cuduServices.factory('Asociado', ['$resource',
  function($resource) {
    return $resource('/api/asociado/:id', {}, {
      'query': { method: 'GET', params: { size: 200 }, isArray: false },
      'crear': { method: 'POST' },
      'actualizar': { method:'PUT' }
    });
  }]);

cuduServices.factory('Grupo', ['$resource',
  function($resource) {
    return $resource('/api/grupo/:id');
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
