/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Cursos {
  
  export interface Curso { 
    id: number;
    titulo: string;
    fechaInicioInscripcion: Date;
    fechaFinInscripcion: Date;
    fechaNacimientoMinima: Date;
    plazas: number;
    inscritos: number;
    disponibles: number;
    descripcionFechas: string;
    descripcionLugar: string;
    usuarioInscrito: boolean;
    usuarioListaEspera: boolean;
    
    plazoCerrado: boolean;
    plazoRestante: string;
  }
  
  interface EstadoInscripcionEnCurso {
    cursoId: number;
    plazas: number;
    inscritos: number;
    disponibles: number;
    listaDeEspera: boolean;
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
    
    constructor(private $scope: ng.IScope, private service: CursoService) {
      service.listado().success(pagina => {
        // Divide el listado de cursos en tres columnas, repartidas
        // por igual de izquierda a derecha (similar a _.chunk)
        var chunked: Curso[][] = [[], [], []];
        var lista: Curso[] = pagina.content;
        for (let i = 0, j = 0; i < lista.length; i+=3, j++) {
            // El bucle interno está desenrollado
            if (typeof(lista[i]) === 'undefined') { break; }
            chunked[0][j] = this.establecerPlazos(lista[i]);
            if (typeof(lista[i+1]) === 'undefined') { break; }
            chunked[1][j] = this.establecerPlazos(lista[i+1]);
            if (typeof(lista[i+2]) === 'undefined') { break; }
            chunked[2][j] = this.establecerPlazos(lista[i+2]);
        }        
        this.cursos = chunked;
      });
    }
    
    inscribir(id: number) {
      this.service.inscribir(id).success((e: EstadoInscripcionEnCurso) => {
        this.actualizarEstadoCurso(e, true);
      }).error(e => { 
        /* TODO Toast! */ 
        console.log(e);
      });
    }
    
    desinscribir(id: number) {
      this.service.desinscribir(id).success((e: EstadoInscripcionEnCurso) => {
        this.actualizarEstadoCurso(e, false);
      }).error(e => { 
        /* TODO Toast! */
        console.log(e);
      });
    }
    
    private actualizarEstadoCurso(e: EstadoInscripcionEnCurso, inscrito: boolean) {
      var actual = _.find(_.flatten(this.cursos), c => c.id == e.cursoId);
      actual.disponibles = e.disponibles;
      e.inscritos = e.inscritos;
      actual.usuarioInscrito = inscrito;
      actual.usuarioListaEspera = e.listaDeEspera;
    }
    
    establecerPlazos(curso: Curso) {
      var m = moment(curso.fechaFinInscripcion);
      if (m.isValid) {
          curso.plazoCerrado = m.isAfter();
          if (m.isAfter()) {            
            curso.plazoRestante = "El plazo inscripción cierra en " + m.fromNow();
          } else {
            curso.plazoRestante = "Plazo de inscripición cerrado";
          }
      }
      return curso;
    }
  }
  
  class CursoService {
    constructor(private http: ng.IHttpService, private usuarioId: number) { }
    
    listado(): ng.IHttpPromise<Page<Curso>> {
      return this.http.get<Page<Curso>>("/api/lluerna/curso?sort=id&size=100");
    }
    
    inscribir(id: number): ng.IHttpPromise<EstadoInscripcionEnCurso> {      
      return this.http.post('/api/lluerna/curso/' + id + '/participantes', this.usuarioId);
    }
    
    desinscribir(id: number): ng.IHttpPromise<EstadoInscripcionEnCurso> {
      return this.http.delete('/api/lluerna/curso/' + id + '/participantes/' + this.usuarioId, { });
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
