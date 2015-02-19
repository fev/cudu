'use strict';

// TODO Mover a constantes de angular
var estados = {
  LIMPIO: 0,
  GUARDANDO: 1,
  OK: 2,
  ERROR: 3,
  VALIDACION: 4
};

angular.module('cuduApp')
  .controller('SeguridadCtrl', ['$scope', '$rootScope', 'Traducciones', 'Usuario', function($scope, $rootScope, Traducciones, Usuario) {
    $scope.mensajeError = null;
    $scope.activando = false;

    $rootScope.$on('asociado.editar', function() {
      $scope.mensajeError = null;
      $scope.activando = false;
    });

    $scope.activar = function(asociado) {
      var edad = Usuario.calcularEdad(asociado.fechaNacimiento);
      if (!asociado.email || edad < 18) {
        $scope.mensajeError = Traducciones.texto('activar.email18');
        return;
      }
      Usuario.activar(asociado.id, asociado.email)
        .success(function(data, status) {
          $scope.activando = true;
        })
        .error(function(error, status) {
          if (status === 400 && error.codigo === 'AsociadoInactivo') {
            $scope.mensajeError = Traducciones.texto('activar.asociadoInactivo');
          } else if (status === 409 && error.codigo === 'ActivacionDeUsuarioEnCurso') {
            $scope.mensajeError = Traducciones.texto('activar.activacionEnCurso');
          } else {
            $scope.mensajeError = Traducciones.texto('activar.errorServidor');
          }
        });
    };

    $scope.reenviarEmail = function() {
      $scope.mensajeError = null;
      $scope.activando = false;
    };

    $scope.desactivar = function(asociado) {
      if (asociado.id === Usuario.usuario.id) {
        $scope.mensajeError = Traducciones.texto('activar.deshabilitarUsuarioActual');
        return;
      }
      Usuario.desactivar(asociado.id)
        .success(function(data, status) {
          asociado.usuarioActivo = false;
          $scope.activando = false;
          $scope.mensajeError = null;
        })
        .error(function(error, status) {
          if (status === 400 && error.codigo === 'DeshabilitarUsuarioActual') {
            $scope.mensajeError = Traducciones.texto('activar.deshabilitarUsuarioActual');
          } else {
            $scope.mensajeError = Traducciones.texto('activar.errorServidor');
          }
        });
    };
  }])
  .controller('AsociadoCtrl', ['$scope', 'Asociado', 'Grupo', 'Usuario', 'EstadosFormulario', 'Traducciones',
      function ($scope, Asociado, Grupo, Usuario, EstadosFormulario, Traducciones) {
    $scope.grupo = Usuario.usuario.grupo;
    $scope.asociados = [];
    Asociado.query(function(asociados) {
      $scope.asociados = asociados.content;
    });

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
    $scope.filtro = { tipo: '', eliminados: false };
    $scope.columnas = { rama: true, direccion: false, contacto: false };
    $scope.orden = 'apellidos';

    $scope.modal = { eliminar: false };

    var emitirAsociadoEditandose = function(asociado) {
      $scope.$emit('asociado.editar', asociado);
    };

    $scope.seleccionarTab = function(tabId) {
      $scope.tabActivo = tabId;
    };

    $scope.limpiarFiltro = function() {
      $scope.busqueda = '';
      $scope.filtro = { tipo: '', eliminados: false };
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
              (f.eliminados || asociado.activo));
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
      $scope.asociado = generarAsociadoVacio();
    };

    $scope.editar = function(id) {
      marcarCambiosPendientes();

      var pos = _.findIndex($scope.asociados, function(a) { return a ? a.id === id : false; });
      var original = $scope.asociados[pos];
      if (original.cambiosPendientes)
      {
        // Si previamente no se han guardado los cambios, evitamos recargar desde
        // el servidor y dejamos el asociado original con cambios pendientes.
        $scope.asociado = original;
        emitirAsociadoEditandose(original);
      } else {
        Asociado.get({ 'id': id }, function(asociado) {
          asociado.marcado = original.marcado;
          asociado.guardado = original.guardado;
          asociado.puntosCovol = calcularPuntosCovol(asociado);
          $scope.asociado = asociado;
          $scope.asociados[pos] = asociado;
          emitirAsociadoEditandose(asociado);
        });
      }
    };

    $scope.guardar = function(id) {
      $scope.estado = EstadosFormulario.GUARDANDO;

      var asociadoNuevo = false;
      var guardar = Asociado.actualizar;
      if (!$scope.asociado.id) {
        guardar = Asociado.crear;
        asociadoNuevo = true;
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

    $scope.copiarDatosContacto = function(desde, hasta) {
      $scope.asociado['telefono' + desde] = $scope.asociado['telefono' + hasta];
      $scope.asociado['email' + desde] = $scope.asociado['email' + hasta];
      $scope.formAsociado.$setDirty();
    };

    $scope.obtenerNombreCompleto = function(asociado) {
      if (asociado && (asociado.nombre || asociado.apellidos)) {
        return [asociado.nombre, asociado.apellidos].join(' ');
      }
      return '(nuevo)';
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

    var calcularRamaRecomendada = function(fechaNacimiento) {
      var edad = Usuario.calcularEdad(fechaNacimiento);
      if (edad <= 7) { return 'Colonia'; }
      if (edad > 7 && edad <= 10) { return 'Manada'; }
      if (edad > 10 && edad <= 13) { return 'Exploradores'; }
      if (edad > 13 && edad <= 16) { return 'Expedicion'; }
      if (edad > 16) { return 'Ruta'; }
      return null;
    }

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
  }]);
