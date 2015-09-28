'use strict';

angular.module('cuduApp')
  .controller('MiembrosCtrl', ['$scope', '$location', 'Typeahead', 'Asociado', 'Miembro', function($scope, $location, Typeahead, Asociado, Miembro) {

    var cargos = {
      FORMADOR: 34,
      COLABORADOR: 70,
      TUFO: 36
    };

    var getCargoId = function(cargo) {
      switch (cargo) {
        case 'F':
          return cargos.FORMADOR;
        case 'T':
          return cargos.TUFO;
        case 'C':
          return cargos.COLABORADOR;
      }
    };

    var getCargo = function(cargoId) {
      switch (cargoId) {
        case cargos.TUFO:
          return 'T';
        case cargos.COLABORADOR:
          return 'C';
        case cargos.FORMADOR:
          return 'F';
      }
    };

    $scope.typeaheadAsociadoOpt = { highlight: true, editable: false };
    $scope.typeaheadAsociadoDts = Typeahead.miembro();
    $scope.miembroPorIncluir = null;

    $scope.miembro = { };
	  $scope.miembros = [];
    Miembro.queryAll(function(data) {
      $scope.miembros = _.map(data, function(a) {
        a.tipoMiembro = getCargo(a.cargoId);
        a.nuevo = false; // evita resaltar la lista
        return a;
       });
    }, function() { });

    // Afegir asistent
    $scope.$on('typeahead:selected', function(e, miembro) {
      if(!_.isUndefined(_.findWhere($scope.miembros, { 'id': miembro.id }))) {
        return;
      }
      Miembro.añadir({ id: miembro.id }, { cargo: cargos.FORMADOR, mesa_pedagogica: false }, function(res) {
        $scope.añadirMiembro(miembro);});
    });

    $scope.añadirMiembro = function(miembro) {
      var nuevoMiembro = {
        nombreCompleto: miembro.nombre + ' ' + miembro.apellidos,
        id: miembro.id,
        tipoMiembro: 'F',
        mesaPedagogica: false,
        telefono: miembro.telefono,
        email: miembro.email,
        nuevo: true
      };

      $scope.miembros.unshift(nuevoMiembro);
    };

    $scope.quitarTipo = function(miembro) {
      Miembro.eliminar({ id: miembro.id}, {}, function() {
        _.remove($scope.miembros, function(m) { return m.id === miembro.id; });
      });
    };

    $scope.establecerTipo = function(miembro, tipo) {
      var cargoId = getCargoId(tipo);
      Miembro.eliminar({ id: miembro.id}, {}, function() {
        Miembro.añadir({ id: miembro.id}, { cargo: cargoId, mesaPedagogica: miembro.mesaPedagogica}, function() {
          miembro.tipoMiembro = getCargo(cargoId);
        });
      });
    };

    $scope.mesaPedagogica = function(miembro) {
      var cargoId = getCargoId(miembro.tipoMiembro);
      Miembro.eliminar({ id: miembro.id}, {}, function() {
        Miembro.añadir({ id: miembro.id}, { cargo: cargoId, mesaPedagogica: miembro.mesaPedagogica});
      });
    };

    $scope.traducciones = { F: 'formador', C: 'colaborador', T: 'tufo' };
    $scope.traduce = function(clave) { return _.result($scope.traducciones, clave); };

  }]);
