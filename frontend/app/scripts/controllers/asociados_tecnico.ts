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

  class AsociadoFiltro {
    asociacion: string;
    tipo: string;
    grupoId: string;
    ramas: string;
    asociadoActivo;
  }

  export interface AsociadosTecnicoScope extends ng.IScope {
    grupos: Array<String>;
    grupoPorDefecto: String;
    asociados: Array<Asociado>;
    jovenActivo: boolean;
    kraalActivo: boolean;
    comiteActivo: boolean;
    filtro: AsociadoFiltro;
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

    public activar(tipo: string) {
      switch(tipo) {
        case 'Joven':
          this.$scope.jovenActivo = true;
          this.$scope.kraalActivo = this.$scope.comiteActivo = false;
          break;
        case 'Kraal':
          this.$scope.kraalActivo = true;
          this.$scope.jovenActivo = this.$scope.comiteActivo = false;
          break;
        case 'Comite':
          this.$scope.comiteActivo = true;
          this.$scope.kraalActivo = this.$scope.jovenActivo = false;
          break;
      }

      this.$scope.filtro = new AsociadoFiltro();
      this.$scope.filtro.tipo = tipo;
      this.service.filtrado(this.$scope.filtro).success(data => {
        this.$scope.asociados = _.map(data.datos, (a: Array<any>) => { return this.bindAsociado(a, data.campos); });
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
    filtrado(filtro: AsociadoFiltro): ng.IHttpPromise<any>;
  }

  export class AsociadoService implements IAsociadoService {
    constructor(private $http: ng.IHttpService) { }
    listado(): ng.IHttpPromise<any> {
      return this.$http.get('api/tecnico/asociado');
    }
    filtrado(filtro: AsociadoFiltro): ng.IHttpPromise<any> {
      return this.$http.get('api/tecnico/asociado', { params: filtro });
    }
  }
}

angular.module('cuduApp')
  .controller('AsociadosTecnicoController', Cudu.Tecnicos.Asociados.Listado.AsociadosTecnicoController)
  .service('AsociadoService', Cudu.Tecnicos.Asociados.Listado.AsociadoService)
