'use strict';

angular.module('cuduApp')
  .controller('ActividadesListadoCtrl', ['$scope', '$location', 'Actividad', function($scope, $location, Actividad) {
    $scope.actividades = [];
    Actividad.queryAll(function(actividades) {
      $scope.actividades = actividades;
    });

    $scope.detalle = function(id) {
      $location.path('/actividades/' + id);
    };
  }])
  .controller('ActividadesDetalleCtrl', ['$scope', '$routeParams', 'Actividad', 'EstadosFormulario', 'Usuario', function($scope, $routeParams, Actividad, EstadosFormulario, Usuario) {
    $scope.estado = EstadosFormulario.LIMPIO;

    $scope.resumen = {
      asistentes: 0,
      pagados: 0,
      recaudacion: 0,
      esperado: 0
    };

    var normalizarPrecio = function(precio) {
      if (!precio) {
        return 0.0;
      }
      precio = precio + '';
      precio = precio.replace('€', '').replace(',', '.');
      var resultado = parseFloat(precio);
      if (isNaN(resultado)) {
        return 0.0;
      }
      return resultado;
    };

    var resumir = function(listado, precio) {
      if (typeof listado === "undefined") {
        return { asistentes: 0, pagados: 0, recaudacion: 0, esperado: 0 };
      }
      var precio = normalizarPrecio(precio);
      var van = 0;
      var pagados = 0;
      for (var i = 0; i < listado.length; i++) {
        var asistente = listado[i];
        if (asistente.estadoAsistente === 'S') { van = van + 1; }
        if (asistente.estadoAsistente === 'P' || asistente.estadoAsistente === 'B') { pagados = pagados + 1; }
      }
      return {
        asistentes: pagados + van,
        pagados: pagados,
        recaudacion: ((1.0 * pagados) * precio).toFixed(2),
        esperado: ((listado.length || 0) * precio).toFixed(2)
      };
    };

    var id = $routeParams.id;
    if (id === 'nueva') {
      var fechaInicio = null;
      var hoy = moment().day(); // 0 domingo, 6 sábado
      var usarSemanaSiguiente = (hoy == 0 || hoy >= 4); // jueves a domingo
      if (usarSemanaSiguiente) {
        fechaInicio = moment().endOf('isoWeek').add(1, 'weeks').subtract(1, 'days');
      } else {
        fechaInicio = moment().endOf('isoWeek').subtract(1, 'days');
      }

      $scope.actividad = {
        'grupoId': Usuario.usuario.grupo.id,
        'ramaCualquiera': true,
        'fechaInicio': [fechaInicio.year(), fechaInicio.month() + 1, fechaInicio.date()],
        'creadaPor': 'Z'
      };
    } else {
      Actividad.get({ 'id': id }, function(actividad) {
        $scope.actividad = actividad;
        $scope.actividad.creadaPor = 'Z';
        $scope.resumen = resumir(actividad.detalle, actividad.precio);
      });
    }

    $scope.guardar = function(id) {
      $scope.estado = EstadosFormulario.GUARDANDO;

      var guardar = Actividad.actualizar;
      if (!$scope.actividad.id) {
        guardar = Actividad.crear;
      }

      guardar({ id: id }, $scope.actividad).$promise.then(function(asociadoGuardado) {
        $scope.estado = EstadosFormulario.OK;
        $scope.formActividad.$setPristine();
      }, function(respuesta) {
        if (respuesta.status == 400) {
          $scope.estado = EstadosFormulario.VALIDACION;
          $scope.erroresValidacion = respuesta.data || [];
        } else {
          $scope.estado = EstadosFormulario.ERROR;
        }
      });
    };

    $scope.establecerRama = function(rama) {
      var actual = $scope.actividad.rama[rama];
      if (actual) {
        $scope.actividad.rama[rama] = false;
      } else {
        $scope.actividad.rama[rama] = true;
      }
      if (rama != 'alguna') {
        $scope.actividad.rama.alguna = true;
      } else {
        $scope.actividad.rama.colonia = false;
        $scope.actividad.rama.manada = false;
        $scope.actividad.rama.exploradores = false;
        $scope.actividad.rama.expedicion = false;
        $scope.actividad.rama.ruta = false;
      }
      $scope.formActividad.$setDirty();
    };

    $scope.establecerAsistencia = function(asistente, estado) {
      Actividad.canviarEstat({ 'id': asistente.actividadId, 'asociadoId': asistente.asociadoId }, estado, function() {
        asistente.estadoAsistente = estado;
        $scope.resumen = resumir($scope.actividad.detalle, $scope.actividad.precio);
      });
    };

    $scope.recalcularPrecio = function() {
      $scope.resumen = resumir($scope.actividad.detalle, $scope.actividad.precio);
    };
  }])
