/// <reference path="../../../typings/tsd.d.ts"/>

module Cudu.Tecnicos.Asociados.Listado {
  class Asociado {
    grupoId: number;
    id: number;
    nombre: String;
    apellidos: String;
    tipo: String;
    rama: String;
    email: String;
    telefono: String;
    usuario: String;
    creado: String;
    actualizado: String;
    baja: String;
  }

  export interface AsociadosTecnicoScope extends ng.IScope {
    grupos: Array<String>;
    grupoPorDefecto: String;
    asociados: Array<Asociado>;
  }

  export class AsociadosTecnicoController {
    static $inject = ['$scope', 'AsociadoService'];
    constructor(private $scope: AsociadosTecnicoScope, private service: IAsociadoService) {
      $scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4'];
      $scope.grupoPorDefecto = $scope.grupos[0];
      service.listado().success(data => {  
        $scope.asociados = _.map(data.datos, (a: Array<any>) => { return this.bindAsociado(a, data.campos); });
      });
    }

    private bindAsociado(valores: Array<any>, indices: any): Asociado {
      return {
        grupoId: valores[indices.grupo_id],
        id: valores[indices.id],
        nombre: valores[indices.nombre],
        apellidos: valores[indices.apellidos],
        tipo: valores[indices.tipo],
        rama: valores[indices.rama],
        email: valores[indices.email],
        telefono: valores[indices.telefono],
        usuario: valores[indices.usuario_activo],
        creado: valores[indices.fecha_alta],
        actualizado: valores[indices.fecha_actualizacion],
        baja: valores[indices.fecha_baja],
      };
    }
  }

  export interface IAsociadoService {
    listado(): ng.IHttpPromise<any>;
  }

  export class AsociadoService implements IAsociadoService {
    constructor(private $http: ng.IHttpService) { }
    listado(): ng.IHttpPromise<any> {
      return this.$http.get('api/tecnico/asociado');
    }
  }
}

angular.module('cuduApp')
  .controller('AsociadosTecnicoController', Cudu.Tecnicos.Asociados.Listado.AsociadosTecnicoController)
  .service('AsociadoService', Cudu.Tecnicos.Asociados.Listado.AsociadoService)
