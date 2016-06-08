var Cudu;
(function (Cudu) {
    var Liquidaciones;
    (function (Liquidaciones) {
        var LiquidacionesGruposController = (function () {
            function LiquidacionesGruposController($scope, $location, service) {
                this.$scope = $scope;
                this.$location = $location;
                this.service = service;
                service.resumenPorGrupos().then(function (g) { $scope.grupos = g; });
            }
            LiquidacionesGruposController.prototype.verBalance = function (grupoId) {
                this.$location.path('/liquidaciones/balance/' + grupoId);
            };
            return LiquidacionesGruposController;
        }());
        Liquidaciones.LiquidacionesGruposController = LiquidacionesGruposController;
        var LiquidacionesBalanceController = (function () {
            function LiquidacionesBalanceController($scope, $location, $routeParams, service) {
                this.$scope = $scope;
                this.$location = $location;
                this.$routeParams = $routeParams;
                this.service = service;
                service.balanceGrupo($routeParams.grupoId, 2015).then(function (l) { return $scope.liquidaciones = l; });
            }
            LiquidacionesBalanceController.prototype.verDesglose = function (liquidacionId) {
                this.$location.path('/liquidaciones/desglose/' + liquidacionId);
            };
            return LiquidacionesBalanceController;
        }());
        Liquidaciones.LiquidacionesBalanceController = LiquidacionesBalanceController;
        var LiquidacionesDesgloseController = (function () {
            function LiquidacionesDesgloseController($scope, $routeParams, service) {
                this.$scope = $scope;
                this.$routeParams = $routeParams;
                this.service = service;
            }
            return LiquidacionesDesgloseController;
        }());
        Liquidaciones.LiquidacionesDesgloseController = LiquidacionesDesgloseController;
        var LiquidacionesServiceImpl = (function () {
            function LiquidacionesServiceImpl(http) {
                this.http = http;
            }
            LiquidacionesServiceImpl.prototype.resumenPorGrupos = function () {
                return this.http.get("/api/liquidaciones/grupos").then(function (g) { return g.data; });
            };
            LiquidacionesServiceImpl.prototype.balanceGrupo = function (grupoId, rondaId) {
                return this.http.get("/api/liquidaciones/balance/" + grupoId + '/' + rondaId).then(function (g) { return g.data; });
            };
            return LiquidacionesServiceImpl;
        }());
        function LiquidacionesServiceFactory($http) {
            return new LiquidacionesServiceImpl($http);
        }
        Liquidaciones.LiquidacionesServiceFactory = LiquidacionesServiceFactory;
    })(Liquidaciones = Cudu.Liquidaciones || (Cudu.Liquidaciones = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .factory('LiquidacionesService', ['$http', Cudu.Liquidaciones.LiquidacionesServiceFactory])
    .controller('LiquidacionesGruposController', ['$scope', '$location', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesGruposController])
    .controller('LiquidacionesBalanceController', ['$scope', '$location', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesBalanceController])
    .controller('LiquidacionesDesgloseController', ['$scope', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesDesgloseController]);
