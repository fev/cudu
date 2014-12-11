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
  var usuario = null;
  var svc = {};

  svc.autenticado = function() {
    return usuario != null;
  };

  svc.autenticar = function(email, password) {
    delete $cookies["JSESSIONID"];
    // TODO Limpiar campos, btoa polyfill
    return $http.get('/api/usuario', {
      withCredentials: true,
      headers: {'Authorization': 'Basic ' + btoa(email + ":" + password)}
    });
  };

  svc.desautenticar = function() {
    delete $cookies["JSESSIONID"];
    usuario = null;
  };

  return svc;
}]);
