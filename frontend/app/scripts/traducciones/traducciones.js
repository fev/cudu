'use strict';

// TODO Add ngCookies here
var traducciones = angular.module('cuduTraducciones', []);

traducciones.factory('Traducciones', [function() {
  var ca = {
    'login.clausula': 'Has d\'acceptar la clàusula de confidencialitat i la política de cookies per poder utilitzar Cudú.',
    'login.credencialesIncorrectas': 'El nom d\'usuari o la contrasenya són incorrectes. Torna a introduir i verifica que no ets un robot.',
    'login.error': 'S\'ha produït un error, si us plau intenta-ho més tard.',
    'login.emailPasswordVacio': 'Si us plau completa el email i la contrasenya abans de continuar.'
  };
  var es = {
    'login.clausula': 'Debes aceptar la clausula de confidencialidad y la política de cookies para poder utilizar Cudú.',
    'login.credencialesIncorrectas': 'El nombre de usuario o la contraseña son incorrectos. Vuelve a introducirlos y verifica que no eres un robot.',
    'login.error': 'Se ha producido un error, por favor intentalo más tarde.',
    'login.emailPasswordVacio': 'Por favor completa el email y el password antes de continuar.'
  };

  var lenguajePorDefecto = 'ca';

  return {
    tr: { 'ca': ca, 'es': es },
    lenguaje: lenguajePorDefecto,

    texto: function(clave, lenguaje) {
      var lang = lenguaje || this.lenguaje;
      var ln = this.tr[lang];
      if (ln) {
        var txt = ln[clave];
        if (txt) {
          return txt;
        }
        if (lang !== lenguajePorDefecto) {
          this.warn("Traducción no encontrada: " + clave + " (" + lang + "), fallback: " + lenguajePorDefecto);
          return this.texto(clave, lenguajePorDefecto);
        }
      }
      this.warn("Traducción no encontrada: " + clave + " (" + lang + ")");
      return '';
    },

    warn: function(msg) {
      if (console) {
        console.warn(msg);
      }
    }
  };
}]);
