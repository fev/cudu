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
      var result: ng.IHttpPromise<any> = this.http.get("/api/lluerna/curso");
      return result;
    }
  }

  export class CursosController {
    cursos: Curso[];

    static $inject = ['$scope', 'CursosService'];
    constructor($scope: CursosControllerScope, private service: CursosService) {
      this.cursos = [];

      service.listarCursos().success((r: Curso[]) => this.cursos = r);

      for (let i = 0; i < 5; i++) {
          this.cursos.push({
            id: i, titulo: 'titulo' + i,
            fechaFinInscripcion: ["03", "05", "15"],
            fechaInicioInscripcion: ["03", "05", "15"],
            plazas: i * 5,
            fechaNacimientoMinima: ["03", "05", "15"]
      });
    }

      $scope.cursos = this.cursos;
    }
  }
}

angular.module('cuduApp')
  .controller('CursosController', Cudu.Lluerna.Cursos.CursosController)
  .service('CursosService', Cudu.Lluerna.Cursos.CursosService);
