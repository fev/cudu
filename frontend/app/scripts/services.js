(function() {
'use strict';

// TODO Add ngCookies here
var cuduServices = angular.module('cuduServices', ['ngResource']);

var metodos = {
  'crear': { method: 'POST' },
  'actualizar': { method: 'PUT' },
  'query': { method: 'GET', params: { size: 200 }, isArray: false },
  'queryAll': { method: 'GET', params: { }, isArray: true }
};

var metodosAsociado = _.clone(metodos);
metodosAsociado['activar'] = { url: '/api/asociado/:id/activar', method: 'PUT' };
metodosAsociado['desactivar'] = { url: '/api/asociado/:id/desactivar', method: 'PUT' };
metodosAsociado['desactivarSeleccionados'] = { url: '/api/asociado/desactivar', method: 'POST' };
metodosAsociado['asignarCargo'] = { url: '/api/asociado/:id/cargo/:cargoId', method: 'PUT' };
metodosAsociado['asignarCargoCustom'] = { url: '/api/asociado/:id/cargo', method: 'POST' };
metodosAsociado['eliminarCargo'] = { url: '/api/asociado/:id/cargo/:cargoId', method: 'DELETE' };
metodosAsociado['cambiarTipo'] = { url: '/api/asociado/cambiarTipo', method: 'POST' };
metodosAsociado['cambiarRama'] = { url: '/api/asociado/cambiarRama', method: 'POST' };

var metodosMiembro = _.clone(metodos);
metodosMiembro['añadir'] = { url: '/api/lluerna/miembro/:id', method: 'PUT' };
metodosMiembro['eliminar'] = { url: '/api/lluerna/miembro/:id', method: 'DELETE' };

cuduServices.factory('Asociado', ['$resource',
  function($resource) {
    return $resource('/api/asociado/:id', {}, metodosAsociado);
  }]);

cuduServices.factory('Miembro', ['$resource',
  function($resource) {
    return $resource('api/lluerna/miembro/:id', {}, metodosMiembro);
  }]);

cuduServices.factory('Grupo', ['$resource',
  function($resource) {
    return $resource('/api/grupo/:id', {}, metodos);
  }]);

cuduServices.factory('Actividad', ['$resource',
  function($resource) {
    return $resource('/api/actividad/:id', {}, {
      'crear'      : { method: 'POST' },
      'actualizar' : { method: 'PUT' },
      'queryAll'   : { method: 'GET', params: { }, isArray: true },
      'afegirBranca'    : { url: '/api/actividad/:id/rama', method: 'POST' },
      'afegirAssistent' : { url: '/api/actividad/:id/asociado/:asociadoId', method: 'POST' },
      'llevarAssistent' : { url: '/api/actividad/:id/asociado/:asociadoId', method: 'DELETE' },
      'canviarEstat'    : { url: '/api/actividad/:id/asociado/:asociadoId/estado', method: 'POST' }
    });
  }]);

  cuduServices.factory('Ficha', ['$http',
  function($http) {
    return {
      queryAll : function(tipo, onSuccess, onError) {
        var url = _.template('/api/fichas/entidad/<%= tipo %>');
        return $http.get(url({ 'tipo' : tipo }))
        .success(onSuccess)
        .error(onError);
      },
      generar : function(fichaId, asociados, actividadId, onSuccess, onError) {
        var template = _.template('/api/ficha/<%= id %>/generar');
        var url = template({ 'id' : fichaId });
        if(actividadId != null) {
          template = _.template('/api/ficha/<%= id %>/actividad/<%= actividadId %>/generar');
          url = template({ 'id' : fichaId, 'actividadId': actividadId });
        }
        return $http.post(url, asociados)
        .success(onSuccess)
        .error(onError);
      },
      listado : function(asociados, columnas, onSuccess, onError) {
        var url = '/api/asociado/imprimir';
        return $http.post(url, { identificadores: asociados, columnas: columnas })
        .success(onSuccess)
        .error(onError);
      }
    };
  }]);

cuduServices.factory('Typeahead', [function() {
  var typeahead = function(entidad, fn, displayKey) {
    var dkFnc = displayKey || fn;
    return function() {
      var source = new Bloodhound({
        datumTokenizer: function(d) { return Bloodhound.tokenizers.whitespace(d.nombre); },
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
          url: '/api/typeahead/' + entidad + '/%QUERY',
          wildcard: '%QUERY',
          filter: function(response) { return response.content; }
        }
      });
      source.initialize();
      return { displayKey: dkFnc, source: source.ttAdapter() };
    };
  };
  return {
    'asociado': typeahead('asociado', function(e) { return e.nombre + ' ' + e.apellidos; }),
    'miembro': typeahead('miembro', function(e) { return e.nombre + ' ' + e.apellidos; }),
    'formador': typeahead('formador', function(e) { return e.nombreCompleto; }),
    'participante' : function(cursoId) { return typeahead('curso/' + cursoId + '/participante', function(e) { return e.nombre + ' ' + e.apellidos; })(cursoId); }
  };
}]);

cuduServices.factory('Graficas', ['$http', function($http) {
  var _rango5rosa = ['#fce4ec', '#fdbfce', '#fa99b1', '#f47195', '#ec407a'];
  var _rango5azul = ["#E0F7FA", "#B2EBF2", "#80DEEA", "#4DD0E1", "#00ACC1"];
  var _rangoMulticolor = ['#ffc107', '#ffeb3b', '#5677fc', '#e51c23', '#8bc34a'];
  var _colorLinea = "rgba(148,159,177,1)";
  var _coloresRamas = _.map(_rangoMulticolor, function(c) {
    return { fillColor: _colorLinea, strokeColor: c, };
  });

  var _colorRellenoTipo = ['#3F51B5', '#7986CB', '#9FA8DA'];
  var _coloresTipos = _.map(_colorRellenoTipo, function(c) {
    return { fillColor: _colorLinea, strokeColor: c, };
  });

  return {
    colorLinea : _colorLinea,
    rango5azul : _rango5azul,
    rangoMulticolor: _rangoMulticolor,
    coloresRama: _coloresRamas,
    coloresTipo: _coloresTipos,
    colorEscalaLogin: "#80DEEA",
    coloresHistoricoLogin: [
      { fillColor: "rgba(20, 20, 20, 0.1)", strokeColor: "#B2EBF2" },
      { fillColor: "rgba(20, 20, 20, 0.1)", strokeColor: "#80DEEA" },
      { fillColor: "rgba(20, 20, 20, 0.1)", strokeColor: "#76FF03" }
    ],
    login: function() {
      return $http.get('/api/graficas/login');
    },
    grupo: function(grupoId) {
      return $http.get('/api/graficas/grupo/' + grupoId);
    }
  };
}]);

cuduServices.factory('Usuario', ['$http', '$cookies', '$q', function($http, $cookies, $q) {
  // Angular carga algunos de los controladores antes que el servidor devuelva
  // los datos del usuario actual, por ello es necesario establecer un usuario
  // anónimo que evite problemas al navegar propiedades.
  var usuarioAnonimo = { grupo: null };
  var usuarioDiferido = $q.defer();
  var svc = { usuario: usuarioAnonimo };

  svc.obtenerActual = function() {
    return usuarioDiferido.promise;
  };

  svc.obtenerActualDelServidor = function() {
    var respuesta = $http.get('/api/usuario/actual');
    respuesta.success(function(data, status) {
      svc.usuario = data;
      usuarioDiferido.resolve(data);
    }); // 403 redirige al login
    return respuesta;
  };

  svc.autenticar = function(email, password, captcha) {
    delete $cookies['JSESSIONID'];
    var respuesta = $http.post('/api/usuario/autenticar', { 'email': email, 'password': password, 'captcha': captcha });
    return respuesta.success(function(data, status) {
      svc.usuario = data;
      usuarioDiferido.resolve(data);
    });
  };

  svc.desautenticar = function() {
    var respuesta = $http.post('/api/usuario/desautenticar', {});
    var limpiar = function() {
      delete $cookies['JSESSIONID'];
      svc.usuario = usuarioAnonimo;
    };
    respuesta.success(limpiar).error(limpiar);
    return respuesta;
  };

  svc.activar = function(id, email) {
    return $http.post('/api/usuario/activar/' + id, email);
  };

  svc.desactivar = function(id) {
    return $http.post('/api/usuario/desactivar/' + id);
  };

  svc.resetPassword = function(email, captcha) {
    return $http.post('/api/usuario/reset', { 'email': email, 'password': null, 'captcha': captcha });
  };

  svc.calcularEdad = function(valor) {
    if (Object.prototype.toString.call(valor) === '[object Array]' && valor.length === 3) {
      var fechaNacimiento = new Date(valor[0], valor[1], valor[2]);
      if (!isNaN(fechaNacimiento.valueOf())) {
        var hoy = new Date();
        return hoy.getYear() - fechaNacimiento.getYear();
      }
    }
    return '¿?';
  };

  svc.cambiarIdioma = function(codigo) {
    return $http.post('/api/usuario/lenguaje', codigo);
  };

  return svc;
}]);

cuduServices.factory('focus', function($timeout) {
  return function(id) {
    $timeout(function() {
      var element = document.getElementById(id);
      if(element) {
        element.focus();
      }
    });
  };
});

// Servicio de manipulación del DOM fuera del scope de angular, para cambiar
// entre el modo de login y el de APP y renderizar algunos estáticos adicionales.
// No muy elegante, pero es rápido y evita bindings en el rootScope.
angular.module('cuduDom', []).factory('Dom', ['$rootScope', 'Traducciones', 'RolesMenu', function($rootScope, Traducciones, RolesMenu) {
  var establecerTextosMenu = function(nombreCompletoUsuario, codigoGrupo) {
    // ELementos del menú, para uso con bind-once.
    $rootScope.menu = {
      actividades: Traducciones.texto('menu.actividades'),
      asociados: Traducciones.texto('menu.asociados'),
      cursos: Traducciones.texto('menu.cursos'),
      grupos: Traducciones.texto('menu.grupos'),
      liquidaciones: Traducciones.texto('menu.liquidaciones'),
      miembros: Traducciones.texto('menu.miembros'),
      migrupo: Traducciones.texto('menu.migrupo'),
      misdatos: Traducciones.texto('menu.misdatos'),
      participantes: Traducciones.texto('menu.participantes'),
      permisos: Traducciones.texto('menu.permisos'),
      salir: Traducciones.texto('menu.salir'),
      nombreCompletoUsuario: nombreCompletoUsuario,
      codigoGrupo: codigoGrupo
    };
  };

  return {
    loginCompleto: function(usuario, lang) {
      // TODO Mover algunas de las propiedades a bindonce
      $('#cuduNav, #cuduNavBg').removeClass('hidden');
      $('#checkLenguajeES, #checkLenguajeCA').hide();
      $('#checkLenguaje' + lang.toUpperCase()).show();

      var grupo = usuario.grupo || { id: 'up' };
      establecerTextosMenu(usuario.nombreCompleto, grupo.id.toLowerCase());

      var rolMenu = RolesMenu.ASOCIADO;
      if ((usuario.tipo === 'T') && (usuario.ambitoEdicion === 'E')) {
        rolMenu = RolesMenu.LLUERNA;
      } else if  ((usuario.tipo === 'T') && (usuario.ambitoEdicion === 'F' || usuario.ambitoEdicion === 'A')) {
        rolMenu = RolesMenu.TECNICO;
      }
      $('body').addClass(rolMenu);
    }
  };
}]);

cuduServices.factory('Notificaciones', function() {
  var callbackObj = function(nodo) {
    return {
      cerrar: function() { toastr.clear(nodo); }
    };
  };
  return {
    progreso: function(mensaje, timeOut) {
      timeOut = timeOut || (10000);
      var nodo = toastr.info(mensaje, null, { progressBar: true, iconClass: 'toast-en-curso', timeOut: timeOut });
      return callbackObj(nodo);
    },
    errorServidor: function(mensaje, progreso) {
      progreso = progreso || { cerrar: function() { toastr.clear(); } };
      progreso.cerrar();
      toastr.error(mensaje, "Error del servidor");
    },
    completado: function(mensaje, progreso) {
      progreso = progreso || { cerrar: function() { toastr.clear(); } };
      progreso.cerrar();
      toastr.info(mensaje);
    }
  };
});

}());
