'use strict';

angular.module('cuduApp')
  .controller('GrupoCtrl', ['$scope', 'Grupo', 'Usuario', function ($scope, Grupo, Usuario) {
    var idGrupo = Usuario.usuario.idGrupo;
    Grupo.get({id: idGrupo}, function(grupo) {
      $scope.grupo = grupo;
    });
    $scope.tabActivo = 0;
  }]);
