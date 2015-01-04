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
          return '/asociados';
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
         redirectTo: '/'
      });
  })
  .run(function($rootScope, $location, $cookies, RolesMenu, Usuario) {

    $rootScope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $rootScope.controlador = target.$$route.controller;
        $rootScope.seccion = target.$$route.seccion;
        $rootScope.rol = RolesMenu.ASOCIADO;
      }
    });

    // Handler para botón de "Salir" en menú principal
    $rootScope.desautenticar = function() {
      Usuario.desautenticar()
        .success(function() {
          // window.location = "http://www.scoutsfev.org";
          window.location = "/";
        });
    };

    // Intentamos obtener el usuario actual. Si el servidor devuelve 403,
    // redirigimos a la página de login, en caso contrario las credenciales
    // son correctas (almacenadas en la cookie JSESSIONID).
    Usuario.obtenerActual()
      .success(function(usuario) {
        // No muy elegante, pero es rápido y sólo se ejecuta una vez
        $("#lnkUsuarioActual").text(usuario.nombreCompleto);
        $('#cuduNav, #cuduNavBg').removeClass("hidden");

        // TODO Si el usuario es asociado, redirigir a /asociados
        // Tecnicos y Lluerna tienen otras url de entrada
        $location.path("/asociados");
      })
      .error(function() {
        $location.path("/login");
      });
  });
