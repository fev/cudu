(function() {
'use strict';

function LoginCtrl($scope, $location, Usuario, Traducciones, Dom, Graficas, focus) {
  $scope.error = null;
  $scope.mayusculas = false;
  $scope.captchaVisible = false;
  $scope.cambioPassword = false;
  $scope.completado = null;

  $scope.etiquetas = {
    'rama': [
      Traducciones.texto('rama.colonia'), 
      Traducciones.texto('rama.manada'), 
      Traducciones.texto('rama.exploradores'),
      Traducciones.texto('rama.expedicion'),
      Traducciones.texto('rama.ruta')
    ]
  };

  var colorLinea = Graficas.colorLinea;
  $scope.colours = _.map(Graficas.rango5azul, function(c) {
    return {
      fillColor: c,
      strokeColor: c,
      pointColor: c,
      pointStrokeColor: "#E4FCFF",
      pointHighlightFill: c,
      pointHighlightStroke: c
    };
  });

  $scope.opcionesHistorico = { scaleFontColor: Graficas.colorEscalaLogin };
  $scope.coloresHistorico = Graficas.coloresHistoricoLogin;

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

  var respuestaCaptchaOk = function($scope) {    
    if (!$scope.captchaVisible) {
      return { ok: true, respuesta: null };
    }
    var respuestaCaptcha = grecaptcha.getResponse();
    if (respuestaCaptcha === "") {
      $scope.error = Traducciones.texto('login.debeVerificar');
      return { ok: false, respuesta: null };
    }
    return { ok: true, respuesta: respuestaCaptcha };
  };

  $scope.login = function() {
    $scope.completado = null;
    if (!$scope.email || !$scope.password) {
      $scope.error = Traducciones.texto('login.emailPasswordVacio');
      return;
    }

    var respuestaCaptcha = respuestaCaptchaOk($scope);
    if (!respuestaCaptcha.ok) {
      return;
    }

    Usuario.autenticar($scope.email, $scope.password, respuestaCaptcha.respuesta)
      .success(function(usuario, status) {
        var lang = Traducciones.establecerLenguaje(usuario.lenguaje);
        Dom.loginCompleto(usuario, lang);
        // TODO Refactorizar código para redirigir a un lugar u otro dependiendo
        // del tipo de usuario. Vér código en app.js#145. El bloque completo de
        // .success se puede extraer a un método aparte en este servicio.
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

  $scope.mostrarOlvidoPassword = function() {
    $scope.mostrarCaptcha();
    $scope.cambioPassword = true;
    $scope.completado = null;
    $scope.error = null;
    focus("txtEmail");
  };

  $scope.ocultarEnvioPassword = function() {
    $scope.cambioPassword = false;
    $scope.completado = null;
    $scope.error = null;
    focus("txtEmail");
  };

  $scope.enviarCambioPassword = function() {    
    if (!$scope.email) {
      $scope.error = Traducciones.texto('login.emailVacio');
      focus("txtEmail");
      return;
    }

    var respuestaCaptcha = respuestaCaptchaOk($scope);
    if (!respuestaCaptcha.ok) {
      return;
    }

    Usuario.resetPassword($scope.email, respuestaCaptcha.respuesta);
    $scope.cambioPassword = false;
    $scope.error = null;
    $scope.completado = Traducciones.texto('login.envioCompleto');
  };

  $scope.__dbgAutoLogin = function(email) {
    $scope.email = email;
    $scope.password = 'wackamole';
    $scope.login();
  };
}

angular.module('cuduApp').controller('LoginCtrl', ['$scope', '$location', 'Usuario', 'Traducciones', 'Dom', 'Graficas', 'focus', LoginCtrl]);

}());
