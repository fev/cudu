var Cudu;
(function (Cudu) {
    var Permisos;
    (function (Permisos) {
        var PermisosController = (function () {
            function PermisosController($scope, service, traducciones, notificaciones, modalCambioDni, modalEliminar, modalRecuperarPassword, modalCrearUsuario, typeahead) {
                this.$scope = $scope;
                this.service = service;
                this.traducciones = traducciones;
                this.notificaciones = notificaciones;
                this.modalCambioDni = modalCambioDni;
                this.modalEliminar = modalEliminar;
                this.modalRecuperarPassword = modalRecuperarPassword;
                this.modalCrearUsuario = modalCrearUsuario;
                this.typeahead = typeahead;
                service.listado().then(function (u) { $scope.usuarios = u; });
                typeahead.attach($scope);
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
            PermisosController.prototype.mostrarDialogoCambioEmail = function (u) {
                u.nuevoEmail = u.email;
                this.$scope.usuarioActual = u;
                this.$scope.cambiandoEmail = false;
                this.$scope.errorCambioEmail = null;
                this.modalCambioDni.show();
            };
            PermisosController.prototype.cambiarEmail = function () {
                var _this = this;
                this.$scope.cambiandoEmail = true;
                this.service.cambiarEmail(this.$scope.usuarioActual.id, this.$scope.usuarioActual.nuevoEmail).then(function () {
                    _this.modalCambioDni.hide();
                    _this.$scope.usuarioActual.email = _this.$scope.usuarioActual.nuevoEmail;
                    _this.$scope.errorCambioEmail = null;
                }).catch(function (e) {
                    if (e.status == 400) {
                        _this.$scope.errorCambioEmail = _this.traducciones.texto("permisos.error.email");
                    }
                    else if (e.status == 409) {
                        _this.$scope.errorCambioEmail = _this.traducciones.texto("permisos.error.emailDuplicado");
                    }
                    else {
                        _this.$scope.errorCambioEmail = _this.traducciones.texto("permisos.error.servidor");
                    }
                }).finally(function () { _this.$scope.cambiandoEmail = false; });
            };
            PermisosController.prototype.mostrarDialogoEliminar = function (u) {
                this.$scope.usuarioActual = u;
                this.$scope.eliminandoUsuario = false;
                this.$scope.eliminarTambienDatos = false;
                this.$scope.errorEliminar = null;
                this.modalEliminar.show();
            };
            PermisosController.prototype.eliminar = function () {
                var _this = this;
                this.$scope.eliminandoUsuario = true;
                this.service.desactivar(this.$scope.usuarioActual.id, this.$scope.eliminarTambienDatos).then(function () {
                    _this.modalEliminar.hide();
                    _this.$scope.errorEliminar = null;
                }).catch(function (e) {
                    _this.$scope.errorCambioEmail = _this.traducciones.texto("permisos.error.servidor");
                }).finally(function () {
                    _this.$scope.eliminandoUsuario = false;
                    _this.$scope.eliminarTambienDatos = false;
                });
            };
            PermisosController.prototype.mostarDialogoRecuperarPassword = function (u) {
                this.$scope.usuarioActual = u;
                this.$scope.recuperandoPassword = false;
                this.$scope.errorRecuperarPassword = null;
                this.modalRecuperarPassword.show();
            };
            PermisosController.prototype.recuperarPassword = function () {
                var _this = this;
                this.$scope.recuperandoPassword = true;
                var u = this.$scope.usuarioActual;
                this.service.recuperarPassword(u.id, u.email).then(function () {
                    _this.modalRecuperarPassword.hide();
                    _this.$scope.errorRecuperarPassword = null;
                }).catch(function (e) {
                    if (e.status == 409) {
                        _this.$scope.errorRecuperarPassword = _this.traducciones.texto("activar.activacionEnCurso");
                    }
                    else {
                        _this.$scope.errorRecuperarPassword = _this.traducciones.texto("permisos.error.servidor");
                    }
                }).finally(function () {
                    _this.$scope.recuperandoPassword = false;
                });
            };
            PermisosController.prototype.mostrarDialogoCrearUsuario = function () {
                this.$scope.nuevoUsuario = null;
                this.$scope.creandoUsuario = false;
                this.$scope.errorCrearUsuario = null;
                this.modalCrearUsuario.show();
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
            PermisosServiceImpl.prototype.cambiarEmail = function (usuarioId, email) {
                return this.http.put("/api/usuario/" + usuarioId + "/email", email);
            };
            PermisosServiceImpl.prototype.desactivar = function (usuarioId, eliminarDatos) {
                var desactivarAsociado = "";
                if (eliminarDatos) {
                    desactivarAsociado += "?desactivarAsociado=true";
                }
                return this.http.post("/api/usuario/desactivar/" + usuarioId + desactivarAsociado, {});
            };
            PermisosServiceImpl.prototype.recuperarPassword = function (usuarioId, email) {
                return this.http.post("/api/usuario/activar/" + usuarioId, email);
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
    .controller('PermisosController', ['$scope', 'PermisosService', 'Traducciones', 'Notificaciones',
    'ModalCambioDni', 'ModalEliminarUsuario', 'ModalRecuperarPassword', 'ModalCrearUsuario',
    'PermisosTypeahead', Cudu.Permisos.PermisosController])
    .factory('PermisosService', ['$http', 'Usuario', Cudu.Permisos.PermisosServiceFactory])
    .factory('ModalCambioDni', Cudu.Ux.ModalFactory("#dlgCambiarEmail", "#dlgCambiarEmailInput", true))
    .factory('ModalEliminarUsuario', Cudu.Ux.ModalFactory("#dlgEliminarUsuario"))
    .factory('ModalRecuperarPassword', Cudu.Ux.ModalFactory("#dlgRecuperarPassword"))
    .factory('ModalCrearUsuario', Cudu.Ux.ModalFactory("#dlgCrearUsuario", "#dlgCrearUsuarioInput", true))
    .factory('PermisosTypeahead', Cudu.Ux.TypeaheadFactory("#dlgCrearUsuarioInput", "asociado"));
