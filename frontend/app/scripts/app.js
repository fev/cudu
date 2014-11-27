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
      .when('/asociados', {
        templateUrl: 'views/asociado.html',
        controller: 'AsociadoCtrl',
        tabActivo: 'asociado'
      })
      .when('/asociados/:id', {
        templateUrl: 'views/asociado.html',
        controller: 'AsociadoCtrl',
        tabActivo: 'asociado'
      })
      .when('/grupo/:id', {
        templateUrl: 'views/grupo.html',
        controller: 'GrupoCtrl',
        tabActivo: 'grupo'
      })
      .otherwise({
        redirectTo: '/asociados'
      });
  });
