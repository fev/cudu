'use strict';

angular.module('cuduApp')
  .controller('GrupoCtrl', ['$scope', 'Grupo', function ($scope, Grupo) {
    $scope.grupo = Grupo.get();
    $scope.tabActivo = 0;    
  }]);
