'use strict';

angular.module('cuduApp').controller('LoginCtrl', ['$scope', '$http', 'Usuario', 'Traducciones', function ($scope, $http, Usuario, Traducciones) {

  $scope.error = null;
  $scope.mayusculas = false;

  $scope.detectarMayusculas = function(e) {
    var keyCode = e.keyCode ? e.keyCode : e.which;
    var shiftKey = e.shiftKey ? e.shiftKey : ((keyCode == 16) ? true : false);
    $scope.mayusculas = (((keyCode >= 65 && keyCode <= 90) && !shiftKey) || ((keyCode >= 97 && keyCode <= 122) && shiftKey));
  };

  $scope.noAceptaClausula = function() {
    $scope.error = Traducciones.texto('login.clausula');
  };

  $scope.login = function() {
    if (!$scope.email || !$scope.password) {
      $scope.error = Traducciones.texto('login.emailPasswordVacio');
      return;
    }

    // $scope.error = Traducciones.texto('login.credencialesIncorrectas');
    // grecaptcha.render("recaptcha", {"sitekey": "6Lev6P8SAAAAAJOf3EeaZg3CclR-MUmLRL-ghRch", "theme": "light"});

    Usuario.autenticar($scope.email, $scope.password)
      .success(function(status, data) {

      })
      .error(function(status, data) {
        $scope.error = Traducciones.texto('login.error');
      });
  };
}]);
