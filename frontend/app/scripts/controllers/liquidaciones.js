var Cudu;
(function (Cudu) {
    var Liquidaciones;
    (function (Liquidaciones) {
        var LiquidacionesGruposController = (function () {
            function LiquidacionesGruposController($scope, $location, service) {
                this.$scope = $scope;
                this.$location = $location;
                this.service = service;
                this.$scope.rondaActual = service.rondaActual();
                this.cargarDatosRonda(this.$scope.rondaActual);
            }
            LiquidacionesGruposController.prototype.verBalance = function (grupoId, rondaId) {
                this.$location.path('/liquidaciones/balance/' + grupoId + "/" + rondaId);
            };
            LiquidacionesGruposController.prototype.cargarDatosRonda = function (rondaId) {
                var _this = this;
                this.service.resumenPorGrupos(rondaId).then(function (g) {
                    _this.$scope.grupos = g;
                    _this.$scope.rondaId = rondaId;
                });
            };
            return LiquidacionesGruposController;
        }());
        Liquidaciones.LiquidacionesGruposController = LiquidacionesGruposController;
        var LiquidacionesBalanceController = (function () {
            function LiquidacionesBalanceController($scope, $location, $routeParams, modalEditarLiquidacion, service) {
                var _this = this;
                this.$scope = $scope;
                this.$location = $location;
                this.$routeParams = $routeParams;
                this.modalEditarLiquidacion = modalEditarLiquidacion;
                this.service = service;
                this.$scope.grupoId = $routeParams.grupoId;
                this.$scope.rondaId = $routeParams.rondaId || service.rondaActual();
                this.$scope.rondaEtiqueta = this.$scope.rondaId + '-' + (1 + parseInt(this.$scope.rondaId));
                this.cargarBalanceGrupo($routeParams.grupoId, this.$scope.rondaId);
                this.modalEditarLiquidacion.subscribe(Cudu.Ux.ModalEvent.BeforeHide, function () { return _this.despuesCerrarModalLiquidacion(); });
                $scope.$on('$destroy', function () { _this.modalEditarLiquidacion.unsubscribe(); });
            }
            LiquidacionesBalanceController.prototype.cargarBalanceGrupoActual = function (rondaId) {
                this.cargarBalanceGrupo(this.$routeParams.grupoId, rondaId);
            };
            LiquidacionesBalanceController.prototype.verDesglose = function (liquidacionId) {
                this.$location.path('/liquidaciones/desglose/' + liquidacionId);
            };
            LiquidacionesBalanceController.prototype.nuevaLiquidacion = function () {
                var _this = this;
                this.service.crearLiquidacion(this.$scope.grupoId, this.$scope.rondaId).then(function (resumen) {
                    _this.$scope.seleccionada = _.last(resumen.balance);
                    _this.modalEditarLiquidacion.show();
                });
            };
            LiquidacionesBalanceController.prototype.editarLiquidacion = function (l) {
                this.$scope.seleccionada = l;
                this.modalEditarLiquidacion.show();
            };
            LiquidacionesBalanceController.prototype.eliminarLiquidacion = function (l) {
                var _this = this;
                this.$scope.seleccionada = null;
                this.service.eliminarLiquidacion(l.grupoId, l.rondaId, l.liquidacionId).then(function () {
                    _this.modalEditarLiquidacion.hide();
                });
            };
            LiquidacionesBalanceController.prototype.guardarLiquidacion = function (l) {
                var _this = this;
                var pagado = _.trim(l.pagado);
                var ajusteManual = _.trim(l.ajusteManual);
                pagado = pagado === "" ? "0" : pagado;
                ajusteManual = ajusteManual === "" ? null : ajusteManual;
                this.service.guardarLiquidacion(l.grupoId, l.rondaId, l.liquidacionId, ajusteManual, pagado, l.borrador).then(function (resumen) {
                    _this.modalEditarLiquidacion.hide();
                });
            };
            LiquidacionesBalanceController.prototype.crearReferencia = function (liquidacion) {
                if (!liquidacion) {
                    return "";
                }
                return liquidacion.grupoId + "-" + liquidacion.rondaId + "-" + liquidacion.liquidacionId;
            };
            LiquidacionesBalanceController.prototype.despuesCerrarModalLiquidacion = function () {
                this.cargarBalanceGrupo(this.$scope.grupoId, this.$scope.rondaId);
            };
            LiquidacionesBalanceController.prototype.cargarBalanceGrupo = function (grupoId, rondaId) {
                var _this = this;
                this.service.balanceGrupo(grupoId, rondaId).then(function (resumen) {
                    _this.procesarResumen(resumen, rondaId);
                });
            };
            LiquidacionesBalanceController.prototype.procesarResumen = function (resumen, rondaId) {
                this.$scope.resumen = resumen;
                this.$scope.nombreGrupo = resumen.nombreGrupo;
                this.$scope.totalAjustado = this.limitarTotal(resumen.total);
                this.$scope.totalAjustadoAbs = Math.abs(this.$scope.totalAjustado);
                this.$scope.balancePositivo = resumen.total >= 0;
                this.$scope.existenAltasCompensadas = resumen.total > 0;
                this.$scope.rondaId = rondaId;
                this.$scope.informacionPago = resumen.informacionPago;
                var ultimaSinPagar = _.findLast(resumen.balance, function (b) { return b.pagado == 0; });
                if (ultimaSinPagar) {
                    this.$scope.informacionPago.concepto = this.crearReferencia(ultimaSinPagar);
                }
                else {
                    this.$scope.informacionPago.concepto = "—";
                }
            };
            LiquidacionesBalanceController.prototype.limitarTotal = function (total) {
                var minimo = Math.min(0, total);
                if (isNaN(minimo)) {
                    return 0;
                }
                return minimo;
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
            LiquidacionesServiceImpl.prototype.rondaActual = function () {
                var m = moment();
                if (m.month() >= 8) {
                    return m.year();
                }
                return m.year() - 1;
            };
            LiquidacionesServiceImpl.prototype.resumenPorGrupos = function (rondaId) {
                return this.http.get("/api/liquidaciones/grupos/" + rondaId).then(function (g) { return g.data; });
            };
            LiquidacionesServiceImpl.prototype.balanceGrupo = function (grupoId, rondaId) {
                return this.http.get("/api/liquidaciones/balance/" + grupoId + '/' + rondaId).then(function (g) { return g.data; });
            };
            LiquidacionesServiceImpl.prototype.crearLiquidacion = function (grupoId, rondaId) {
                return this.http.post("/api/liquidaciones/balance/" + grupoId + '/' + rondaId, {}).then(function (f) { return f.data; });
            };
            LiquidacionesServiceImpl.prototype.eliminarLiquidacion = function (grupoId, rondaId, liquidacionId) {
                return this.http.delete("/api/liquidaciones/balance/" + grupoId + '/' + rondaId + "/" + liquidacionId);
            };
            LiquidacionesServiceImpl.prototype.guardarLiquidacion = function (grupoId, rondaId, liquidacionId, ajusteManual, pagado, borrador) {
                var payload = { id: liquidacionId, ajusteManual: ajusteManual, pagado: pagado, borrador: borrador };
                return this.http.put("/api/liquidaciones/balance/" + grupoId + '/' + rondaId + "/" + liquidacionId, payload).then(function (f) { return f.data; });
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
    .factory('ModalEditarLiquidacion', Cudu.Ux.ModalFactory("#dlgEditarLiquidacion"))
    .controller('LiquidacionesGruposController', ['$scope', '$location', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesGruposController])
    .controller('LiquidacionesBalanceController', ['$scope', '$location', '$routeParams', 'ModalEditarLiquidacion', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesBalanceController])
    .controller('LiquidacionesDesgloseController', ['$scope', '$routeParams', 'LiquidacionesService', Cudu.Liquidaciones.LiquidacionesDesgloseController]);
