'use strict';

angular.module('cuduApp').controller('LoginCtrl', ['$scope', '$http', '$location', 'Usuario', 'Traducciones', function ($scope, $http, $location, Usuario, Traducciones) {

  $scope.error = null;
  $scope.mayusculas = false;
  $scope.captchaVisible = false;

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

    var respuestaCaptcha = null;
    if ($scope.captchaVisible) {
      respuestaCaptcha = grecaptcha.getResponse();
      if (respuestaCaptcha === "") {
        $scope.error = Traducciones.texto('login.debeVerificar');
        return;
      }
    }

    Usuario.autenticar($scope.email, $scope.password, respuestaCaptcha)
      .success(function(usuario, status) {
        // No muy elegante, pero es rápido y sólo se ejecuta una vez
        $("#lnkUsuarioActual").text(usuario.nombreCompleto);
        $('#cuduNav, #cuduNavBg').removeClass("hidden");
        $location.path("/");
      })
      .error(function(data, status) {
        if (status == 403) {
          $scope.error = Traducciones.texto("login.credencialesIncorrectas");
        } else {
          $scope.error = Traducciones.texto('login.error');
        }
        $scope.mostrarCaptcha();
      });
  };

  $scope.mostrarCaptcha = function() {
    if ($scope.captchaVisible) {
      grecaptcha.reset();
    } else {
      grecaptcha.render("recaptcha", {"sitekey": "6Lev6P8SAAAAAJOf3EeaZg3CclR-MUmLRL-ghRch", "theme": "light"});
    }
    $scope.captchaVisible = true;
  };

  $scope.__dbgAutoLogin = function(email) {
    $scope.email = email;
    $scope.password = 'test';
    $scope.login();
  };
}]);
