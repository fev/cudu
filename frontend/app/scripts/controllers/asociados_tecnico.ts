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

class Scroll {
    isBusy: boolean;
    isEnabled: boolean;
    pagina: number;
    limite: number;
    constructor() {
        const me = this;
        me.isBusy = false;
        me.isEnabled = true;
        me.pagina = 0;
        me.limite = 0;
    }
    
    disable() {
        const me = this;
        me.isEnabled = false;
    } 
}

export interface AsociadosTecnicoScope extends ng.IScope {
    grupos: Array<String>;
    grupoPorDefecto: String;
    asociados: Array<Asociado>;
    filtro: AsociadoFiltro;
    // filtroAsociadoTipo: FiltroAsociadoTipo;
}

export class AsociadosTecnicoController {
    static $inject = ['$scope', 'AsociadoService'];
    private scroll: Scroll;
    constructor(private $scope: AsociadosTecnicoScope, private service: IAsociadoService) {
        const me = this;
        me.$scope = $scope;
        me.$scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4']; // TODO
        me.$scope.grupoPorDefecto = $scope.grupos[0]; // TODO
        // me.$scope.filtroAsociadoTipo = new FiltroAsociadoTipo();
        me.$scope.filtro = new AsociadoFiltro();
        me.$scope.filtro.ramasSeparadasPorComas = "";
        me.scroll = new Scroll();
    }
    
    public obternerAsociados() {
        const me = this;
        if (me.scroll.isBusy || !me.scroll.isEnabled) {
            return;
        }
        
        me.scroll.isBusy = true;
        me.service.listado(me.scroll.pagina).success(data => {
            let asociados = _.map(data.datos, (a: Array<any>) => { return this.bindAsociado(a, data.campos); });
            if(me.scroll.pagina === 0) {
                me.scroll.limite = data.total;
            }
            if(_.isUndefined(me.$scope.asociados)) {
                me.$scope.asociados = asociados;
            } else {
                me.$scope.asociados.push.apply(me.$scope.asociados, asociados);   
            }
            
            if(me.$scope.asociados.length >= me.scroll.limite) {
                me.scroll.disable();
            }
            
            me.scroll.isBusy = false;
            me.scroll.pagina = me.scroll.pagina + 1;
        });
    }

    public filtraRama(rama: string) {
      let lista = _.words(this.$scope.filtro.ramasSeparadasPorComas);
      if (this.esRama(rama)) {
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

    // public activar(tipo: string) {
    //   if (this.$scope.filtroAsociadoTipo.isActivo(tipo)) {
    //     this.$scope.filtroAsociadoTipo.desactivar(tipo);
    //   }
    //   else {
    //     this.$scope.filtroAsociadoTipo.activar(tipo);
    //     this.$scope.filtro.tipo = tipo;
    //   }
    //   this.filtraAsociados();
    // }

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
    listado(pagina: number): ng.IHttpPromise<any>;
    filtrado(filtro: AsociadoFiltro): ng.IHttpPromise<any>;
}

export class AsociadoService implements IAsociadoService {
    constructor(private $http: ng.IHttpService) { }
    listado(pagina: number): ng.IHttpPromise<any> {
      return this.$http.get('api/tecnico/asociado/?page=' + pagina);
    }
    filtrado(filtro: AsociadoFiltro): ng.IHttpPromise<any> {
      return this.$http.get('api/tecnico/asociado', { params: filtro });
    }
  }
}

angular.module('cuduApp')
  .controller('AsociadosTecnicoController', Cudu.Tecnicos.Asociados.Listado.AsociadosTecnicoController)
  .service('AsociadoService', Cudu.Tecnicos.Asociados.Listado.AsociadoService)
