'use strict';

angular.module('cuduApp')
  .controller('MenuCtrl', ['$scope', function($scope) {
    $scope.$on('$routeChangeSuccess', function(e, target) {
      if (target && target.$$route) {
        $scope.controlador = target.$$route.controller;
      }
    });
  }]);
