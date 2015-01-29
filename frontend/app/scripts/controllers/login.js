(function() {
'use strict';

function LoginCtrl($scope, $location, Usuario, Traducciones, Dom, Graficas) {
  $scope.error = null;
  $scope.mayusculas = false;
  $scope.captchaVisible = false;

  $scope.etiquetas = {
    'rama': ['colonia', 'manada', 'exploradores', 'expedición', 'ruta']
  };

  var colores = ["#E0F7FA", "#B2EBF2", "#80DEEA", "#4DD0E1", "#00ACC1"];
  var colorLinea = "rgba(148,159,177,1)";
  $scope.colours = _.map(colores, function(c) {
    return {
      fillColor: c,
      strokeColor: c,
      pointColor: c,
      pointStrokeColor: "#E4FCFF",
      pointHighlightFill: c,
      pointHighlightStroke: c
    };
  });

  $scope.opcionesHistorico = {
    scaleFontColor: "#80DEEA"
  };
  $scope.coloresHistorico = [
    { fillColor: "rgba(20, 20, 20, 0.1)", strokeColor: "#B2EBF2" },
    { fillColor: "rgba(20, 20, 20, 0.1)", strokeColor: "#80DEEA" },
    { fillColor: "rgba(20, 20, 20, 0.1)", strokeColor: "#76FF03" }
  ];

  Graficas.login().success(function(data) {
    $scope.graficas = data;
  });

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
        Dom.loginCompleto(usuario);
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
    $scope.password = 'wackamole';
    $scope.login();
  };
}

angular.module('cuduApp').controller('LoginCtrl', ['$scope', '$location', 'Usuario', 'Traducciones', 'Dom', 'Graficas', LoginCtrl]);

}());
