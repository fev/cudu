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
  .controller('ActividadesDetalleCtrl', ['$scope', '$routeParams', 'Actividad', 'EstadosFormulario', function($scope, $routeParams, Actividad, EstadosFormulario) {
    $scope.estado = EstadosFormulario.LIMPIO;

    var id = $routeParams.id;
    if (id === 'nueva') {
      $scope.actividad = {
        'ramaCualquiera': true,
        'creadaPor': 'Z'
      };
    } else {
      Actividad.get({ 'id': id }, function(actividad) {
        $scope.actividad = actividad;
        $scope.actividad.creadaPor = 'Z';
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

    $scope.establecerRama = function(rama) {
      var actual = $scope.actividad[rama];
      if (actual) {
        $scope.actividad[rama] = false;
      } else {
        $scope.actividad[rama] = true;
      }
      if (rama != 'ramaCualquiera') {
        $scope.actividad.ramaCualquiera = false;
      } else {
        $scope.actividad.ramaColonia = false;
        $scope.actividad.ramaManada = false;
        $scope.actividad.ramaExploradores = false;
        $scope.actividad.ramaExpedicion = false;
        $scope.actividad.ramaRuta = false;
      }
      $scope.formAsociado.$setDirty();
    };
  }])
