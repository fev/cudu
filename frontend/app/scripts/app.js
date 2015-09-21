'use strict';

angular
  .module('cuduApp', [
    'ngResource',
    'ngRoute',
    'ngCookies',
    'ngAnimate',
    'cuduTraducciones',
    'cuduRamas',
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
    'VALIDACION' : 4,
    'CUSTOM' : 5,
  })
  .constant('CuduEtc', {
    'IDIOMA': 'cudu.lenguaje'
  })
  .config(function($routeProvider) {
    $routeProvider
      .when('/', {
        redirectTo: function() {
          return '/asociados';
        }
      })
      .when('/login', {
        templateUrl: 'i18n/views/login.html',
        controller: 'LoginCtrl',
        seccion: 'login'
      })
      .when('/asociados', {
        templateUrl: 'i18n/views/asociado.html',
        controller: 'AsociadoCtrl',
        seccion: 'asociado'
      })
      .when('/grupo/:id', {
        templateUrl: 'i18n/views/grupo.html',
        controller: 'GrupoCtrl',
        seccion: 'grupo'
      })
      .when('/permisos', {
        templateUrl: 'i18n/views/permisos.html',
        controller: 'PermisosCtrl',
        seccion: 'permisos'
      })
      .when('/tecnico/fev/asociado', {
        templateUrl: 'i18n/views/tecnico_fev.html',
        controller: 'TecnicoFevCtrl',
        seccion: 'tecnico-fev'
      })
      .when('/actividades', {
        templateUrl: 'i18n/views/actividades/listado.html',
        controller: 'ActividadesListadoCtrl',
        seccion: 'actividades'
      })
      .when('/actividades/:id', {
        templateUrl: 'i18n/views/actividades/detalle.html',
        controller: 'ActividadesDetalleCtrl',
        seccion: 'actividades'
      })
      .when('/lluerna/miembros', {
        templateUrl: 'i18n/views/lluerna/miembro.html',
        controller: 'MiembrosCtrl',
        seccion: 'miembros'
      })
      .when('/lluerna/cursos', { // gestión a cursos, técnico lluerna
        templateUrl: 'i18n/views/lluerna/curso.html',
        seccion: 'cursos'
      })
      .when('/cursos', { // apuntarse a cursos, asociado
        templateUrl: 'i18n/views/cursos.html',
        seccion: 'cursos'
      })
      .otherwise({
         redirectTo: '/'
      });
  })
  .config(function($httpProvider, CuduEtc) {
    // Al cambiar de idioma la selección se guarda en el localStorage del navegador.
    // Cuando angular recarge el template asociado a '/' es necesario que lo pida
    // con el header correcto, por lo que establecemos los defaults del provider aqui.
    var lenguajeSeleccionado = localStorage.getItem(CuduEtc.IDIOMA);
    if (lenguajeSeleccionado) {
      $httpProvider.defaults.headers.common['Accept-Language'] = lenguajeSeleccionado;
    }
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
  .run(function($rootScope, $location, RolesMenu, Dom, Usuario, Traducciones, CuduEtc) {
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
      var recarga = function() {        
        localStorage.setItem(CuduEtc.IDIOMA, codigo);
        window.location = "/";         
      };
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

        if ((usuario.tipo === 'T') && (usuario.ambitoEdicion === 'E')) {
          $location.path("/lluerna/cursos");
        } else if ((usuario.tipo === 'T') && (usuario.ambitoEdicion === 'F' || usuario.ambitoEdicion === 'A')) {
            $location.path("/tecnico/asociados");
        } else  {
          $location.path("/asociados");
        }
      })
      .error(function() {
        // TODO Último lenguaje conocido o el del navegador
        Traducciones.establecerLenguaje();
        $location.path("/login");
      });
  });  
