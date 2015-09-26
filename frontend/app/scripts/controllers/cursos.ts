/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Cursos {
  
  enum Estado { Normal, Inscrito, ListaEspera }
  
  // TODO mover a Cudu.Page<T> para poder compartirlo
  export interface Page<T> {
    content: T[];
    first: boolean;
    last: boolean;
    number: number;
    numberOfElements: number;
    size: number;
    sort: string;
    totalElements: number;
    totalPages: number;
  }
  
  export interface Curso { 
    id: number;
    titulo: string;
    estado: Estado;
    // fechaInicioInscripcion: Date;
    // fechaFinInscripcion: Date;
    // fechaNacimientoMinima: Date;
    plazas: number;
    inscritos: number;
    disponibles: number;
    descripcionFechas: string;
    descripcionLugar: string;
    usuarioInscrito: boolean;
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
    cursos: Curso[][];
    
    constructor($scope: ng.IScope, private service: CursoService) {
      service.listado().success(pagina => {
        // Divide el listado de cursos en tres columnas, repartidas
        // por igual de izquierda a derecha (similar a _.chunk)
        var chunked: Curso[][] = [[], [], []];
        var lista: Curso[] = pagina.content;
        for (let i = 0, j = 0; i < lista.length; i+=3, j++) {
            // El bucle interno está desenrollado
            if (typeof(lista[i]) === 'undefined') { break; }
            chunked[0][j] = lista[i];
            if (typeof(lista[i+1]) === 'undefined') { break; }
            chunked[1][j] = lista[i+1];
            if (typeof(lista[i+2]) === 'undefined') { break; }
            chunked[2][j] = lista[i+2];
        }        
        this.cursos = chunked;
      });
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
    
    listado(): ng.IHttpPromise<Page<Curso>> {
      return this.http.get<Page<Curso>>("/api/lluerna/curso?sort=id&size=100");
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
  // .directive('panelCurso', Cudu.Cursos.PanelCurso)
  .factory('CursoService', ['$http', 'Usuario', Cudu.Cursos.CursoServiceFactory]);
