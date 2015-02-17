'use strict';

angular
  .module('cuduApp', [
    'ngResource',
    'ngRoute',
    'ngCookies',
    'ngAnimate',
    'cuduTraducciones',
    'cuduDom',
    'cuduServices',
    'cuduFilters',
    'chart.js',
    'siyfion.sfTypeahead'
  ])
  .constant("RolesMenu", {
    "ASOCIADO": "rol-asociado",
    "TECNICO" : "rol-tecnico",
    "LLUERNA" : "rol-lluerna"
  })
  .constant("EstadosFormulario", {
    'LIMPIO'     : 0,
    'GUARDANDO'  : 1,
    'OK'         : 2,
    'ERROR'      : 3,
    'VALIDACION' : 4
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
      .when('/permisos', {
        templateUrl: 'views/permisos.html',
        controller: 'PermisosCtrl',
        seccion: 'permisos'
      })
      .when('/tecnico/fev/asociado', {
        templateUrl: 'views/tecnico_fev.html',
        controller: 'TecnicoFevCtrl',
        seccion: 'tecnico-fev'
      })
      .when('/actividades', {
        templateUrl: 'views/actividades/listado.html',
        controller: 'ActividadesListadoCtrl',
        seccion: 'actividades'
      })
      .when('/actividades/:id', {
        templateUrl: 'views/actividades/detalle.html',
        controller: 'ActividadesDetalleCtrl',
        seccion: 'actividades'
      })
      .otherwise({
         redirectTo: '/'
      });
  })  
  .config(function($httpProvider) {
    var interceptor = function($q, $location) {
      return {
        'responseError': function(rejection) {
          if (rejection.status == 401 || rejection.status == 403) {
            $location.path('/login');
          }
          return $q.reject(rejection);
        }
      };
    };
    $httpProvider.interceptors.push(interceptor);
  })
  .run(function($rootScope, $location, RolesMenu, Dom, Usuario, Traducciones) {
    $rootScope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $rootScope.controlador = target.$$route.controller;
        $rootScope.seccion = target.$$route.seccion;
      }
    });

    // Handler para botón de "Salir" en menú principal
    $rootScope.desautenticar = function() {
      var redirigirLogin = function() { window.location = "/"; };
      Usuario.desautenticar().success(redirigirLogin).error(redirigirLogin);
    };

    $rootScope.cambiarIdioma = function(codigo) {
      var recarga = function() { window.location = "/"; };
      Usuario.cambiarIdioma(codigo).success(recarga).error(recarga);
    };

    // Intentamos obtener el usuario actual. Si el servidor devuelve 403,
    // redirigimos a la página de login, en caso contrario las credenciales
    // son correctas (almacenadas en la cookie JSESSIONID).
    Usuario.obtenerActual()
      .success(function(usuario) {        
        // Cada vez que el usuario cambia de lenguaje se guarda en la columna
        // 'lenguaje' de la tabla asociado. Si nunca ha cambiado de lenguaje,
        // la columna será null y usaremos el lenguaje del navegador (lang).
        var lang = Traducciones.establecerLenguaje(usuario.lenguaje);
        Dom.loginCompleto(usuario, lang);

        // TODO Si el usuario es asociado, redirigir a /asociados
        // Tecnicos y Lluerna tienen otras url de entrada
        $location.path("/asociados");
      })
      .error(function() {
        // TODO Último lenguaje conocido o el del navegador
        Traducciones.establecerLenguaje();
        $location.path("/login");
      });
  });
