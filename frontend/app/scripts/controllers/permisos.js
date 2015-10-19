var Cudu;
(function (Cudu) {
    var Permisos;
    (function (Permisos) {
        var PermisosController = (function () {
            function PermisosController($scope, service, traducciones, notificaciones) {
                this.$scope = $scope;
                this.service = service;
                this.traducciones = traducciones;
                this.notificaciones = notificaciones;
                service.listado().then(function (u) { $scope.usuarios = u; });
            }
            PermisosController.prototype.obtenerPermisosGrupo = function (u) {
                if (u.ambitoEdicion == "P") {
                    return this.traducciones.texto('permisos.sinAcceso');
                }
                if (u.restricciones.noPuedeEditarDatosDelGrupo) {
                    return this.traducciones.texto('permisos.soloLecturaGrupo');
                }
                return this.traducciones.texto('permisos.edicion');
            };
            PermisosController.prototype.establecerPermisosGrupo = function (u, noPuedeEditarDatosDelGrupo, soloLectura) {
                u.restricciones.noPuedeEditarDatosDelGrupo = noPuedeEditarDatosDelGrupo;
                u.restricciones.soloLectura = soloLectura;
                u.ambitoEdicion = noPuedeEditarDatosDelGrupo && soloLectura ? 'P' : 'G';
                var command = {
                    usuarioId: u.id,
                    ambitoPersonal: u.ambitoEdicion == 'P',
                    noPuedeEditarDatosDelGrupo: u.restricciones.noPuedeEditarDatosDelGrupo,
                    noPuedeEditarOtrasRamas: u.restricciones.noPuedeEditarOtrasRamas,
                    soloLectura: u.restricciones.soloLectura
                };
                this.editarPermisosUsuario(command);
            };
            PermisosController.prototype.obtenerPermisosAsociados = function (u) {
                if (u.ambitoEdicion == "P") {
                    return this.traducciones.texto('permisos.soloSusDatos');
                }
                if (u.restricciones.noPuedeEditarOtrasRamas && u.restricciones.soloLectura) {
                    return this.traducciones.texto('permisos.soloRamaSoloLectura');
                }
                if (u.restricciones.noPuedeEditarOtrasRamas) {
                    return this.traducciones.texto('permisos.soloRama');
                }
                if (u.restricciones.soloLectura) {
                    return this.traducciones.texto('permisos.soloLectura');
                }
                return this.traducciones.texto('permisos.todos');
            };
            PermisosController.prototype.estalecerPermisosAsociado = function (u, ambitoPersonal, noPuedeEditarOtrasRamas, soloLectura) {
                u.restricciones.noPuedeEditarOtrasRamas = noPuedeEditarOtrasRamas;
                u.restricciones.soloLectura = soloLectura;
                u.ambitoEdicion = ambitoPersonal ? 'P' : 'G';
                var command = {
                    usuarioId: u.id,
                    ambitoPersonal: ambitoPersonal,
                    noPuedeEditarDatosDelGrupo: u.restricciones.noPuedeEditarDatosDelGrupo,
                    noPuedeEditarOtrasRamas: u.restricciones.noPuedeEditarOtrasRamas,
                    soloLectura: u.restricciones.soloLectura
                };
                this.editarPermisosUsuario(command);
            };
            PermisosController.prototype.editarPermisosUsuario = function (command) {
                var _this = this;
                var progresoActivo = false;
                var timeoutId = _.delay(function (e) {
                    e.marcarProgresoActivo();
                    e.notificaciones.progreso(e.mensaje);
                }, 250, {
                    notificaciones: this.notificaciones,
                    mensaje: this.traducciones.texto('permisos.progreso'),
                    marcarProgresoActivo: function () { progresoActivo = true; }
                });
                this.service.editarPermisosUsuario(command).then(function () {
                    window.clearTimeout(timeoutId);
                    if (progresoActivo) {
                        _this.notificaciones.completado(_this.traducciones.texto('permisos.completado'));
                    }
                }).catch(function () {
                    window.clearTimeout(timeoutId);
                    _this.notificaciones.errorServidor(_this.traducciones.texto('permisos.error'));
                });
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
            PermisosServiceImpl.prototype.editarPermisosUsuario = function (command) {
                return this.http.put("/api/usuario/" + command.usuarioId + '/permisos', command);
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
    .controller('PermisosController', ['$scope', 'PermisosService', 'Traducciones', 'Notificaciones', Cudu.Permisos.PermisosController])
    .factory('PermisosService', ['$http', 'Usuario', Cudu.Permisos.PermisosServiceFactory]);
