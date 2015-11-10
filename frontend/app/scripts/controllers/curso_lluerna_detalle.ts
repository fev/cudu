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
      cursoService: CursosService;

      static $inject = ['$scope', '$routeParams', 'CursosService', 'Typeahead'];
      constructor(private $scope: CursoLluernaScope, $routeParams: CursoLluernaParams, cursoService: CursosService, TypeAhead: ITypeAhead) {
        $scope.typeaheadFormadorOpt = $scope.typeaheadParticipanteOpt = { highlight: true, editable: false };
        $scope.typeaheadFormadorDts = TypeAhead.formador();
        $scope.typeaheadParticipanteDts = TypeAhead.participante(+$routeParams.id);
        $scope.miembroPorIncluir = $scope.participantePorIncluir = null;
        this.cursoService = cursoService;
        this.scope = $scope;

        $scope.$on('typeahead:selected', (e, asociado) => this.añadirAsociado(e, asociado));

        cursoService.getCurso(+$routeParams.id).success(c => {
          var formadores = _.map(c.formadores, (f: any) => {
            f.nuevo = false;
            return f;
          });
          c.formadores = formadores;

          var participates = _.map(c.formadores, (f: any) => {
            f.nuevo = false;
            return f;
          });
          c.participates = participates;
          $scope.curso = c;
        });
      }

      añadirAsociado(e: ng.IAngularEvent, asociado: any) {
        var fn: any;
        var lista: any;
        var nuevoAsociado: any;
        if(!_.isUndefined(asociado.nombreCompleto)) {
           nuevoAsociado = { "id": asociado.id, "nombreCompleto": asociado.nombreCompleto };
          fn = (cursoId: number, asociadoId: number) => this.cursoService.añadirFormador(cursoId, asociadoId);
          lista = this.scope.curso.formadores;
        }
        else {
          nuevoAsociado = { "id": asociado.id, "nombreCompleto": asociado.nombre + " " + asociado.apellidos };
          fn = (cursoId: number, asociadoId: number) => this.cursoService.añadirParticipante(cursoId, asociadoId);
          lista = this.scope.curso.participantes;
        }

        if(!_.isUndefined(_.findWhere(lista, { 'id': asociado.id }))) {
          return;
        }
        fn(this.$scope.curso.id, asociado.id).success(() => {
          nuevoAsociado.nuevo = true;
          lista.unshift(nuevoAsociado);
        }).error((e) => { alert(e) });
      }

      eliminarFormador(formadorId: number) {
        this.cursoService.eliminarFormador(this.$scope.curso.id, formadorId).success(() => {
          _.remove(this.$scope.curso.formadores, (f) => f.id == formadorId);
        }).error((e) => { alert(e) });
      }

      eliminarParticipante(participanteId: number) {
        this.cursoService.eliminarParticipante(this.$scope.curso.id, participanteId).success(() => {
          _.remove(this.$scope.curso.participantes, (p) => p.id == participanteId);
        }).error((e) => { alert(e) });
      }
    }
}

angular.module('cuduApp')
.controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
