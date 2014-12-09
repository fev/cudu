'use strict';

angular.module('cuduApp')
  .controller('MenuCtrl', ['$scope', '$rootScope', function($scope, $rootScope) {
    $scope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $scope.controlador = target.$$route.controller;
        $rootScope.seccion = target.$$route.seccion;
      }
    });
  }]);
