'use strict';

angular
  .module('cuduApp', [
    'ngResource',
    'ngRoute',
    'ngCookies',
    'cuduTraducciones',
    'cuduServices',
    'cuduFilters'
  ])
  .constant("RolesMenu", {
    "ASOCIADO": "rol-asociado",
    "TECNICO" : "rol-tecnico",
    "LLUERNA" : "rol-lluerna"
  })
  .config(function($routeProvider) {
    $routeProvider
      .when('/', {
        redirectTo: function() {
          // TODO Redirigir dependiendo del rol del usuario
          return '/asociado';
        }
      })
      .when('/login', {
        templateUrl: 'views/login.html',
        controller: 'LoginCtrl',
        seccion: 'login'
      })
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
  .run(function($rootScope, $location, $cookies, RolesMenu, Usuario) {

    // console.log("Start");
    // console.log($cookies.JSESSIONID);

    $rootScope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $rootScope.controlador = target.$$route.controller;
        $rootScope.seccion = target.$$route.seccion;
        $rootScope.rol = RolesMenu.ASOCIADO;
      }
    });

    // if (Usuario.autenticado()) {
    //   $location.path("/");
    // }
    // $location.path("/login");
  });
