/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Lluerna.Cursos {

  export interface Curso {
    id: number;
    titulo: string;
    fechaInicioInscripcion: string[];
    fechaFinInscripcion: string[];
    plazas: number;
    fechaNacimientoMinima: string[];
    formadores: any[];
    participantes: any[];
  }

  export interface CursosControllerScope extends ng.IScope {
    cursos: Curso[];
  }

  export class CursosService {
    http: ng.IHttpService;

    static $inject = ['$http'];
    constructor($http: ng.IHttpService) {
      this.http = $http;
    }

    listarCursos(): ng.IHttpPromise<any> {
      var result: ng.IHttpPromise<any> = this.http.get("/api/lluerna/curso/completo");
      return result;
    }

    getCurso(id: number) : ng.IHttpPromise<any> {
      var result: ng.IHttpPromise<any> = this.http.get("/api/lluerna/curso/" + id);
      return result;
    }

    añadirFormador(cursoId: number, formadorId: number): ng.IHttpPromise<any> {
      return this.http.post('/api/lluerna/curso/' + cursoId + '/formadores', formadorId);
    }

    eliminarFormador(cursoId: number, formadorId: number): ng.IHttpPromise<any> {
      return this.http.delete('/api/lluerna/curso/' + cursoId + '/formadores/' + formadorId);
    }

    añadirParticipante(cursoId: number, participanteId: number): ng.IHttpPromise<any> {
      return this.http.post('/api/lluerna/curso/' + cursoId + '/participantes', participanteId);
    }

    eliminarParticipante(cursoId: number, participanteId: number): ng.IHttpPromise<any> {
      return this.http.delete('/api/lluerna/curso/' + cursoId + '/participantes/' + participanteId);
    }
  }

  export class CursosController {
    cursos: Curso[];
    location: ng.ILocationService;

    static $inject = ['$scope', '$location', 'CursosService'];
    constructor($scope: CursosControllerScope, $location: ng.ILocationService, private service: CursosService) {
      this.cursos = [];
      this.location = $location;
      service.listarCursos().success((r: any) => this.cursos = r.content);
      $scope.cursos = this.cursos;
    }

    detalle(id: number) {
      this.location.path('/lluerna/curso/' + id);
    }
  }
}

angular.module('cuduApp')
  .controller('CursosController', Cudu.Lluerna.Cursos.CursosController)
  .service('CursosService', Cudu.Lluerna.Cursos.CursosService);
