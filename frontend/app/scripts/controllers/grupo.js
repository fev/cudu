'use strict';

angular.module('cuduApp')
  .controller('GrupoCtrl', ['$scope', 'Grupo', 'Usuario', 'Graficas', 'EstadosFormulario', 'Traducciones', function ($scope, Grupo, Usuario, Graficas, EstadosFormulario, Traducciones) {
    $scope.estado = EstadosFormulario.LIMPIO;
    var grupo = Usuario.usuario.grupo || { id: -1 };
    Grupo.get({id: grupo.id}, function(grupo) {
      $scope.grupo = grupo;
    });
    $scope.tabActivo = 0;

    $scope.coloresRama = Graficas.coloresRama;
    $scope.coloresTipo = Graficas.coloresTipo;
    $scope.etiquetas = {
      'rama': [
        Traducciones.texto('rama.colonia'), 
        Traducciones.texto('rama.manada'), 
        Traducciones.texto('rama.exploradores'),
        Traducciones.texto('rama.expedicion'),
        Traducciones.texto('rama.ruta')
      ],
      'tipo': [
        Traducciones.texto('tipo.J'),
        Traducciones.texto('tipo.K'),
        Traducciones.texto('tipo.C')
      ]
    };

    Graficas.grupo(grupo.id).success(function(data) {
      $scope.graficas = data;
    });

    $scope.guardar = function() {
      $scope.estado = EstadosFormulario.GUARDANDO;
      Grupo.actualizar({id: $scope.grupo.id }, $scope.grupo).$promise.then(function(grupoGuardado) {
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
  }]);
