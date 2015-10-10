/// <reference path="../../../typings/tsd.d.ts"/>
/// <reference path="../services.d.ts"/>
var Cudu;
(function (Cudu) {
    var Permisos;
    (function (Permisos) {
        var PermisosController = (function () {
            function PermisosController($scope, service) {
                this.$scope = $scope;
                this.service = service;
                service.listado().then(function (u) {
                    $scope.usuarios = u;
                });
            }
            PermisosController.prototype.permisosGrupo = function (u) {
                if (u.ambitoEdicion == "P") {
                    return "Sin acceso";
                }
                if (u.restricciones.noPuedeEditarDatosDelGrupo) {
                    return "Solo lectura";
                }
            };
            PermisosController.prototype.permisosAsociados = function (u) {
                if (u.ambitoEdicion == "P") {
                    return "Sólo sus datos";
                }
                if (u.restricciones.noPuedeEditarOtrasRamas && u.restricciones.soloLectura) {
                    return "Sólo a su rama, sólo lectura";
                }
                if (u.restricciones.noPuedeEditarOtrasRamas) {
                    return "Sólo a su rama";
                }
                if (u.restricciones.soloLectura) {
                    return "Todos, sólo lectura";
                }
                return "Todos";
            };
            return PermisosController;
        })();
        Permisos.PermisosController = PermisosController;
        var PermisosServiceImpl = (function () {
            function PermisosServiceImpl(http, usuarioService) {
                this.http = http;
                this.usuarioService = usuarioService;
            }
            PermisosServiceImpl.prototype.listado = function () {
                var _this = this;
                return this.usuarioService.obtenerActual().then(function (u) {
                    _this.grupoIdActual = u.grupo.id;
                    return _this.http.get("/api/usuario/grupo/" + _this.grupoIdActual);
                }).then(function (d) { return d.data; });
            };
            return PermisosServiceImpl;
        })();
        function PermisosServiceFactory($http, usuarioService) {
            return new PermisosServiceImpl($http, usuarioService);
        }
        Permisos.PermisosServiceFactory = PermisosServiceFactory;
    })(Permisos = Cudu.Permisos || (Cudu.Permisos = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('PermisosController', ['$scope', 'PermisosService', Cudu.Permisos.PermisosController])
    .factory('PermisosService', ['$http', 'Usuario', Cudu.Permisos.PermisosServiceFactory]);
