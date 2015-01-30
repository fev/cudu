'use strict';

angular.module('cuduApp')
  .controller('GrupoCtrl', ['$scope', 'Grupo', 'Usuario', 'Graficas', function ($scope, Grupo, Usuario, Graficas) {
    var grupoId = Usuario.usuario.grupo.id;
    Grupo.get({id: grupoId}, function(grupo) {
      $scope.grupo = grupo;
    });
    $scope.tabActivo = 0;

    $scope.coloresRama = Graficas.coloresRama;
    $scope.coloresTipo = Graficas.coloresTipo;
    $scope.etiquetas = {
      'rama': ['colonia', 'manada', 'exploradores', 'expedición', 'ruta'],
      'tipo': ['joven', 'kraal', 'comite']
    };

    Graficas.grupo(grupoId).success(function(data) {
      $scope.graficas = data;
    });
  }]);
