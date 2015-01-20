'use strict';

// TODO Mover a constantes de angular
var estados = {
  LIMPIO: 0,
  GUARDANDO: 1,
  OK: 2,
  ERROR: 3,
  VALIDACION: 4
};

angular.module('cuduApp')
  .controller('SeguridadCtrl', ['$scope', function($scope) {
    $scope.mostrarAlertaPassword = false;

    $scope.cambiarPassword = function(asociado) { };

    $scope.activar = function(asociado) {
      $scope.mostrarAlertaPassword = true;
    };

    $scope.desactivar = function(asociado) {
    };
  }])
  .controller('AsociadoCtrl', ['$scope', 'Asociado', 'Grupo', 'Usuario', 'EstadosFormulario', function ($scope, Asociado, Grupo, Usuario, EstadosFormulario) {
    $scope.grupo = Usuario.usuario.grupo;
    $scope.asociados = [];
    Asociado.query(function(asociados) {
      $scope.asociados = asociados.content;
    });

    $scope.asociado = { 'grupoId': $scope.grupo.id, 'ambitoEdicion': 'G' };
    $scope.marcados = [];

    $scope.estado = EstadosFormulario.LIMPIO;
    $scope.erroresValidacion = [];

    $scope.tabActivo = 0;
    $scope.busqueda = '';
    $scope.filtro = { tipo: '', eliminados: false };
    $scope.columnas = { rama: true, direccion: false, contacto: false };
    $scope.orden = 'apellidos';

    $scope.modal = { eliminar: false };

    $scope.seleccionarTab = function(tabId) {
      $scope.tabActivo = tabId;
    };

    $scope.limpiarFiltro = function() {
      $scope.busqueda = '';
      $scope.filtro = { tipo: '', eliminados: false };
    };

    $scope.filtrar = function(asociado) {
      var f = $scope.filtro || {};
      var sinFiltroDeRama = !(f.ramaColonia || f.ramaManada ||
        f.ramaExploradores || f.ramaExpedicion || f.ramaRuta);

      return ((f.tipo === '' || asociado.tipo === f.tipo) &&
              (sinFiltroDeRama || (f.ramaColonia && asociado.ramaColonia) ||
                  (f.ramaManada && asociado.ramaManada) ||
                  (f.ramaExploradores && asociado.ramaExploradores) ||
                  (f.ramaExpedicion && asociado.ramaExpedicion) || 
                  (f.ramaRuta && asociado.ramaRuta)) &&
              (f.eliminados || asociado.activo));
    };

    $scope.activarFiltro = function(filtro, valor, porDefecto) {
      var valorPorDefecto = typeof porDefecto !== 'undefined' ? porDefecto : '';
      if ($scope.filtro[filtro] === valor) {
        $scope.filtro[filtro] = valorPorDefecto;
      } else {
        $scope.filtro[filtro] = valor;
      }
    };

    $scope.nuevo = function() {
      marcarCambiosPendientes();
      $scope.asociado = {
        'grupoId': $scope.grupo.id,
        'ambitoEdicion': 'G'
      };
    };

    $scope.editar = function(id) {
      marcarCambiosPendientes();

      var pos = _.findIndex($scope.asociados, function(a) { return a ? a.id === id : false; });
      var original = $scope.asociados[pos];
      if (original.cambiosPendientes)
      {
        // Si previamente no se han guardado los cambios, evitamos recargar desde
        // el servidor y dejamos el asociado original con cambios pendientes.
        $scope.asociado = original;
      } else {
        Asociado.get({ 'id': id }, function(asociado) {
          asociado.marcado = original.marcado;
          asociado.guardado = original.guardado;
          $scope.asociado = asociado;
          $scope.asociados[pos] = asociado;
        });
      }
    };

    $scope.guardar = function(id) {
      $scope.estado = EstadosFormulario.GUARDANDO;

      var guardar = Asociado.actualizar;
      if (!$scope.asociado.id) {
        guardar = Asociado.crear;
      }

      guardar({ id: id }, $scope.asociado).$promise.then(function(asociadoGuardado) {
        $scope.estado = EstadosFormulario.OK;
        asociadoGuardado.guardado = true;
        $scope.asociado = asociadoGuardado;
        var pos = _.findIndex($scope.asociados, function(a) { return a ? a.id === id : false; });
        $scope.asociados[pos] = asociadoGuardado;
        $scope.formAsociado.$setPristine();
      }, function(respuesta) {
        if (respuesta.status == 400) {
          $scope.estado = EstadosFormulario.VALIDACION;
          $scope.erroresValidacion = respuesta.data || [];
        } else {
          $scope.estado = EstadosFormulario.ERROR;
        }
      });
    };

    $scope.activar = function(id, activar) {
      // TODO petición a servidor aqui
      $scope.asociado.activo = activar;
      $scope.asociado.cambiosPendientes = false;
      $scope.formAsociado.$setPristine();
    };

    $scope.eliminar = function(id) {
      _.remove($scope.asociados, function(a) { return a ? a.id === id : false; });
      $scope.asociado = {};
      $scope.modal.eliminar = false;
    };

    $scope.marcar = function(asociado, e) {
      if (!asociado.marcado) {
        asociado.marcado = true;
        $scope.marcados.push(asociado.id);
      } else {
        asociado.marcado = false;
        _.remove($scope.marcados, function(i) { return i === asociado.id; });
      }
    };

    $scope.establecerSexo = function(sexo) {
      $scope.asociado.sexo = sexo;
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerTipo = function(tipo) {
      $scope.asociado.tipo = tipo;
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerRama = function(rama) {
      var actual = $scope.asociado[rama];
      if (actual) {
        $scope.asociado[rama] = false;
      } else {
        $scope.asociado[rama] = true;
      }
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerEstiloGuardado = function() {
      if ($scope.estado === EstadosFormulario.LIMPIO) { return 'btn-default'; }
      if ($scope.estado === EstadosFormulario.GUARDANDO) { return 'btn-progress'; }
      if ($scope.estado === EstadosFormulario.OK) { return 'btn-success'; }
      if ($scope.estado === EstadosFormulario.ERROR) { return 'btn-error'; }
      if ($scope.estado === EstadosFormulario.VALIDACION) { return 'btn-warning'; }
      return 'btn-default';
    };

    $scope.copiarDatosContacto = function(desde, hasta) {
      $scope.asociado['telefono' + desde] = $scope.asociado['telefono' + hasta];
      $scope.asociado['email' + desde] = $scope.asociado['email' + hasta];
      $scope.formAsociado.$setDirty();
    };

    $scope.obtenerNombreCompleto = function(asociado) {
      if (asociado && (asociado.nombre || asociado.apellidos)) {
        return [asociado.nombre, asociado.apellidos].join(' ');
      }
      return '(nuevo)';
    };

    $scope.mostrarColumna = function(nombre) {
      var columnas = $scope.columnas || {};
      if (columnas[nombre]) {
        columnas[nombre] = false;
      } else {
        columnas[nombre] = true;
      }
    };

    $scope.cssRadio = function(valor) {
      if (valor) {
        return "fa-dot-circle-o";
      } else {
        return "fa-circle-o";
      }
    };

    $scope.cssCheck = function(valor) {
      if (valor) {
        return "fa-check-square-o";
      } else {
        return "fa-square-o";
      }
    };

    var marcarCambiosPendientes = function() {
      if ($scope.asociado.id && $scope.formAsociado.$dirty) {
        $scope.asociado.estadoFrm = EstadosFormulario.VALIDACION;
        $scope.asociado.cambiosPendientes = true;
        $scope.formAsociado.$setPristine();
      }
      $scope.estado = EstadosFormulario.LIMPIO;
    };
  }]);
