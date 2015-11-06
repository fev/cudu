/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Lluerna.Cursos.Detalle {

    export interface TypeAheadOptions {
      highlight: boolean;
      editable: boolean;
    }

    export interface Miembro { }
    export interface Participante { }

    export interface CursoLluernaParams extends angular.route.IRouteParamsService {
        id: string;
    }

    export interface CursoLluernaScope extends ng.IScope {
      curso: Curso;
      typeaheadFormadorOpt: TypeAheadOptions;
      typeaheadParticipanteOpt: TypeAheadOptions;
      miembroPorIncluir: Miembro;
      participantePorIncluir: Participante;
      typeaheadFormadorDts: any[];
      typeaheadParticipanteDts: any[];
    }

    export class CursoLluernaController {
      routeParams: angular.route.IRouteParamsService;
      scope: CursoLluernaScope;

      static $inject = ['$scope', '$routeParams', 'CursosService', 'Typeahead'];
      constructor(private $scope: CursoLluernaScope, $routeParams: CursoLluernaParams, private cursoService: CursosService, TypeAhead: ITypeAhead) {
        $scope.typeaheadFormadorOpt = $scope.typeaheadParticipanteOpt = { highlight: true, editable: false };
        $scope.typeaheadFormadorDts = TypeAhead.formador();
        $scope.typeaheadParticipanteDts = TypeAhead.participante(+$routeParams.id);
        $scope.miembroPorIncluir = $scope.participantePorIncluir = null;

        $scope.$on('typeahead:selected', function(e, miembro) {
          if(!_.isUndefined(_.findWhere($scope.curso.formadores, { 'id': miembro.id }))) {
            return;
          }
          cursoService.añadirFormador($scope.curso.id, miembro.id).success(() => {
            $scope.curso.formadores.unshift( { "id": miembro.id, "nombreCompleto": miembro.nombreCompleto, "nuevo": true })
          }).error((e) => { alert(e) });
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

      eliminarFormador(formadorId: number) {
        this.cursoService.eliminarFormador(this.$scope.curso.id, formadorId).success(() => {
          _.remove(this.$scope.curso.formadores, (f) => f.id == formadorId);
        }).error((e) => { alert(e) });
      }
    }
}

angular.module('cuduApp')
.controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
