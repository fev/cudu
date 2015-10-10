/// <reference path="../../../typings/tsd.d.ts"/>
/// <reference path="../services.d.ts"/>
var Cudu;
(function (Cudu) {
    var Cursos;
    (function (Cursos) {
        var CursoController = (function () {
            function CursoController($scope, service) {
                var _this = this;
                this.$scope = $scope;
                this.service = service;
                service.listado().then(function (pagina) {
                    var chunked = [[], [], []];
                    var lista = pagina.content;
                    for (var i = 0, j = 0; i < lista.length; i += 3, j++) {
                        if (typeof (lista[i]) === 'undefined') {
                            break;
                        }
                        chunked[0][j] = _this.establecerPlazos(lista[i]);
                        if (typeof (lista[i + 1]) === 'undefined') {
                            break;
                        }
                        chunked[1][j] = _this.establecerPlazos(lista[i + 1]);
                        if (typeof (lista[i + 2]) === 'undefined') {
                            break;
                        }
                        chunked[2][j] = _this.establecerPlazos(lista[i + 2]);
                    }
                    _this.cursos = chunked;
                });
            }
            CursoController.prototype.inscribir = function (curso) {
                var _this = this;
                if (curso.operacionEnCurso || !this.elCursoEstaEnPlazo(curso)) {
                    return;
                }
                curso.operacionEnCurso = true;
                this.service.inscribir(curso.id).success(function (e) {
                    _this.actualizarEstadoCurso(e, true);
                }).error(function (e) {
                    console.log(e);
                }).finally(function () {
                    curso.operacionEnCurso = false;
                });
            };
            CursoController.prototype.desinscribir = function (curso) {
                var _this = this;
                if (curso.operacionEnCurso || !this.elCursoEstaEnPlazo(curso)) {
                    return;
                }
                curso.operacionEnCurso = true;
                this.service.desinscribir(curso.id).success(function (e) {
                    _this.actualizarEstadoCurso(e, false);
                }).error(function (e) {
                    console.log(e);
                }).finally(function () {
                    curso.operacionEnCurso = false;
                });
            };
            CursoController.prototype.establecerPlazos = function (curso) {
                var m = moment(curso.fechaFinInscripcion);
                if (m.isValid) {
                    curso.plazoCerrado = m.isAfter();
                    if (m.isAfter()) {
                        curso.plazoRestante = "El plazo inscripción cierra en " + m.fromNow();
                    }
                    else {
                        curso.plazoRestante = "Plazo de inscripición cerrado";
                    }
                }
                return curso;
            };
            CursoController.prototype.actualizarEstadoCurso = function (e, inscrito) {
                var actual = _.find(_.flatten(this.cursos), function (c) { return c.id == e.cursoId; });
                actual.disponibles = e.disponibles;
                e.inscritos = e.inscritos;
                actual.usuarioInscrito = inscrito;
                actual.usuarioListaEspera = e.listaDeEspera;
            };
            CursoController.prototype.elCursoEstaEnPlazo = function (curso) {
                var ahora = moment();
                var fechaInicio = moment(curso.fechaInicioInscripcion);
                var fechaFin = moment(curso.fechaFinInscripcion);
                if (fechaInicio.isValid() && ahora.isAfter(fechaFin)) {
                    return false;
                }
                if (fechaFin.isValid() && ahora.isBefore(fechaInicio)) {
                    return false;
                }
                return true;
            };
            return CursoController;
        })();
        Cursos.CursoController = CursoController;
        var CursoServiceImpl = (function () {
            function CursoServiceImpl(http, usuarioService) {
                this.http = http;
                this.usuarioService = usuarioService;
            }
            CursoServiceImpl.prototype.listado = function () {
                var _this = this;
                return this.usuarioService.obtenerActual().then(function (u) {
                    _this.usuarioId = u.id;
                    return _this.http.get("/api/lluerna/curso?sort=id&size=100");
                });
            };
            CursoServiceImpl.prototype.inscribir = function (id) {
                return this.http.post('/api/lluerna/curso/' + id + '/participantes', this.usuarioId);
            };
            CursoServiceImpl.prototype.desinscribir = function (id) {
                return this.http.delete('/api/lluerna/curso/' + id + '/participantes/' + this.usuarioId, {});
            };
            return CursoServiceImpl;
        })();
        function CursoServiceFactory($http, usuarioService) {
            return new CursoServiceImpl($http, usuarioService);
        }
        Cursos.CursoServiceFactory = CursoServiceFactory;
    })(Cursos = Cudu.Cursos || (Cudu.Cursos = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('CursoController', ['$scope', 'CursoService', Cudu.Cursos.CursoController])
    .factory('CursoService', ['$http', 'Usuario', Cudu.Cursos.CursoServiceFactory]);
