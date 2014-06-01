'use strict';

angular.module('cuduApp')
  .controller('AsociadoCtrl', ['$scope', 'Asociado', function ($scope, Asociado) {
    $scope.grupo = grupo;
    $scope.asociados = asociadosDePrueba;
    $scope.asociado = { };

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
      });*/
    };

    $scope.guardar = function(id) {
      var asociado = _.find($scope.asociados, function(a) { return a ? a.id === id : false; });
      // TODO petición al servidor aqui
      asociado.guardado = true;
      asociado.cambiosPendientes = false;
      $scope.asociado = asociado;
      $scope.formAsociado.$setPristine();
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

    $scope.establecerTipo = function(tipo) {
      $scope.asociado.tipo = tipo;
    };

    $scope.establecerRama = function(rama) {
      $scope.asociado.rama = rama;
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
        $scope.asociado.cambiosPendientes = true;
        $scope.formAsociado.$setPristine();
      }
    };
  }]);