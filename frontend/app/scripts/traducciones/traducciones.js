(function() {
'use strict';

var traducciones = angular.module('cuduTraducciones', []);

traducciones.factory('Traducciones', ['$http', 'CuduEtc', function($http, CuduEtc) {
  var ca = {
    'asociacion.0': 'Scouts de Alicante (SdA)',
    'asociacion.1': 'Scouts de Castelló (SdC)',
    'asociacion.2': 'Moviment Escolta de València (MEV)',
    'tipo.J': 'Jove',
    'tipo.K': 'Kraal',
    'tipo.C': 'Comitè',
    'rama.colonia': 'Castors',
    'rama.manada': 'Llops',
    'rama.exploradores': 'Exploradors',
    'rama.expedicion': 'Pioners',
    'rama.ruta': 'Companys',
    'rama.cualquiera': '(qualsevol)',
    'rama.ninguna': '(cap)',
    'activo.true': 'Actiu',
    'activo.false': 'Inactiu',
    'asistente.D': 'Dubte',
    'asistente.N': 'No Vindrà',
    'asistente.S': 'Vindrà',
    'asistente.P': 'Pagat',
    'asistente.B': 'Becat',
    'nunca': 'Mai',
    'login.clausula': 'Has d\'acceptar la clàusula de confidencialitat i la política de cookies per poder utilitzar Cudú.',
    'login.credencialesIncorrectas': 'El nom d\'usuari o la contrasenya són incorrectes. Torna a introduir i verifica que no ets un robot.',
    'login.error': 'S\'ha produït un error, per favor intenta-ho més tard.',
    'login.emailPasswordVacio': 'Per favor completa el email i la contrasenya abans de continuar.',
    'login.debeVerificar': 'Has de verificar que no ets un robot abans de continuar.',
    'activar.email18': 'Per a activar un usuari es necessari que siga major de 18 anys i tinga una adreça d\'email coneguda.',
    'activar.asociadoInactivo': 'No és possible activar el compte d\'un usuari la fitxa d\'associat del qual estiga eliminada o desactivada',
    'activar.activacionEnCurso': 'No hem enviat un email a l\'associat perquè algú prèviament va sol·licitar el canvi de contrasenya. Per favor, espera 30 minuts i torna a intentar-ho.',
    'activar.deshabilitarUsuarioActual': 'No és possible deshabilitar l\'usuari actual.',
    'activar.errorServidor': 'S\'ha produït un error en el servidor.'
  };
  var es = {
    'asociacion.0': 'Scouts de Alicante (SdA)',
    'asociacion.1': 'Scouts de Castelló (SdC)',
    'asociacion.2': 'Moviment Escolta de València (MEV)',
    'tipo.J': 'Joven',
    'tipo.K': 'Kraal',
    'tipo.C': 'Comité',
    'rama.colonia': 'Castores',
    'rama.manada': 'Lobatos',
    'rama.exploradores': 'Exploradores',
    'rama.expedicion': 'Pioneros',
    'rama.ruta': 'Compañeros',
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

  var lenguajeDelNavegador = function() {
    var lang = navigator.language || navigator.userLanguage || lenguajePorDefecto;
    lang = lang.split('-')[0]; // es-ES, RFC-4646
    if (lang === 'es' || lang === 'ca') {
      return lang;
    }
    return lenguajePorDefecto;
  };

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

    establecerLenguaje: function(codigo) {
      var lang = codigo || lenguajeDelNavegador();
      moment.locale(lang);
      this.lenguaje = lang;
      $http.defaults.headers.common['Accept-Language'] = lang;
      localStorage.setItem(CuduEtc.IDIOMA, codigo);
      return lang;
    },

    warn: function(msg) {
      if (console) {
        console.warn(msg);
      }
    }
  };
}]);

})();
