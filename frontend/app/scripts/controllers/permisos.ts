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
      constructor(private $scope: PermisosScope, private service: PermisosService) {
        service.listado().then(u => {
          $scope.usuarios = u;
        });
      }
      
      permisosGrupo(u: Usuario) {
        if (u.ambitoEdicion == "P") {
          return "Sin acceso";
        }
        if (u.restricciones.noPuedeEditarDatosDelGrupo) {
          return "Solo lectura";
        }
      }
      
      permisosAsociados(u: Usuario) {
        if (u.ambitoEdicion == "P") {
          return "Sólo sus datos";
        }        
        if (u.restricciones.noPuedeEditarOtrasRamas && u.restricciones.soloLectura) {
          return "Sólo a su rama, sólo lectura";
        }
        if (u.restricciones.noPuedeEditarOtrasRamas) {
          return "Sólo a su rama";
        }
        if (u.restricciones.soloLectura) {
          return "Todos, sólo lectura";
        }
        return "Todos";
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
  .controller('PermisosController', ['$scope', 'PermisosService', Cudu.Permisos.PermisosController])
  .factory('PermisosService', ['$http', 'Usuario', Cudu.Permisos.PermisosServiceFactory]);