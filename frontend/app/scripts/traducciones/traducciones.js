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
    'fechaIncorrecta': 'Data incorrecta',
    'login.clausula': 'Has d\'acceptar la clàusula de confidencialitat i la política de cookies per poder utilitzar Cudú.',
    'login.credencialesIncorrectas': 'El nom d\'usuari o la contrasenya són incorrectes. Torna a introduir i verifica que no ets un robot.',
    'login.error': 'S\'ha produït un error, per favor intenta-ho més tard.',
    'login.emailPasswordVacio': 'Per favor completa el email i la contrasenya abans de continuar.',
    'login.emailVacio': 'Per favor completa el email abans de continuar.',
    'login.debeVerificar': 'Has de verificar que no ets un robot abans de continuar.',
    'login.envioCompleto': 'Hem enviat un e-mail amb les instruccions.',
    'activar.email18': 'Per a activar un usuari es necessari que siga major de 18 anys i tinga una adreça d\'email coneguda.',
    'activar.asociadoInactivo': 'No és possible activar el compte d\'un usuari la fitxa d\'associat del qual estiga eliminada o desactivada',
    'activar.activacionEnCurso': 'No hem enviat un email a l\'associat perquè algú prèviament va sol·licitar el canvi de contrasenya. Per favor, espera 30 minuts i torna a intentar-ho.',
    'activar.deshabilitarUsuarioActual': 'No és possible deshabilitar l\'usuari actual.',
    'activar.errorServidor': 'S\'ha produït un error en el servidor.',
    'asociado.nuevoNombre': '(nou)',
    'menu.actividades': 'Activitats',
    'menu.asociados': 'Associats',
    'menu.cursos': 'Cursos',
    'menu.grupos': 'Agrupaments',
    'menu.liquidaciones': 'Liquidacions',
    'menu.miembros': 'Membres',
    'menu.migrupo': 'Agrupament',
    'menu.misdatos': 'Dades d\'usuari',
    'menu.participantes': 'Participants',
    'menu.permisos': 'Permisos',
    'menu.salir': 'Eixir',
    'cargo.grupo.jefe_de_grupo': 'Cap de Agrupament',
    'cargo.grupo.subjefe_de_grupo': 'Subcap de Agrupament',
    'cargo.grupo.tesorero': 'Tresorer',
    'cargo.grupo.secretario': 'Secretari',
    'cargo.grupo.dinamizador_de_kraal': 'Dinamitzador de Kraal',
    'cargo.grupo.responsable_de_progresion_personal': 'Responsable de Progressió Personal',
    'cargo.grupo.responsable_de_material': 'Responsable de Material',
    'cargo.grupo.consiliario': 'Consiliari',
    'cargo.comite.presidencia': 'Presidència del Comitè',
    'cargo.comite.secretaria': 'Secretaria del Comitè',
    'cargo.comite.tesoreria': 'Tresoreria del Comitè',
    'cargo.comite.vocal': 'Vocal del Comitè',
    'cargo.fev.coordinador_equipo_fe': 'Coordinador Equip de Fe',
    'cargo.fev.voluntario_equipo_fe': 'Voluntari Equip de Fe',
    'cargo.fev.coordinador_equipo_salud': 'Coordinador Equip de Salut',
    'cargo.fev.voluntario_equipo_salud': 'Voluntari Equip de Salut',
    'cargo.fev.coordinador_equipo_social': 'Coordinador Equip Social',
    'cargo.fev.voluntario_equipo_social': 'Voluntari Equip Social',
    'cargo.fev.coordinador_equipo_internacional': 'Coordinador Equip Internacional',
    'cargo.fev.voluntario_equipo_internacional': 'Voluntari Equip Internacional',
    'cargo.fev.coordinador_equipo_desarrollo': 'Coordinador Equip Desenvolupament',
    'cargo.fev.voluntario_equipo_desarrollo': 'Voluntari Equip Desenvolupament',
    'cargo.fev.coordinador_equipo_participacion': 'Coordinador Equip Participació',
    'cargo.fev.voluntario_equipo_participacion': 'Voluntari Equip Participació',
    'cargo.fev.coordinador_equipo_promocion_cultural': 'Coordinador Equip Promoció Cultural',
    'cargo.fev.voluntario_equipo_promocion_cultural': 'Voluntari Equip Promoció Cultural',
    'cargo.fev.coordinador_equipo_medio_ambiente': 'Coordinador Equip Medi Ambient',
    'cargo.fev.voluntario_equipo_medio_ambiente': 'Voluntari Equip Medi Ambient',
    'cargo.fev.voluntario_creequip': 'Voluntari Creequip',
    'cargo.fev.voluntario_equipo_animacion_pedagogica': 'Voluntari Equip Animació Pedagògica',
    'cargo.fev.voluntario_equipo_comunicacion': 'Voluntari Equip Comunicació',
    'cargo.fev.coordinador_equipo_4vents': 'Coordinador Equip 4 Vents',
    'cargo.fev.voluntario_equipo_4vents': 'Voluntari Equip 4 Vents',
    'cargo.fev.formador': 'Formador',
    'cargo.fev.coordinador_de_curso': 'Coordinador de Curs',
    'cargo.fev.tutor_de_formacion': 'Tutor de Formació',
    'cargo.fev.miembro_de_la_mesa_pedagogica': 'Membre de la Taula Pedagògica',
    'cargo.fev.direccion_escola_lluerna': 'Direcció Escola Lluerna',
    'cargo.fev.presidencia_fev': 'Presidència FEV',
    'cargo.fev.vicepresidencia_fev': 'Vicepresidència FEV',
    'impresion.noseleccionados': 'No has seleccionat cap associat',
    'miembros.colaborador' : 'Col·laborador',
    'miembros.formador' : 'Formador',
    'miembros.tufo' : 'TUFO'
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
    'fechaIncorrecta': 'Fecha incorrecta',
    'login.clausula': 'Debes aceptar la clausula de confidencialidad y la política de cookies para poder utilizar Cudú.',
    'login.credencialesIncorrectas': 'El nombre de usuario o la contraseña son incorrectos. Vuelve a introducirlos y verifica que no eres un robot.',
    'login.error': 'Se ha producido un error, por favor intentalo más tarde.',
    'login.emailPasswordVacio': 'Por favor completa el email y el password antes de continuar.',
    'login.emailVacio': 'Por favor completa el email antes de continuar.',
    'login.debeVerificar': 'Tienes que verificar que no eres un robot antes de continuar.',
    'login.envioCompleto': 'Hemos enviado un email con las instrucciones.',
    'activar.email18': 'Para activar un usuario es necesario que sea mayor de 18 años y tenga una dirección de email conocida.',
    'activar.asociadoInactivo': 'No es posible activar la cuenta de un usuario cuya ficha de asociado esta eliminada o desactivada.',
    'activar.activacionEnCurso': 'No hemos enviado un email al asociado porque alguien previamente solicitó el cambio de contraseña. Por favor, espera 30 minutos y vuelve a intentarlo.',
    'activar.deshabilitarUsuarioActual': 'No es posible deshabilitar el usuario actual.',
    'activar.errorServidor': 'Se ha producido un error en el servidor.',
    'asociado.nuevoNombre': '(nuevo)',
    'menu.actividades': 'Actividades',
    'menu.asociados': 'Asociados',
    'menu.cursos': 'Cursos',
    'menu.grupos': 'Grupos',
    'menu.liquidaciones': 'Liquidaciones',
    'menu.miembros': 'Miembros',
    'menu.migrupo': 'Mi Grupo',
    'menu.misdatos': 'Mis Datos',
    'menu.participantes': 'Participantes',
    'menu.permisos': 'Permisos',
    'menu.salir': 'Salir',
    'cargo.grupo.jefe_de_grupo': 'Jefe de Grupo',
    'cargo.grupo.subjefe_de_grupo': 'Subjefe de Grupo',
    'cargo.grupo.tesorero': 'Tesorero',
    'cargo.grupo.secretario': 'Secretario',
    'cargo.grupo.dinamizador_de_kraal': 'Dinamizador de Kraal',
    'cargo.grupo.responsable_de_progresion_personal': 'Responsable de Progresión Personal',
    'cargo.grupo.responsable_de_material': 'Responsable de Material',
    'cargo.grupo.consiliario': 'Consiliario',
    'cargo.comite.presidencia': 'Presidencia del Comité',
    'cargo.comite.secretaria': 'Secretaría del Comité',
    'cargo.comite.tesoreria': 'Tesorería del Comité',
    'cargo.comite.vocal': 'Vocal del Comité',
    'cargo.fev.coordinador_equipo_fe': 'Coordinador Equipo de Fe',
    'cargo.fev.voluntario_equipo_fe': 'Voluntario Equipo de Fe',
    'cargo.fev.coordinador_equipo_salud': 'Coordinador Equipo de Salud',
    'cargo.fev.voluntario_equipo_salud': 'Voluntario Equipo de Salud',
    'cargo.fev.coordinador_equipo_social': 'Coordinador Equipo Social',
    'cargo.fev.voluntario_equipo_social': 'Voluntario Equipo Social',
    'cargo.fev.coordinador_equipo_internacional': 'Coordinador Equipo Internacional',
    'cargo.fev.voluntario_equipo_internacional': 'Voluntario Equipo Internacional',
    'cargo.fev.coordinador_equipo_desarrollo': 'Coordinador Equipo Desarrollo',
    'cargo.fev.voluntario_equipo_desarrollo': 'Voluntario Equipo Desarrollo',
    'cargo.fev.coordinador_equipo_participacion': 'Coordinador Equipo Participación',
    'cargo.fev.voluntario_equipo_participacion': 'Voluntario Equipo Participación',
    'cargo.fev.coordinador_equipo_promocion_cultural': 'Coordinador Equipo Promoción Cultural',
    'cargo.fev.voluntario_equipo_promocion_cultural': 'Voluntario Equipo Promoción Cultural',
    'cargo.fev.coordinador_equipo_medio_ambiente': 'Coordinador Equipo Medio Ambiente',
    'cargo.fev.voluntario_equipo_medio_ambiente': 'Voluntario Equipo Medio Ambiente',
    'cargo.fev.voluntario_creequip': 'Voluntario Creequip',
    'cargo.fev.voluntario_equipo_animacion_pedagogica': 'Voluntario Equipo Animación Pedagógica',
    'cargo.fev.voluntario_equipo_comunicacion': 'Voluntario Equipo Comunicación',
    'cargo.fev.coordinador_equipo_4vents': 'Coordinador Equipo 4 Vents',
    'cargo.fev.voluntario_equipo_4vents': 'Voluntario Equipo 4 Vents',
    'cargo.fev.formador': 'Formador',
    'cargo.fev.coordinador_de_curso': 'Coordinador de Curso',
    'cargo.fev.tutor_de_formacion': 'Tutor de Formación',
    'cargo.fev.miembro_de_la_mesa_pedagogica': 'Miembro de la Mesa Pedagógica',
    'cargo.fev.direccion_escola_lluerna': 'Dirección Escola Lluerna',
    'cargo.fev.presidencia_fev': 'Presidencia FEV',
    'cargo.fev.vicepresidencia_fev': 'Vicepresidencia FEV',
    'impresion.noseleccionados': 'No has seleccionado ningún asociado',
    'miembros.colaborador' : 'Colaborador',
    'miembros.formador' : 'Formador',
    'miembros.tufo' : 'TUFO'
  };

  var lenguajePorDefecto = 'es';

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
      localStorage.setItem(CuduEtc.IDIOMA, lang);
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
