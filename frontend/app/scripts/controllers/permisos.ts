/// <reference path="../../../typings/tsd.d.ts"/>
/// <reference path="../services.d.ts"/>

module Cudu.Permisos {

    interface Usuario {
      id: number;
      nombreCompleto: string;
      email: string;
      calidadPassword: number;
      ambitoEdicion: string;
      restricciones: Restricciones;
    }

    interface Restricciones {
      noPuedeEditarDatosDelGrupo: boolean;
      noPuedeEditarOtrasRamas: boolean;
      restriccionAsociacion?: string;
      soloLectura: boolean;
    }

    interface PermisosScope extends ng.IScope {
      usuarios: Usuario[];
    }

    export class PermisosController {
      constructor(private $scope: PermisosScope, private service: PermisosService, private traducciones: TraduccionesService) {
        service.listado().then(u => { $scope.usuarios = u; });
        // TODO catch
      }

      obtenerPermisosGrupo(u: Usuario) {
        if (u.ambitoEdicion == "P") {
          return this.traducciones.texto('permisos.sinAcceso');
        }
        if (u.restricciones.noPuedeEditarDatosDelGrupo) {
          return this.traducciones.texto('permisos.soloLectura');
        }
        return this.traducciones.texto('permisos.edicion');
      }

      obtenerPermisosAsociados(u: Usuario) {
        if (u.ambitoEdicion == "P") {
          return this.traducciones.texto('permisos.soloSusDatos');
        }
        if (u.restricciones.noPuedeEditarOtrasRamas && u.restricciones.soloLectura) {
          return this.traducciones.texto('permisos.soloRamaSoloLectura');
        }
        if (u.restricciones.noPuedeEditarOtrasRamas) {
          return this.traducciones.texto('permisos.soloRama');
        }
        if (u.restricciones.soloLectura) {
          return this.traducciones.texto('permisos.soloLectura');
        }
        return this.traducciones.texto('permisos.todos');
      }
    }

    interface PermisosService {
      listado(): ng.IPromise<Usuario[]>;
    }

    class PermisosServiceImpl implements PermisosService {
      private grupoIdActual: string;

      constructor(private http: ng.IHttpService, private usuarioService: UsuarioService) { }

      listado(): ng.IPromise<Usuario[]> {
        return this.usuarioService.obtenerActual().then(u => {
          this.grupoIdActual = u.grupo.id;
          return this.http.get<Usuario[]>("/api/usuario/grupo/" + this.grupoIdActual);
        }).then(d => d.data);
      }
    }

    export function PermisosServiceFactory($http: ng.IHttpService, usuarioService: UsuarioService): PermisosService {
      return new PermisosServiceImpl($http, usuarioService);
    }
}

angular.module('cuduApp')
  .controller('PermisosController', ['$scope', 'PermisosService', 'Traducciones', Cudu.Permisos.PermisosController])
  .factory('PermisosService', ['$http', 'Usuario', Cudu.Permisos.PermisosServiceFactory]);
