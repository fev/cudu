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
        seccion: 'asociado'
      })
      .when('/grupo/:id', {
        templateUrl: 'views/grupo.html',
        controller: 'GrupoCtrl',
        seccion: 'grupo'
      })
      .when('/tecnico/fev/asociado', {
        templateUrl: 'views/tecnico_fev.html',
        controller: 'TecnicoFevCtrl',
        seccion: 'tecnico-fev'
      })
      .otherwise({
        redirectTo: '/asociados'
      });
  })
  .run(function($rootScope) {

    $rootScope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $rootScope.controlador = target.$$route.controller;
        $rootScope.seccion = target.$$route.seccion;
      }
    });
    
  });
