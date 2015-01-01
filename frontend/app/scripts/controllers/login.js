'use strict';

angular.module('cuduApp').controller('LoginCtrl', ['$scope', '$http', 'Usuario', function ($scope, $http, Usuario) {

  $scope.error = null;
  $scope.mayusculas = false;

  $scope.detectarMayusculas = function(e) {
    var keyCode = e.keyCode ? e.keyCode : e.which;
    var shiftKey = e.shiftKey ? e.shiftKey : ((keyCode == 16) ? true : false);
    $scope.mayusculas = (((keyCode >= 65 && keyCode <= 90) && !shiftKey) || ((keyCode >= 97 && keyCode <= 122) && shiftKey));
  };

  $scope.login = function() {
    var email = "baden@scoutsfev.org";
    var password = "1234";

    Usuario.autenticar(email, password)
      .success(function(status, data) {

      })
      .error(function(status, data) {

      });
  };
}]);
