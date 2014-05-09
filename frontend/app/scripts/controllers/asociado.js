'use strict';

angular.module('cuduApp')
  .controller('AsociadoCtrl', ['$scope', 'Asociado', function ($scope, Asociado) {
    $scope.grupo = { 'id': 'AK', 'nombre': 'Ain-Karen' };
    
    /*Asociado.get({ }, function(asociados) {
      $scope.asociados = asociados.lista;
    });*/
    
    $scope.asociados = [
      { 'id': 123, 'nombre': 'Mike', 'apellidos': 'Wazowski', 'rama': 'M', 'tipo': 'J', 'fechaNacimiento': '31/01/1982' },
      { 'id': 12617, 'nombre': 'John', 'apellidos': 'Snow', 'rama': 'C', 'tipo': 'K', 'fechaNacimiento': '03/05/1984', dni: '12345678Z' }
    ];
    
    $scope.busqueda = '';
    $scope.tabActivo = 0;

    $scope.seleccionarTab = function(tabId) {
      $scope.tabActivo = tabId;
    };

    $scope.editarAsociado = function(id) {
      Asociado.get({ 'idAsociado': id }, function(asociado) {
        $scope.asociado = asociado;
      });
    };

    $scope.limpiarFiltro = function() {
      $scope.busqueda = '';
    };
  }]);