/// <reference path="../../../typing.tsd.d.ts"/>

module Cudu.Cursos {
  
  enum Estado { Normal, Inscrito, ListaEspera }
  
  export interface Curso { 
    id: number;
    titulo: string;
    estado: Estado;
    // fechaInicioInscripcion: Date;
    // fechaFinInscripcion: Date;
    // fechaNacimientoMinima: Date;
    plazas: number;
    inscritos: number;
    descripcionFechas: string;
    descripcionLugar: string;
    //constructor(public id: number) { }
  }
  
  // Mover a .tds?
  export interface Usuario {
    id: number;
  }  
  export interface UsuarioService {
    usuario: Usuario;
  }
  // end mover
  
  export class CursoController {
    cursos: Curso[];
    
    constructor($scope: ng.IScope, private service: CursoService) {
      // service.listado().success(lista => {
      //   this.cursos = lista;
      // });      
      this.cursos = service.listado_mock();
    }
    
    inscribir(id: number) {
      this.service.inscribir(id).success((c: Curso) => {
        // TODO Actualizar elemento de la lista, plazas e inscritos
      }).error(e => { /* TODO Toast! */ });
    }
    
    desinscribir(id: number) {
      this.service.desinscribir(id).success((c: Curso) => {
        // TODO Actualizar elemento de la lista, plazas e inscritos
      }).error(e => { /* TODO Toast! */ });
    }
  }
  
  class CursoService {
    constructor(private http: ng.IHttpService, private usuarioId: number) { }
    
    listado(): ng.IHttpPromise<Curso[]> {
      return this.http.get<Curso[]>("/api/lluerna/cursos");
    }
    
    listado_mock(): Curso[] {      
      return [{
          id: 1, titulo: 'Contenidos Básicos',
          estado: Estado.Normal,
          plazas: 21, inscritos: 8,
          descripcionFechas: 'Puente de octubre',
          descripcionLugar: 'Moraira'
      }];
    }
    
    inscribir(id: number): ng.IHttpPromise<Curso> {
      return this.http.post('/api/lluerna/curso/' + id + '/inscribir/' + this.usuarioId, { });
    }
    
    desinscribir(id: number): ng.IHttpPromise<Curso> {
      return this.http.post('/api/lluerna/curso/' + id + '/inscribir/' + this.usuarioId, { });
    }
  }
  
  export function CursoServiceFactory($http: ng.IHttpService, usuario: UsuarioService): CursoService {
    return new CursoService($http, usuario.usuario.id);
  }
}

angular.module('cuduApp')
  .controller('CursoController', ['$scope', 'CursoService', Cudu.Cursos.CursoController])
  .factory('CursoService', ['$http', 'Usuario', Cudu.Cursos.CursoServiceFactory]);
