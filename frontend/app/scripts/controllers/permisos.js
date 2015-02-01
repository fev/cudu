'use strict';

angular.module('cuduApp')
  .controller('PermisosCtrl', ['$scope', 'Usuario', function($scope, Usuario) {
    var u = Usuario.usuario;
    var ultima = moment(u.fechaUsuarioVisto);
    if (ultima.isValid()) {
      u.fechaUsuarioVistoTxt = ultima.fromNow();
    }
    $scope.actual = u;
  }]);