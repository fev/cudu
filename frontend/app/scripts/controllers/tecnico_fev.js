'use strict';

angular.module('cuduApp')
  .controller('TecnicoFevCtrl', ['$q', '$scope', '$routeParams', 'Asociado', function ($q, $scope, $routeParams, Asociado) {
  	$scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4'];
  	$scope.grupoPorDefecto = $scope.grupos[0];

    $scope.asociados = { list : [] , total : []} ;
  	if($scope.asociados.list.length == 0)
      $scope.asociados.total = Asociado.query();

  	$scope.asociado = {};
    $scope.inactivos = false;
    $q.when($scope.asociados.total.$promise).then(function () {
      filtraAsociados($scope.inactivos);
      // if(typeof($routeParams.id) !== 'undefined')
      //   $scope.asociado = $filter('byId')($scope.asociados.list, $routeParams.id);
      //   $scope.asociado.seleccionado = true;
    });

    var filtraAsociados = function (incluirInactivos) {
      var asociados = $scope.asociados.total;
      if(!incluirInactivos) {
        asociados = $.grep(asociados, function (a) { return a.activo; });
      }

      $scope.asociados.list = asociados.slice(0, 10); 
    }

    $scope.mostrarInactivos = function () {
      $scope.inactivos = !$scope.inactivos;
      filtraAsociados($scope.inactivos);
    };

  }]);