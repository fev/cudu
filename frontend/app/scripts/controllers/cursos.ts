/// <reference path="../../../typings/tsd.d.ts"/>
/// <reference path="../services.d.ts"/>

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

    operacionEnCurso?: boolean;
  }

  interface EstadoInscripcionEnCurso {
    cursoId: number;
    plazas: number;
    inscritos: number;
    disponibles: number;
    listaDeEspera: boolean;
  }

  export class CursoController {
    cursos: Curso[][];

    constructor(private $scope: ng.IScope, private service: CursoService) {
      service.listado().then(pagina => {
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

    inscribir(curso: Curso) {
      if (curso.operacionEnCurso || !this.elCursoEstaEnPlazo(curso)) {
        return;
      }
      curso.operacionEnCurso = true;
      this.service.inscribir(curso.id).success((e: EstadoInscripcionEnCurso) => {
        this.actualizarEstadoCurso(e, true);
      }).error(e => {
        /* TODO Toast! */ 
      }).finally(() => {
        curso.operacionEnCurso = false;
      });
    }

    desinscribir(curso: Curso) {
      if (curso.operacionEnCurso || !this.elCursoEstaEnPlazo(curso)) {
        return;
      }
      curso.operacionEnCurso = true;
      this.service.desinscribir(curso.id).success((e: EstadoInscripcionEnCurso) => {
        this.actualizarEstadoCurso(e, false);
      }).error(e => {
        /* TODO Toast! */
      }).finally(() => {
        curso.operacionEnCurso = false;
      });
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

    private actualizarEstadoCurso(e: EstadoInscripcionEnCurso, inscrito: boolean) {
      var actual = _.find(_.flatten(this.cursos), c => c.id == e.cursoId);
      actual.disponibles = e.disponibles;
      e.inscritos = e.inscritos;
      actual.usuarioInscrito = inscrito;
      actual.usuarioListaEspera = e.listaDeEspera;
    }

    private elCursoEstaEnPlazo(curso: Curso): boolean {
      var ahora = moment();
      var fechaInicio = moment(curso.fechaInicioInscripcion);
      var fechaFin = moment(curso.fechaFinInscripcion);
      if (fechaInicio.isValid() && ahora.isAfter(fechaFin)) { return false; }
      if (fechaFin.isValid() && ahora.isBefore(fechaInicio)) { return false; }
      return true;
    }
  }

  interface CursoService {
    listado(): ng.IPromise<Page<Curso>>;
    inscribir(id: number): ng.IHttpPromise<EstadoInscripcionEnCurso>;
    desinscribir(id: number): ng.IHttpPromise<EstadoInscripcionEnCurso>;
  }

  class CursoServiceImpl implements CursoService {
    private usuarioId: number;

    constructor(private http: ng.IHttpService, private usuarioService: UsuarioService) { }

    listado(): ng.IPromise<Page<Curso>> {
      return this.usuarioService.obtenerActual().then(u => {
        this.usuarioId = u.id;
        return this.http.get<Page<Curso>>("/api/lluerna/curso?sort=id&size=100")
      }).then(d => { return d.data; });
    }

    inscribir(id: number): ng.IHttpPromise<EstadoInscripcionEnCurso> {
      return this.http.post('/api/lluerna/curso/' + id + '/participantes', this.usuarioId);
    }

    desinscribir(id: number): ng.IHttpPromise<EstadoInscripcionEnCurso> {
      return this.http.delete('/api/lluerna/curso/' + id + '/participantes/' + this.usuarioId, { });
    }
  }

  export function CursoServiceFactory($http: ng.IHttpService, usuarioService: UsuarioService): CursoService {
    return new CursoServiceImpl($http, usuarioService);
  }
}

angular.module('cuduApp')
  .controller('CursoController', ['$scope', 'CursoService', Cudu.Cursos.CursoController])
  .factory('CursoService', ['$http', 'Usuario', Cudu.Cursos.CursoServiceFactory]);
