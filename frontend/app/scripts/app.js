'use strict';

angular
  .module('cuduApp', [
    'ngResource',
    'ngRoute',
    'cuduServices',
    'cuduFilters'
  ])
  .constant("RolesMenu", {
    "ASOCIADO": "rol-asociado",
    "TECNICO" : "rol-tecnico",
    "LLUERNA" : "rol-lluerna"
  })
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
      .otherwise({
        redirectTo: '/asociados'
      });
  })
  .run(function($rootScope, RolesMenu) {

    $rootScope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $rootScope.controlador = target.$$route.controller;
        $rootScope.seccion = target.$$route.seccion;
        $rootScope.rol = RolesMenu.ASOCIADO;
      }
    });

  });
