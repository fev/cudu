'use strict';

angular.module('cuduApp')
  .controller('GrupoCtrl', ['$scope', 'Grupo', 'Usuario', 'Graficas', 'EstadosFormulario', function ($scope, Grupo, Usuario, Graficas, EstadosFormulario) {
    $scope.estado = EstadosFormulario.LIMPIO;
    var grupo = Usuario.usuario.grupo || { id: -1 };
    Grupo.get({id: grupo.id}, function(grupo) {
      $scope.grupo = grupo;
    });
    $scope.tabActivo = 0;

    $scope.coloresRama = Graficas.coloresRama;
    $scope.coloresTipo = Graficas.coloresTipo;
    $scope.etiquetas = {
      'rama': ['colonia', 'manada', 'exploradores', 'expedición', 'ruta'],
      'tipo': ['joven', 'kraal', 'comite']
    };

    Graficas.grupo(grupo.id).success(function(data) {
      $scope.graficas = data;
    });

    $scope.guardar = function() {
      
    };
  }]);
