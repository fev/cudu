'use strict';

angular.module('cuduApp')
  .controller('AsociadoCtrl', ['$scope', 'Asociado', function ($scope, Asociado) {
    $scope.grupo = grupo;
    $scope.asociados = asociadosDePrueba;
    $scope.asociado = { };
    
    $scope.tabActivo = 0;
    $scope.busqueda = '';
    $scope.filtro = { tipo: '', rama: '', eliminados: false };

    $scope.seleccionarTab = function(tabId) {
      $scope.tabActivo = tabId;
    };
    
    $scope.limpiarFiltro = function() {
      $scope.busqueda = '';
      $scope.filtro = { tipo: '', rama: '', eliminados: false };
    };
    
    $scope.filtrar = function(asociado) {
      return (($scope.filtro.tipo === '' || asociado.tipo === $scope.filtro.tipo)
          &&  ($scope.filtro.rama === '' || asociado.rama === $scope.filtro.rama)
          &&  ($scope.filtro.eliminados  || asociado.activo));
    };
    
    $scope.activarFiltro = function(filtro, valor, valorPorDefecto) {
      var valorPorDefecto = typeof valorPorDefecto !== 'undefined' ? valorPorDefecto : '';
      if ($scope.filtro[filtro] === valor) {
        $scope.filtro[filtro] = valorPorDefecto;
      } else {
        $scope.filtro[filtro] = valor;
      }
    };
    
    $scope.nuevoAsociado = function() {
      $scope.asociado.seleccionado = false;
      marcarCambiosPendientes();
      $scope.asociado = {};
    };
    
    $scope.editarAsociado = function(id) {      
      $scope.asociado.seleccionado = false;
      marcarCambiosPendientes();      
      $scope.asociado = asociadosDePrueba[id];
      $scope.asociado.seleccionado = true;
      /*Asociado.get({ 'idAsociado': id }, function(asociado) {
        $scope.asociado = asociado;
      });*/      
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