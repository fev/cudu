/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Lluerna.Cursos.Detalle {

    export interface TypeAheadOptions {
      highlight: boolean;
      editable: boolean;
    }

    export interface Miembro {

    }

    export interface CursoLluernaParams extends angular.route.IRouteParamsService {
        id: string;
    }

    export interface CursoLluernaScope extends ng.IScope {
      curso: Curso;
      typeaheadFormadorOpt: TypeAheadOptions;
      miembroPorIncluir: Miembro;
      typeaheadFormadorDts: any[];
    }

    export class CursoLluernaController {
      routeParams: angular.route.IRouteParamsService;
      scope: CursoLluernaScope;

      static $inject = ['$scope', '$routeParams', 'CursosService', 'Typeahead'];
      constructor($scope: CursoLluernaScope, $routeParams: CursoLluernaParams, cursoService: CursosService, TypeAhead: IS.ITypeAhead) {
        $scope.typeaheadFormadorOpt = { highlight: true, editable: false };
        $scope.typeaheadFormadorDts = TypeAhead.miembro();
        $scope.miembroPorIncluir = null;

        var id = +$routeParams.id;
        var curso = cursoService.getCurso(id).success(c => $scope.curso = c);
        // $scope.typeaheadAsociadoDts = Typeahead.miembro();
        // $scope.miembroPorIncluir = null;
      }
    }
}

angular.module('cuduApp')
.controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
