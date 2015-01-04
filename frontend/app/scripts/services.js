'use strict';

// TODO Add ngCookies here
var cuduServices = angular.module('cuduServices', ['ngResource']);

cuduServices.factory('Asociado', ['$resource',
  function($resource) {
    return $resource('test_data/asociados.json');
    // return $resource('/api/asociado/:idAsociado', {}, {
    //   query: { method: 'GET', params: { }, isArray: true }
    // });
  }]);

cuduServices.factory('Grupo', ['$resource',
  function($resource) {
    return $resource('test_data/grupo.json');
    // return $resource('grupo/:idGroup');
  }]);

cuduServices.factory('Usuario', ['$http', '$cookies', '$q', function($http, $cookies, $q) {
  var svc = { usuario: null };

  svc.obtenerActual = function() {
    var respuesta = $http.get('/api/usuario/actual');
    respuesta.success(function(data, status) { svc.usuario = data });
    return respuesta;
  };

  svc.autenticar = function(email, password, captcha) {
    delete $cookies["JSESSIONID"];
    var respuesta = $http.post('/api/usuario/autenticar', { 'email': email, 'password': password, 'catpcha': captcha });
    respuesta.success(function(data, status) { svc.usuario = data; });
    return respuesta;
  };

  svc.desautenticar = function() {
    var respuesta = $http.post("/api/usuario/desautenticar", {});
    respuesta.success(function() {
      delete $cookies["JSESSIONID"];
      svc.usuario = null;
    });
    return respuesta;
  };

  return svc;
}]);
