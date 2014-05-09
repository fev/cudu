'use strict';

angular
  .module('cuduApp', [
    'ngResource',
    'ngRoute',
    'cuduServices',
    'cuduFilters'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/asociado.html',
        controller: 'AsociadoCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
