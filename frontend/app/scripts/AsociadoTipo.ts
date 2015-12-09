/// <reference path="../../typings/tsd.d.ts"/>

module Cudu.Tecnicos.Asociados {
  export class AsociadoTipo {
    private tipo: string;
    private activo: boolean;
    constructor(tipo: string, activo: boolean) {
      this.tipo = tipo;
      this.activo = activo;
    }

    getTipo(): string { return this.tipo; }
    activar() { this.activo = true; }
    desactivar() { this.activo = false; }
    isActivo(): boolean { return this.activo; }
  }

  export class FiltroAsociadoTipo {
    private tipoAsociados: Array<AsociadoTipo>;
    constructor() {
      this.tipoAsociados = [];
      this.tipoAsociados.push(new AsociadoTipo("Kraal", false));
      this.tipoAsociados.push(new AsociadoTipo("Joven", false));
      this.tipoAsociados.push(new AsociadoTipo("Comite", false));
    }

    getAsociadoTipo(tipo: string): AsociadoTipo {
      return _.find(this.tipoAsociados, (a) => { return tipo === a.getTipo() });
    }

    activar(tipo: string) {
      var asociadoTipo: AsociadoTipo = this.getAsociadoTipo(tipo);
      asociadoTipo.activar();
      var restoTipos = _.without(this.tipoAsociados, asociadoTipo);
      _.forEach(restoTipos, (t) => t.desactivar());
    }

    desactivar(tipo: string) {
      this.getAsociadoTipo(tipo).desactivar();
    }

    isActivo(tipo: string) {
      return this.getAsociadoTipo(tipo).isActivo();
    }
  }
}
