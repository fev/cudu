/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Lluerna.Cursos {

  export interface Curso {
    id: number;
    titulo: string;
    fechaInicioInscripcion: string[];
    fechaFinInscripcion: string[];
    plazas: number;
    fechaNacimientoMinima: string[];
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
  }

  export class CursosController {
    cursos: Curso[];

    static $inject = ['$scope', 'CursosService'];
    constructor($scope: CursosControllerScope, private service: CursosService) {
      this.cursos = [];
      service.listarCursos().success((r: any) => this.cursos = r.content);
      $scope.cursos = this.cursos;
    }
  }
}

angular.module('cuduApp')
  .controller('CursosController', Cudu.Lluerna.Cursos.CursosController)
  .service('CursosService', Cudu.Lluerna.Cursos.CursosService);
