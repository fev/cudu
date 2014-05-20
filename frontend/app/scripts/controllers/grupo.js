'use strict';

angular.module('cuduApp')
  .controller('GrupoCtrl', ['$scope', 'Grupo', function ($scope, Asociado) {
    $scope.grupo = grupo;
    $scope.tabActivo = 0;    
  }]);