/// <reference path="../../../typings/tsd.d.ts"/>
/// <reference path="../services.d.ts"/>
/// <reference path="../support"/>

module Cudu.Liquidaciones {

  interface LiquidacionGrupoDto { }

  interface LiquidacionesGruposScope extends ng.IScope {
    rondaId: number;
    rondaActual: number;
    grupos: LiquidacionGrupoDto[];
  }

  export class LiquidacionesGruposController {
    constructor(private $scope: LiquidacionesGruposScope,
        private $location: ng.ILocationService,
        private service: LiquidacionesService) {
      this.$scope.rondaActual = service.rondaActual();
      this.cargarDatosRonda(this.$scope.rondaActual);
    }

    verBalance(grupoId: string) {
      this.$location.path('/liquidaciones/balance/' + grupoId);
    }

    cargarDatosRonda(rondaId: number) {
      this.service.resumenPorGrupos(rondaId).then(g => {
        this.$scope.grupos = g;
        this.$scope.rondaId = rondaId;
      });
    }
  }

  interface LiquidacionBalanceDetalle {
    grupoId: string;
    rondaId: number;
    liquidacionId: number;
    balance: number;
  }

  interface LiquidacionBalanceDto {
    numeroActualAsociados: number;
    total: number;
    balance: LiquidacionBalanceDetalle[];
  }

  interface LiquidacionesBalanceScope extends ng.IScope {
    resumen: LiquidacionBalanceDto;
    totalAjustado: number;
    totalAjustadoAbs: number;
    balancePositivo: boolean;
  }

  interface LiquidacionesBalanceRouteParams extends angular.route.IRouteParamsService {
    grupoId: string;
  }

  export class LiquidacionesBalanceController {
    constructor(private $scope: LiquidacionesBalanceScope,
        private $location: ng.ILocationService,
        private $routeParams: LiquidacionesBalanceRouteParams,
        private service: LiquidacionesService) {
      service.balanceGrupo($routeParams.grupoId, 2015).then(l => {
        $scope.resumen = l;
        $scope.totalAjustado = this.limitarTotal(l.total);
        $scope.totalAjustadoAbs = Math.abs($scope.totalAjustado);
        $scope.balancePositivo = l.total > 0;
        // if (l && l.balance && l.balance.length > 0) {
        //   $scope.ultima = l[l.balance.length - 1] || <LiquidacionBalanceDto>{ };
        // }
      });
    }

    verDesglose(liquidacionId: string) {
      this.$location.path('/liquidaciones/desglose/' + liquidacionId);
    }

    crearReferencia(liquidacion: LiquidacionBalanceDetalle) {
      if (!liquidacion) {
        return "";
      }
      return liquidacion.grupoId + "-" + liquidacion.rondaId + "-" + liquidacion.liquidacionId;
    }

    limitarTotal(total: number): number {
      var minimo = Math.min(0, total);
      if (isNaN(minimo)) {
        return 0;
      }
      return minimo;
    }
  }

  interface LiquidacionesDesgloseScope extends ng.IScope {
    hello: string;
  }

  interface LiquidacionesDesgloseRouteParams extends angular.route.IRouteParamsService {
    liquidacionId: string;
  }

  export class LiquidacionesDesgloseController { 
    constructor(private $scope: LiquidacionesDesgloseScope,
        private $routeParams: LiquidacionesDesgloseRouteParams,
        private service: LiquidacionesService) {
    }
  }

  interface LiquidacionesService {
    rondaActual(): number;
    resumenPorGrupos(rondaId: number): ng.IPromise<LiquidacionGrupoDto[]>;
    balanceGrupo(grupoId: string, rondaId: number): ng.IPromise<LiquidacionBalanceDto>;
  }

  class LiquidacionesServiceImpl implements LiquidacionesService {
    constructor(private http: ng.IHttpService) { }

    rondaActual(): number {
      var m = moment();
      if (m.month() >= 8) {
        return m.year();
      }
      return m.year() - 1;
    }

    resumenPorGrupos(rondaId: number): ng.IPromise<LiquidacionGrupoDto[]> {
      return this.http.get<LiquidacionGrupoDto[]>("/api/liquidaciones/grupos/" + rondaId).then(g => g.data);
    }

    balanceGrupo(grupoId: string, rondaId: number): ng.IPromise<LiquidacionBalanceDto> {
      return this.http.get<LiquidacionBalanceDto>("/api/liquidaciones/balance/" + grupoId + '/' + rondaId).then(g => g.data);
    }
  }

  export function LiquidacionesServiceFactory($http: ng.IHttpService) : LiquidacionesService {
    return new LiquidacionesServiceImpl($http);
  }
}

angular.module('cuduApp')
  .factory('LiquidacionesService', ['$http', Cudu.Liquidaciones.LiquidacionesServiceFactory])
  .controller('LiquidacionesGruposController', ['$scope', '$location', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesGruposController])
  .controller('LiquidacionesBalanceController', ['$scope', '$location', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesBalanceController])
  .controller('LiquidacionesDesgloseController', ['$scope', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesDesgloseController]);
