'use strict';

angular.module('cuduApp')
  .controller('TecnicoFevCtrl', ['$q', '$scope', '$routeParams', 'Asociado', function ($q, $scope, $routeParams, Asociado) {
  	$scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4'];
  	$scope.grupoPorDefecto = $scope.grupos[0];

    $scope.asociados = { list : [] } ;
  	if($scope.asociados.list.length == 0)
      $scope.asociados.list = Asociado.query();

  	$scope.asociado = {};
    $q.when($scope.asociados.list.$promise).then(function () {
      if(typeof($routeParams.id) !== 'undefined')
        $scope.asociado = $filter('byId')($scope.asociados.list, $routeParams.id);
        $scope.asociado.seleccionado = true;
    });

  }]);