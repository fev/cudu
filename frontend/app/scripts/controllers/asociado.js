'use strict';

// TODO Mover a constantes de angular
var estados = {
  LIMPIO: 0,
  GUARDANDO: 1,
  OK: 2,
  ERROR: 3,
  VALIDACION: 4,
  CUSTOM: 5
};

angular.module('cuduApp')
  .controller('AsociadoCtrl', ['$scope', '$routeParams', '$location', '$window', '$filter', 'Asociado', 'Grupo', 'Usuario', 'EstadosFormulario', 'Traducciones', 'Notificaciones', 'Ficha',
      function ($scope, $routeParams, $location, $window, $filter, Asociado, Grupo, Usuario, EstadosFormulario, Traducciones, Notificaciones, Ficha) {

    $scope.grupo = {};
    $scope.asociados = [];

    Usuario.obtenerActual().then(function(u) {
      $scope.grupo = u.grupo;
      if (u.tipo === 'T' && $routeParams.id) {
        document.getElementById("panelEdicion").hidden=false;
        document.getElementById("logo-bienvenida").hidden=true;
        $scope.esTecnico = true;
        $scope.editarAsociadoTecnico($routeParams.id);
      } else {
        Asociado.query(function(asociados) {
          $scope.asociados = asociados.content;
        });
      }
    });

    $scope.fichas = [];
    Ficha.queryAll(0, function (data) {
      $scope.fichas = _.filter(data, function (f) { return f.tipoFicha == 0 && f.tipoEntidad == 0; });
      $scope.autorizaciones = _.filter(data, function (f) { return f.tipoFicha == 1 && f.tipoEntidad == 0; });
    }, function () { });

    var generarAsociadoVacio = function() {
      var grupo = $scope.grupo || { id: -1, municipio: '', codigoPostal: '' };
      return {
        'grupoId': grupo.id,
        'ambitoEdicion': 'G',
        'puntosCovol': 0,
        'municipio': grupo.municipio,
        'codigoPostal': grupo.codigoPostal
      };
    };

    $scope.asociado = generarAsociadoVacio();
    $scope.marcados = [];

    $scope.estado = EstadosFormulario.LIMPIO;
    $scope.erroresValidacion = [];

    $scope.tabActivo = 0;
    $scope.busqueda = '';
    $scope.filtro = { tipo: '', eliminados: false, certificadoDelitosSexuales: 'nada' };
    $scope.columnas = { rama: true, direccion: false, contacto: false };
    $scope.orden = 'apellidos';
    $scope.info = { mensaje: ''};

    $scope.modal = { eliminar: false };

    document.getElementById("logo-bienvenida").hidden=false;

    var emitirAsociadoEditandose = function(asociado) {
      $scope.$emit('asociado.editar', asociado);
    };

    $scope.seleccionarTab = function(tabId) {
      $scope.tabActivo = tabId;
    };

    $scope.limpiarFiltro = function() {
      $scope.busqueda = '';
      $scope.filtro = { tipo: '', eliminados: false, certificadoDelitosSexuales: 'nada' };
    };

    $scope.filtrar = function(asociado) {
      var f = $scope.filtro || {};
      var sinFiltroDeRama = !(f.ramaColonia || f.ramaManada ||
        f.ramaExploradores || f.ramaExpedicion || f.ramaRuta);

      return ((f.tipo === '' || asociado.tipo === f.tipo) &&
              (sinFiltroDeRama || (f.ramaColonia && asociado.ramaColonia) ||
                  (f.ramaManada && asociado.ramaManada) ||
                  (f.ramaExploradores && asociado.ramaExploradores) ||
                  (f.ramaExpedicion && asociado.ramaExpedicion) || 
                  (f.ramaRuta && asociado.ramaRuta)) &&
              (f.eliminados || asociado.activo) &&
              (f.certificadoDelitosSexuales=== 'nada' || (String(asociado.certificadoDelitosSexuales)===f.certificadoDelitosSexuales && (asociado.tipo==='K' || asociado.tipo==='C')))
            );
    };

    $scope.activarFiltro = function(filtro, valor, porDefecto) {
      var valorPorDefecto = typeof porDefecto !== 'undefined' ? porDefecto : '';
      if ($scope.filtro[filtro] === valor) {
        $scope.filtro[filtro] = valorPorDefecto;
      } else {
        $scope.filtro[filtro] = valor;
      }
    };

    $scope.nuevo = function() {

      marcarCambiosPendientes();
      document.getElementById("panelEdicion").hidden=false;
      document.getElementById("logo-bienvenida").hidden=true;
      $scope.asociado = generarAsociadoVacio();
    };

    $scope.editar = function(id) {

      marcarCambiosPendientes();
      document.getElementById("panelEdicion").hidden=false;
      document.getElementById("logo-bienvenida").hidden=true;
      $scope.posAsociado = _.findIndex($scope.asociados, function(a) { return a ? a.id === id : false; });
      $scope.original = $scope.asociados[$scope.posAsociado];
      if ($scope.original.cambiosPendientes)
      {
        // Si previamente no se han guardado los cambios, evitamos recargar desde
        // el servidor y dejamos el asociado original con cambios pendientes.
        $scope.asociado = $scope.original;
        emitirAsociadoEditandose($scope.original);
      } else {
        $scope.obtenerAsociado(id);
      }
    };

    $scope.obtenerAsociado = function(id) {
      Asociado.get({ 'id': id }, function(asociado) {
          asociado.marcado = $scope.original.marcado;
          asociado.guardado = $scope.original.guardado;
          asociado.puntosCovol = calcularPuntosCovol(asociado);
          $scope.asociado = asociado;
          $scope.asociados[$scope.posAsociado] = asociado;
          if(!$scope.esTecnico) {
            emitirAsociadoEditandose(asociado);
          }
        });
    };

    $scope.guardar = function(id) {
      $scope.estado = EstadosFormulario.GUARDANDO;

      var asociadoNuevo = false;
      var guardar = Asociado.actualizar;
      if (!$scope.asociado.id) {
        guardar = Asociado.crear;
        asociadoNuevo = true;
      }

      // Validamos si el usuario actual puede acceder a la rama. Dicha validación ya se realiza en
      // el lado del servidor, pero la respuesta estándar es devolver 403 sin detalles. Preferimos
      // hacer el check aqui para evitar responder con demasiado detalle en respuestas de seguridad.
      var restricciones = Usuario.usuario.restricciones || {};
      if (restricciones.noPuedeEditarOtrasRamas) {
          var u = Usuario.usuario;
          var a = $scope.asociado;
          var mismasRamas = (u.ramaColonia && a.ramaColonia) ||
            (u.ramaManada && a.ramaManada) ||
            (u.ramaExploradores && a.ramaExploradores) ||
            (u.ramaExpedicion && a.ramaExpedicion) ||
            (u.ramaRuta && a.ramaRuta);
          if (!mismasRamas) {
            $scope.estado = EstadosFormulario.VALIDACION;
            var ramas = [];
            if (u.ramaColonia) { ramas.push(Traducciones.texto('rama.colonia')); }
            if (u.ramaManada) { ramas.push(Traducciones.texto('rama.manada')); }
            if (u.ramaExploradores) { ramas.push(Traducciones.texto('rama.exploradores')); }
            if (u.ramaExpedicion) { ramas.push(Traducciones.texto('rama.expedicion')); }
            if (u.ramaRuta) { ramas.push(Traducciones.texto('rama.ruta')); }
            $scope.erroresValidacion = [{
              campo: Traducciones.texto('rama'),
              mensaje: Traducciones.texto('asociado.sinPermisosRama') + ramas.join(', ') + "."
            }];
            return;
          }
      }

      guardar({ id: id }, $scope.asociado).$promise.then(function(asociadoGuardado) {
        $scope.estado = EstadosFormulario.OK;
        asociadoGuardado.guardado = true;
        asociadoGuardado.puntosCovol = calcularPuntosCovol(asociadoGuardado);
        $scope.asociado = asociadoGuardado;
        if (!asociadoNuevo) {
          var pos = _.findIndex($scope.asociados, function(a) { return a ? a.id === id : false; });
          $scope.asociados[pos] = asociadoGuardado;
        } else {
          $scope.asociados.unshift(asociadoGuardado);
        }
        $scope.formAsociado.$setPristine();
      }, function(respuesta) {
        if (respuesta.status == 400) {
          $scope.estado = EstadosFormulario.VALIDACION;
          $scope.erroresValidacion = respuesta.data || [];
        } else {
          $scope.estado = EstadosFormulario.ERROR;
        }
      });
    };

    $scope.activar = function(id, activar) {
      var metodo = Asociado.desactivar;
      if (activar) { metodo = Asociado.activar; }
      metodo({id: id}, {}, function(data, status) {
        $scope.asociado.activo = activar;
        $scope.asociado.cambiosPendientes = false;
        $scope.formAsociado.$setPristine();
      }, function(data, status) {
        $scope.estado = EstadosFormulario.ERROR;
      });
    };

    $scope.eliminar = function(id) {
      $scope.modal.eliminar = false;
      Asociado.delete({ id: id }, function() {
        _.remove($scope.asociados, function(a) { return a ? a.id === id : false; });
        $scope.asociado = {};
      }, function() {
        $scope.estado = EstadosFormulario.ERROR;
      });
    };

    $scope.darDeBajaSeleccionados = function() {
      var f = factoriaEdicionMultiple(function(asociado) { asociado.activo = false; }, {
        progreso: Traducciones.texto('multiple.baja.progreso'),
        completado: Traducciones.texto('multiple.baja.completado'),
        errorServidor: Traducciones.texto('multiple.baja.errorServidor')
      });
      if (f) {
        Asociado.desactivarSeleccionados({}, f.marcados, f.completado, f.error);
      }
    };

    $scope.marcar = function(asociado, e) {
      if (!asociado.marcado) {
        asociado.marcado = true;
        $scope.marcados.push(asociado.id);
      } else {
        asociado.marcado = false;
        _.remove($scope.marcados, function(i) { return i === asociado.id; });
      }
    };

    $scope.establecerSexo = function(sexo) {
      $scope.asociado.sexo = sexo;
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerTipo = function(tipo) {
      $scope.asociado.tipo = tipo;
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerRama = function(rama) {
      /* var tipo = $scope.asociado.tipo;
      if (!tipo || tipo === 'J') {
        $scope.asociado.ramaColonia = false;
        $scope.asociado.ramaManada = false;
        $scope.asociado.ramaExpedicion = false;
        $scope.asociado.ramaExploradores = false;
        $scope.asociado.ramaRuta = false;
        $scope.asociado[rama] = true;
      } else { */
        var actual = $scope.asociado[rama];
        if (actual) {
          $scope.asociado[rama] = false;
        } else {
          $scope.asociado[rama] = true;
        }
      // }
      $scope.formAsociado.$setDirty();
    };

    $scope.establecerEstiloGuardado = function() {
      if ($scope.estado === EstadosFormulario.LIMPIO) { return 'btn-default'; }
      if ($scope.estado === EstadosFormulario.GUARDANDO) { return 'btn-progress'; }
      if ($scope.estado === EstadosFormulario.OK) { return 'btn-success'; }
      if ($scope.estado === EstadosFormulario.ERROR) { return 'btn-error'; }
      if ($scope.estado === EstadosFormulario.VALIDACION) { return 'btn-warning'; }
      return 'btn-default';
    };

    $scope.copiarDatosContacto = function(destino) {
      var algunoModificado = false;
      var telefono = $scope.asociado['telefonoMovil'];
      if (!_.isEmpty(telefono)) {
        $scope.asociado['telefono' + destino] = telefono;
        algunoModificado = true;
      }
      var email = $scope.asociado['emailContacto'];
      if (!_.isEmpty(email)) {
        $scope.asociado['email' + destino] = email;
        algunoModificado = true;
      }
      if (algunoModificado === true) {
        $scope.formAsociado.$setDirty();
      }
    };

    $scope.obtenerNombreCompleto = function(asociado) {
      if (asociado && (asociado.nombre || asociado.apellidos)) {
        return [asociado.nombre, asociado.apellidos].join(' ');
      }
      return Traducciones.texto('asociado.nuevoNombre');
    };

    $scope.mostrarColumna = function(nombre) {
      var columnas = $scope.columnas || {};
      if (columnas[nombre]) {
        columnas[nombre] = false;
      } else {
        columnas[nombre] = true;
      }
    };

    $scope.cssRadio = function(valor) {
      if (valor) {
        return "fa-dot-circle-o";
      } else {
        return "fa-circle-o";
      }
    };

    $scope.cssCheck = function(valor) {
      if (valor) {
        return "fa-check-square-o";
      } else {
        return "fa-square-o";
      }
    };

    $scope.asignarCargo = function(cargoId) {
      Asociado.asignarCargo({ id: $scope.asociado.id, cargoId: cargoId }, {}, function(cargoGuardado) {
        $scope.asociado.cargos = $scope.asociado.cargos || [];
        $scope.asociado.cargos.unshift(cargoGuardado);
      });
    };

    $scope.asignarCargoCustom = function(evt) {
      if (evt.keyCode != 13) {
        return;
      }
      Asociado.asignarCargoCustom({ id: $scope.asociado.id }, $scope.nuevoCargo, function(cargoGuardado) {
        $scope.asociado.cargos = $scope.asociado.cargos || [];
        $scope.asociado.cargos.unshift(cargoGuardado);
      });
      $scope.nuevoCargo = '';
    };

    $scope.eliminarCargo = function(cargoId) {
      Asociado.eliminarCargo({ id: $scope.asociado.id, cargoId: cargoId }, function() {
        var pos = _.findIndex($scope.asociado.cargos, function(c) { return c.id === cargoId; });
        $scope.asociado.cargos.splice(pos, 1);
      });
    };

    $scope.comprobarAlertaRama = function(a) {
      if (a.tipo !== 'J') {
        return;
      }
      var algunaRama = (a.ramaColonia || a.ramaManada || a.ramaExpedicion || a.ramaExploradores || a.ramaRuta);
      var rama = calcularRamaRecomendada(a.fechaNacimiento);
      if (!algunaRama) {
        if (rama != null) {
          a['rama' + rama] = true;
        }
        return;
      }
      if (rama == null) { return; }
      if (!a['rama' + rama]) {
        a.alertaRama = Traducciones.texto('rama.' + rama.toLowerCase());
      } else {
        a.alertaRama = null;
      }
    };

    $scope.generarFicha = function(id) {
     if ($scope.marcados.length == 0) {
       $scope.mensajeCustom(Traducciones.texto('impresion.noseleccionados'));
       return;
     }
     Ficha.generar(id, $scope.marcados, null,
     function (data) {
      var url = _.template('/api/ficha/<%= nombre %>/descargar');
      $window.location.assign(url({ 'nombre' : data.nombre }));
     },
     function (data, status) {
      $scope.estado = EstadosFormulario.ERROR;
     });
    };

    $scope.mensajeCustom = function(msn) {
      $scope.estado = EstadosFormulario.CUSTOM;
      $scope.info.mensaje = msn;
    };

    $scope.imprimirTodos = function() {
      $scope.imprimirListado($scope.asociados, Traducciones.texto('impresion.titulo.listadoCompleto') );
    };

    $scope.imprimirVisibles = function() {
      var f = $filter('filter');
      var visibles = f(f($scope.asociados, $scope.busqueda), function(a) {return $scope.filtrar(a); });
      switch($scope.filtro.tipo) {
          case 'J':
              $scope.imprimirListado(visibles, Traducciones.texto('impresion.titulo.listadoVisiblesJoves'));
              break;
          case 'K':
              $scope.imprimirListado(visibles, Traducciones.texto('impresion.titulo.listadoVisiblesKraal'));
              break;
          case 'C':
              $scope.imprimirListado(visibles, Traducciones.texto('impresion.titulo.listadoVisiblesComite'));
              break;
          default:
              $scope.imprimirListado(visibles, Traducciones.texto('impresion.titulo.listadoVisiblesOtro'));
      }
    };

    $scope.imprimirListado = function(asociados,titulo) {
      if(asociados.length==0){
         alert(Traducciones.texto('impresion.novisibles'));
         return;
      }
      var columnas = ["nombre"];
      if($scope.columnas.contacto) {
        columnas.push("telefono");
        columnas.push("email");
      }
      if($scope.columnas.direccion) columnas.push("direccion");
      if($scope.columnas.rama) columnas.push("rama");

      Ficha.listado(_.map(asociados, "id"), columnas, titulo,
      function(data) {
        var url = _.template('/api/ficha/<%= nombre %>/descargar');
        $window.location.assign(url({ 'nombre' : data.nombre }));
      },
      function(data, status) { $scope.estado = EstadosFormulario.ERROR; });
    };

    $scope.cambiarRama = function(rama) {
      // rama:='{ "colonia": true }
      var f = factoriaEdicionMultiple(function(asociado) {
        asociado.ramaColonia = false;
        asociado.ramaManada = false;
        asociado.ramaExpedicion = false;
        asociado.ramaExploradores = false;
        asociado.ramaRuta = false;
        // colonia -> ramaColonia;
        var ramaDestino = 'rama' + rama.charAt(0).toUpperCase() + rama.slice(1);
        asociado[ramaDestino] = true;
      }, {
        progreso: Traducciones.texto('multiple.rama.progreso'),
        completado: Traducciones.texto('multiple.rama.completado'),
        errorServidor: Traducciones.texto('multiple.rama.errorServidor')
      });
      if (f) {
        // rama: { 'colonia': true }
        var cuerpo = { asociados: f.marcados, rama: { } };
        cuerpo.rama[rama] = true;
        Asociado.cambiarRama({}, cuerpo, f.completado, f.error);
      }
    };

    $scope.cambiarTipo = function(tipo) {
      var f = factoriaEdicionMultiple(function(asociado) {
        asociado.tipo = tipo;
      }, {
        progreso: Traducciones.texto('multiple.tipo.progreso'),
        completado: Traducciones.texto('multiple.tipo.completado'),
        errorServidor: Traducciones.texto('multiple.tipo.errorServidor')
      });
      if (f) {
        Asociado.cambiarTipo({ }, { asociados: f.marcados, tipo: tipo }, f.completado, f.error);
      }
    };

    $scope.volver = function() {
        $location.path('/tecnico/asociados');
    };

    $scope.editarAsociadoTecnico = function(id) {
        Asociado.get({ 'id': id }, function(asociado) {
          $scope.asociado = asociado;
          Grupo.get({ 'id': asociado.grupoId }, function(grupo) {
            $scope.grupo = grupo;
          });
        });
    };

    var calcularRamaRecomendada = function(fechaNacimiento) {
      var edad = Usuario.calcularEdad(fechaNacimiento);
      if (edad <= 7) { return 'Colonia'; }
      if (edad > 7 && edad <= 10) { return 'Manada'; }
      if (edad > 10 && edad <= 13) { return 'Exploradores'; }
      if (edad > 13 && edad <= 16) { return 'Expedicion'; }
      if (edad > 16) { return 'Ruta'; }
      return null;
    };

    var calcularPuntosCovol = function(asociado) {
      if (typeof asociado === 'undefined') {
        return 0;
      }
      var cargos = asociado.cargos || [];
      var total = 0;
      for (var i = 0; i < cargos.length; i++) {
        total = total + cargos[i].puntos;
      }
      return total;
      // return _.reduce(cargos, function(a,c) { return a + c.puntos }, 0);
    };

    var marcarCambiosPendientes = function() {
      if ($scope.asociado.id && $scope.formAsociado.$dirty) {
        $scope.asociado.estadoFrm = EstadosFormulario.VALIDACION;
        $scope.asociado.cambiosPendientes = true;
        $scope.formAsociado.$setPristine();
      }
      $scope.estado = EstadosFormulario.LIMPIO;
    };

    var factoriaEdicionMultiple = function(modificador, mensajes) {
      var marcados = $scope.marcados.slice();
      if (marcados.length === 0) {
        $scope.modal.debeSeleccionarAsociado = true;
        return;
      }

      var timeoutId = _.delay(function(msg) {
        Notificaciones.progreso(msg);
      }, 250, mensajes.progreso);

      return {
        marcados: marcados,
        error: function() {
          window.clearTimeout(timeoutId);
          Notificaciones.errorServidor(mensajes.errorServidor);
        },
        completado: function() {
          for (var i = 0; i < $scope.asociados.length; i++) {
            var a = $scope.asociados[i];
            if (_.includes(marcados, a.id)) {
              modificador(a);
              a.marcado = false;
            }
          }
          window.clearTimeout(timeoutId);
          Notificaciones.completado(mensajes.completado);
          $scope.marcados = [];
        }
      };
    };
  }]);
