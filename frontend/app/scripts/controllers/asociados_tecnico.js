var Cudu;
(function (Cudu) {
    var Tecnicos;
    (function (Tecnicos) {
        var Asociados;
        (function (Asociados) {
            var Listado;
            (function (Listado) {
                var Asociado = (function () {
                    function Asociado() {
                    }
                    return Asociado;
                })();
                var AsociadoFiltro = (function () {
                    function AsociadoFiltro() {
                    }
                    return AsociadoFiltro;
                })();
                var AsociadosTecnicoController = (function () {
                    function AsociadosTecnicoController($scope, service) {
                        var _this = this;
                        this.$scope = $scope;
                        this.service = service;
                        $scope.grupos = ['Grupo 1', 'Grupo 2', 'Grupo 3', 'Grupo 4'];
                        $scope.grupoPorDefecto = $scope.grupos[0];
                        $scope.filtroAsociadoTipo = new Asociados.FiltroAsociadoTipo();
                        $scope.filtro = new AsociadoFiltro();
                        service.listado().success(function (data) {
                            $scope.asociados = _.map(data.datos, function (a) { return _this.bindAsociado(a, data.campos); });
                        });
                    }
                    AsociadosTecnicoController.prototype.activar = function (tipo) {
                        var _this = this;
                        if (this.$scope.filtroAsociadoTipo.isActivo(tipo)) {
                            this.$scope.filtroAsociadoTipo.desactivar(tipo);
                        }
                        else {
                            this.$scope.filtroAsociadoTipo.activar(tipo);
                            this.$scope.filtro.tipo = tipo;
                        }
                        this.service.filtrado(this.$scope.filtro).success(function (data) {
                            _this.$scope.asociados = _.map(data.datos, function (a) { return _this.bindAsociado(a, data.campos); });
                        });
                    };
                    AsociadosTecnicoController.prototype.filtraPorSexo = function (sexo) {
                        var _this = this;
                        if (this.$scope.filtro.sexo === sexo) {
                            this.desactivarSexo();
                        }
                        else {
                            this.$scope.filtro.sexo = sexo;
                        }
                        this.service.filtrado(this.$scope.filtro).success(function (data) {
                            _this.$scope.asociados = _.map(data.datos, function (a) { return _this.bindAsociado(a, data.campos); });
                        });
                    };
                    AsociadosTecnicoController.prototype.desactivarSexo = function () {
                        this.$scope.filtro.sexo = null;
                    };
                    AsociadosTecnicoController.prototype.bindAsociado = function (valores, indices) {
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
                    };
                    AsociadosTecnicoController.$inject = ['$scope', 'AsociadoService'];
                    return AsociadosTecnicoController;
                })();
                Listado.AsociadosTecnicoController = AsociadosTecnicoController;
                var AsociadoService = (function () {
                    function AsociadoService($http) {
                        this.$http = $http;
                    }
                    AsociadoService.prototype.listado = function () {
                        return this.$http.get('api/tecnico/asociado');
                    };
                    AsociadoService.prototype.filtrado = function (filtro) {
                        return this.$http.get('api/tecnico/asociado', { params: filtro });
                    };
                    return AsociadoService;
                })();
                Listado.AsociadoService = AsociadoService;
            })(Listado = Asociados.Listado || (Asociados.Listado = {}));
        })(Asociados = Tecnicos.Asociados || (Tecnicos.Asociados = {}));
    })(Tecnicos = Cudu.Tecnicos || (Cudu.Tecnicos = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('AsociadosTecnicoController', Cudu.Tecnicos.Asociados.Listado.AsociadosTecnicoController)
    .service('AsociadoService', Cudu.Tecnicos.Asociados.Listado.AsociadoService);
