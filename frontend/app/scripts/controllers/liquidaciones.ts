/// <reference path="../../../typings/tsd.d.ts"/>
/// <reference path="../services.d.ts"/>
/// <reference path="../support"/>

module Cudu.Liquidaciones {

  interface LiquidacionesGruposScope extends ng.IScope {
    hello: string;
  }

  export class LiquidacionesGruposController {
    constructor(private $scope: LiquidacionesGruposScope, private service: LiquidacionesService) {
      $scope.hello = "hello world!"
    }
  }

  interface LiquidacionesBalanceScope extends ng.IScope {
    hello: string;
  }

  interface LiquidacionesBalanceRouteParams extends angular.route.IRouteParamsService {
    grupoId: string;
  }

  export class LiquidacionesBalanceController {
    constructor(private $scope: LiquidacionesBalanceScope,
        private $routeParams: LiquidacionesBalanceRouteParams,
        private service: LiquidacionesService) {
      $scope.hello = "hello world! " + $routeParams.grupoId;
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
      $scope.hello = "hello world! " + $routeParams.liquidacionId;
    }
  }

  interface LiquidacionesService { }

  class LiquidacionesServiceImpl implements LiquidacionesService {
    constructor(private http: ng.IHttpService) { }
  }

  export function LiquidacionesServiceFactory($http: ng.IHttpService) : LiquidacionesService {
    return new LiquidacionesServiceImpl($http);
  }
}

angular.module('cuduApp')
  .factory('LiquidacionesService', ['$http', Cudu.Liquidaciones.LiquidacionesServiceFactory])
  .controller('LiquidacionesGruposController', ['$scope', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesGruposController])
  .controller('LiquidacionesBalanceController', ['$scope', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesBalanceController])
  .controller('LiquidacionesDesgloseController', ['$scope', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesDesgloseController]);
