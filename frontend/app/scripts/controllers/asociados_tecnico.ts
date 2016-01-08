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
    sexo: String;
  }

  class AsociadoFiltro {
    asociacion: string;
    tipo: string;
    grupoId: string;
    sexo: string;
    asociadoActivo;
    ramasSeparadasPorComas: string;
  }

  export interface AsociadosTecnicoScope extends ng.IScope {
    grupos: Array<String>;
    grupoPorDefecto: String;
    asociados: Array<Asociado>;
    filtro: AsociadoFiltro;
    filtroAsociadoTipo: FiltroAsociadoTipo;
  }

  export class AsociadosTecnicoController {
    static $inject = ['$scope', 'AsociadoService'];
    constructor(private $scope: AsociadosTecnicoScope, private service: IAsociadoService) {
      $scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4']; // TODO
      $scope.grupoPorDefecto = $scope.grupos[0]; // TODO
      $scope.filtroAsociadoTipo = new FiltroAsociadoTipo();
      $scope.filtro = new AsociadoFiltro();
      $scope.filtro.ramasSeparadasPorComas = "";
      service.listado().success(data => {
        $scope.asociados = _.map(data.datos, (a: Array<any>) => { return this.bindAsociado(a, data.campos); });
      });
    }

    public filtraRama(rama: string) {
      var lista = _.words(this.$scope.filtro.ramasSeparadasPorComas);
      if(this.esRama(rama)) {
        _.remove(lista, r => r === rama);
      }
      else {
        lista.push(rama);
      }
      this.$scope.filtro.ramasSeparadasPorComas = lista.join();
      this.filtraAsociados();
    }

    public esRama(rama: string) : boolean {
      return this.$scope.filtro.ramasSeparadasPorComas.indexOf(rama) > -1;
    }

    public activar(tipo: string) {
      if(this.$scope.filtroAsociadoTipo.isActivo(tipo)) {
        this.$scope.filtroAsociadoTipo.desactivar(tipo);
      }
      else {
        this.$scope.filtroAsociadoTipo.activar(tipo);
        this.$scope.filtro.tipo = tipo;
      }
      this.filtraAsociados();
    }

    public filtraPorSexo(sexo: string) {
      if(this.$scope.filtro.sexo === sexo) {
        this.desactivarSexo();
      }
      else {
        this.$scope.filtro.sexo = sexo;
      }
      this.filtraAsociados();
    }

    private desactivarSexo() {
      this.$scope.filtro.sexo = null;
    }

    private filtraAsociados() {

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
        sexo: valores[indices.sexo]
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
