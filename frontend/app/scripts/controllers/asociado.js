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
  .controller('AsociadoCtrl', ['$scope', 'Asociado', 'Grupo', 'Usuario', function ($scope, Asociado, Grupo, Usuario) {
    // var idGrupo = Usuario.usuario.idGrupo;
    // Grupo.get({id: idGrupo}, function(grupo) {
    //   $scope.grupo = grupo;
    // });
    $scope.grupo = { };

    $scope.asociados = [];
    Asociado.query(function(asociados) {
      $scope.asociados = asociados.content;
    });

    $scope.asociado = { };
    $scope.marcados = [];

    $scope.estado = estados.LIMPIO;

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
      $scope.asociado = {};
    };

    $scope.editar = function(id) {
      marcarCambiosPendientes();

      var pos = _.findIndex($scope.asociados, function(a) { return a ? a.id === id : false; })
      var original = $scope.asociados[pos];
      if (original.cambiosPendientes)
      {
        // Si previamente no se han guardado los cambios, evitamos recargar desde
        // el servidor y dejamos el asociado original con cambios pendientes.
        $scope.asociado = original;
      } else {
        Asociado.get({ 'id': id }, function(asociado) {
          asociado.marcado = original.marcado;
          $scope.asociado = asociado;
          $scope.asociados[pos] = asociado;
        });
      }
    };

    $scope.guardar = function(id) {
      $scope.estado = estados.GUARDANDO;

      var asociado = _.find($scope.asociados, function(a) { return a ? a.id === id : false; });

      // TODO petición al servidor aqui
      _.delay(function() {
        asociado.guardado = true;
        asociado.cambiosPendientes = false;
        $scope.asociado = asociado;
        $scope.formAsociado.$setPristine();
        $scope.estado = estados.OK;
        $scope.$apply();
      }, 1000);

      /*var completado = false;
      var tooLongId = _.delay(function(completado) {
        if (completado)
          return;
        $scope.guardando = true;
        $scope.$apply();
      }, 800, completado);*/

      // Replace this with the actual server call
      /*_.delay(function(completado, tooLongId) {
        clearTimeout(tooLongId);
        completado = true;
        $scope.guardando = false;
        $scope.$apply();
      }, 2000, completado, tooLongId);*/
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
      if ($scope.estado === estados.LIMPIO) { return 'btn-default'; }
      if ($scope.estado === estados.GUARDANDO) { return 'btn-progress'; }
      if ($scope.estado === estados.OK) { return 'btn-success'; }
      if ($scope.estado === estados.ERROR) { return 'btn-error'; }
      if ($scope.estado === estados.VALIDACION) { return 'btn-warning'; }
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
        $scope.asociado.estadoFrm = estados.VALIDACION;
        $scope.asociado.cambiosPendientes = true;
        $scope.formAsociado.$setPristine();
      }
      $scope.estado = estados.LIMPIO;
    };
  }]);
