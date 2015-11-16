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
      id: any;
      curso: Curso;
      typeaheadFormadorOpt: TypeAheadOptions;
      typeaheadParticipanteOpt: TypeAheadOptions;
      miembroPorIncluir: Miembro;
      participantePorIncluir: Participante;
      typeaheadFormadorDts: any[];
      typeaheadParticipanteDts: any[];
      estado: number;
      erroresValidacion: Array<string>;
      customError: string;
    }

    export class CursoLluernaController {
      static $inject = ['$scope', 'Traducciones', '$routeParams', 'CursosService', 'Typeahead', 'EstadosFormulario'];
      constructor(private $scope: CursoLluernaScope, private traducciones: TraduccionesService,
        private $routeParams: CursoLluernaParams, private cursoService: CursosService,
        private typeAhead: ITypeAhead, private estados: IEstadosFormulario) {
        $scope.estado = estados.LIMPIO;
        $scope.erroresValidacion = [];
        $scope.typeaheadFormadorOpt = $scope.typeaheadParticipanteOpt = { highlight: true, editable: false };
        $scope.miembroPorIncluir = $scope.participantePorIncluir = null;
        $scope.$on('typeahead:selected', (e, asociado) => this.añadirAsociado(e, asociado));

        var id = $routeParams.id;
        if(id == 'nuevo') {
          $scope.typeaheadParticipanteDts = $scope.typeaheadFormadorDts = [];
          $scope.curso = new Curso();
          $scope.curso.descripcionLugar = "";
          $scope.curso.descripcionFechas = "";
          return;
        }

        $scope.typeaheadFormadorDts = typeAhead.formador();
        $scope.typeaheadParticipanteDts = typeAhead.participante(+$routeParams.id);

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
          lista = this.$scope.curso.formadores;
        }
        else {
          nuevoAsociado = { "id": asociado.id, "nombreCompleto": asociado.nombre + " " + asociado.apellidos };
          fn = (cursoId: number, asociadoId: number) => this.cursoService.añadirParticipante(cursoId, asociadoId);
          lista = this.$scope.curso.participantes;
          if(lista.length == this.$scope.curso.plazas) {
            this.$scope.estado = this.estados.CUSTOM;
            this.$scope.customError = this.traducciones.texto('cursos.maxPlazas');
            this.$scope.$apply();
            return;
          }
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

      guardar() {
        if(_.isUndefined(this.$scope.curso.id)) {
          this.cursoService.crearCurso(this.$scope.curso)
          .success((data) => {
            this.$scope.curso = data;
            this.$scope.estado = this.estados.OK
            this.$scope.typeaheadFormadorDts = this.typeAhead.formador();
            this.$scope.typeaheadParticipanteDts = this.typeAhead.participante(data.id);
          })
          .error((data, e) => {
            if (e == 400) {
              this.$scope.estado = this.estados.VALIDACION;
              this.$scope.erroresValidacion = data || [];
            } else {
              this.$scope.estado = this.estados.ERROR;
            }
          });
          return;
        }

        this.cursoService.guardarCurso(this.$scope.curso)
        .success(() => this.$scope.estado = this.estados.OK)
        .error((data, e) => {
          if (e == 400) {
            this.$scope.estado = this.estados.VALIDACION;
            this.$scope.erroresValidacion = data || [];
          } else {
            this.$scope.estado = this.estados.ERROR;
          }
        });
      }
    }
}

angular.module('cuduApp')
.controller('CursoLluernaController', Cudu.Lluerna.Cursos.Detalle.CursoLluernaController);
