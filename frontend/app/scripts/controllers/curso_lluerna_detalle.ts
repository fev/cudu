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
      constructor($scope: CursoLluernaScope, $routeParams: CursoLluernaParams, cursoService: CursosService, TypeAhead: ITypeAhead) {
        $scope.typeaheadFormadorOpt = { highlight: true, editable: false };
        $scope.typeaheadFormadorDts = TypeAhead.formador();
        $scope.miembroPorIncluir = null;

        $scope.$on('typeahead:selected', function(e, miembro) {
          if(!_.isUndefined(_.findWhere($scope.curso.formadores, { 'id': miembro.id }))) {
            return;
          }
          $scope.curso.formadores.unshift( { "id": miembro.id, "nombreCompleto": miembro.nombreCompleto, "nuevo": true })
          $scope.$apply(); //TODO: remove
        });

        cursoService.getCurso(+$routeParams.id).success(c => {
          var formadores = _.map(c.formadores, (f: any) => {
            f.nuevo = false;
            return f;
          });
          c.formadores = formadores;
          $scope.curso = c;
        });
      }
    }
}

angular.module('cuduApp')
.controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
