'use strict';

var estados = {
  LIMPIO: 0,
  GUARDANDO: 1,
  OK: 2,
  ERROR: 3,
  VALIDACION: 4
};

angular.module('cuduApp')
  .controller('AsociadoCtrl', ['$scope', 'Asociado', 'Grupo', function ($scope, Asociado, Grupo) {
    $scope.grupo = Grupo.get();
    $scope.asociados = Asociado.query();
    $scope.asociado = { };

    $scope.estado = estados.LIMPIO;

    $scope.tabActivo = 0;
    $scope.busqueda = '';
    $scope.filtro = { tipo: '', rama: '', eliminados: false };

    $scope.modal = { eliminar: false };

    $scope.seleccionarTab = function(tabId) {
      $scope.tabActivo = tabId;
    };

    $scope.limpiarFiltro = function() {
      $scope.busqueda = '';
      $scope.filtro = { tipo: '', rama: '', eliminados: false };
    };

    $scope.filtrar = function(asociado) {
      return (($scope.filtro.tipo === '' || asociado.tipo === $scope.filtro.tipo) &&
              ($scope.filtro.rama === '' || asociado.rama === $scope.filtro.rama) &&
              ($scope.filtro.eliminados  || asociado.activo));
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
      $scope.asociado.seleccionado = false;
      marcarCambiosPendientes();
      $scope.asociado = {};
    };

    $scope.editar = function(id) {
      $scope.asociado.seleccionado = false;
      marcarCambiosPendientes();
      $scope.asociado = _.find($scope.asociados, function(a) { return a ? a.id === id : false; });
      $scope.asociado.seleccionado = true;
      /*Asociado.get({ 'idAsociado': id }, function(asociado) {
        $scope.asociado = asociado;
        $scope.asociado.seleccionado = true;
      });*/
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

    $scope.establecerSexo = function(sexo) {
      $scope.asociado.sexo = sexo;
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerTipo = function(tipo) {
      $scope.asociado.tipo = tipo;
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerRama = function(rama) {
      $scope.asociado.rama = rama;
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

    $scope.establecerEstiloRama = function(rama) {
      if (rama !== '') {
        return 'rama' + rama;
      }
      return '';
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

    var marcarCambiosPendientes = function() {
      if ($scope.asociado.id && $scope.formAsociado.$dirty) {
        $scope.asociado.estadoFrm = estados.VALIDACION;
        $scope.asociado.cambiosPendientes = true;
        $scope.formAsociado.$setPristine();
      }
      $scope.estado = estados.LIMPIO;
    };
  }]);
