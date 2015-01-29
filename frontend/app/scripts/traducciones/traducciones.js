'use strict';

// TODO Add ngCookies here
var traducciones = angular.module('cuduTraducciones', []);

traducciones.factory('Traducciones', [function() {
  var ca = {
    'tipo.J': 'Jove',
    'tipo.K': 'Kraal',
    'tipo.C': 'Comitè',
    'rama.colonia': 'Colonia',
    'rama.manada': 'Manada',
    'rama.exploradores': 'Exploradores',
    'rama.expedicion': 'Expedición',
    'rama.ruta': 'Ruta',
    'rama.cualquiera': '(cualsevol)',
    'rama.ninguna': '(cap)',
    'activo.true': 'Actiu',
    'activo.false': 'Inactiu',
    'asistente.D': 'Duda',
    'asistente.N': 'No Viene',
    'asistente.S': 'Viene',
    'asistente.P': 'Pagado',
    'asistente.B': 'Becado',
    'nunca': 'Nunca',
    'login.clausula': 'Has d\'acceptar la clàusula de confidencialitat i la política de cookies per poder utilitzar Cudú.',
    'login.credencialesIncorrectas': 'El nom d\'usuari o la contrasenya són incorrectes. Torna a introduir i verifica que no ets un robot.',
    'login.error': 'S\'ha produït un error, si us plau intenta-ho més tard.',
    'login.emailPasswordVacio': 'Si us plau completa el email i la contrasenya abans de continuar.',
    'login.debeVerificar': 'Has de verificar que no ets un robot abans de continuar.',
    'activar.email18': 'Para activar un usuario es necesario que sea mayor de 18 años y tenga una dirección de email conocida.',
    'activar.asociadoInactivo': 'No es posible activar la cuenta de un usuario cuya ficha de asociado esta eliminada o desactivada.',
    'activar.activacionEnCurso': 'No hemos enviado un email al asociado porque alguien previamente solicitó el cambio de contraseña. Por favor, espera 30 minutos y vuelve a intentarlo.',
    'activar.deshabilitarUsuarioActual': 'No es posible deshabilitar el usuario actual.',
    'activar.errorServidor': 'Se ha producido un error en el servidor.'
  };
  var es = {
    'tipo.J': 'Joven',
    'tipo.K': 'Kraal',
    'tipo.C': 'Comité',
    'rama.colonia': 'Colonia',
    'rama.manada': 'Manada',
    'rama.exploradores': 'Exploradores',
    'rama.expedicion': 'Expedición',
    'rama.ruta': 'Ruta',
    'rama.cualquiera': '(cualquiera)',
    'rama.ninguna': '(ninguna)',
    'activo.true': 'Activo',
    'activo.false': 'Inactivo',
    'asistente.D': 'Duda',
    'asistente.N': 'No Viene',
    'asistente.S': 'Viene',
    'asistente.P': 'Pagado',
    'asistente.B': 'Becado',
    'nunca': 'Nunca',
    'login.clausula': 'Debes aceptar la clausula de confidencialidad y la política de cookies para poder utilizar Cudú.',
    'login.credencialesIncorrectas': 'El nombre de usuario o la contraseña son incorrectos. Vuelve a introducirlos y verifica que no eres un robot.',
    'login.error': 'Se ha producido un error, por favor intentalo más tarde.',
    'login.emailPasswordVacio': 'Por favor completa el email y el password antes de continuar.',
    'login.debeVerificar': 'Tienes que verificar que no eres un robot antes de continuar.',
    'activar.email18': 'Para activar un usuario es necesario que sea mayor de 18 años y tenga una dirección de email conocida.',
    'activar.asociadoInactivo': 'No es posible activar la cuenta de un usuario cuya ficha de asociado esta eliminada o desactivada.',
    'activar.activacionEnCurso': 'No hemos enviado un email al asociado porque alguien previamente solicitó el cambio de contraseña. Por favor, espera 30 minutos y vuelve a intentarlo.',
    'activar.deshabilitarUsuarioActual': 'No es posible deshabilitar el usuario actual.',
    'activar.errorServidor': 'Se ha producido un error en el servidor.'
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
