'use strict';

angular.module('cuduApp').controller('LoginCtrl', ['$scope', '$http', 'Usuario', function ($scope, $http, Usuario) {

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
