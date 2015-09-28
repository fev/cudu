/// <reference path="../../../typings/tsd.d.ts"/>
var Cudu;
(function (Cudu) {
    var Cursos;
    (function (Cursos) {
        var CursoController = (function () {
            function CursoController($scope, service) {
                var _this = this;
                this.$scope = $scope;
                this.service = service;
                service.listado().success(function (pagina) {
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
            CursoController.prototype.inscribir = function (id) {
                var _this = this;
                this.service.inscribir(id).success(function (e) {
                    _this.actualizarEstadoCurso(e, true);
                }).error(function (e) {
                    console.log(e);
                });
            };
            CursoController.prototype.desinscribir = function (id) {
                var _this = this;
                this.service.desinscribir(id).success(function (e) {
                    _this.actualizarEstadoCurso(e, false);
                }).error(function (e) {
                    console.log(e);
                });
            };
            CursoController.prototype.actualizarEstadoCurso = function (e, inscrito) {
                var actual = _.find(_.flatten(this.cursos), function (c) { return c.id == e.cursoId; });
                actual.disponibles = e.disponibles;
                e.inscritos = e.inscritos;
                actual.usuarioInscrito = inscrito;
                actual.usuarioListaEspera = e.listaDeEspera;
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
            return CursoController;
        })();
        Cursos.CursoController = CursoController;
        var CursoService = (function () {
            function CursoService(http, usuarioId) {
                this.http = http;
                this.usuarioId = usuarioId;
            }
            CursoService.prototype.listado = function () {
                return this.http.get("/api/lluerna/curso?sort=id&size=100");
            };
            CursoService.prototype.inscribir = function (id) {
                return this.http.post('/api/lluerna/curso/' + id + '/participantes', this.usuarioId);
            };
            CursoService.prototype.desinscribir = function (id) {
                return this.http.delete('/api/lluerna/curso/' + id + '/participantes/' + this.usuarioId, {});
            };
            return CursoService;
        })();
        function CursoServiceFactory($http, usuario) {
            return new CursoService($http, usuario.usuario.id);
        }
        Cursos.CursoServiceFactory = CursoServiceFactory;
    })(Cursos = Cudu.Cursos || (Cudu.Cursos = {}));
})(Cudu || (Cudu = {}));
angular.module('cuduApp')
    .controller('CursoController', ['$scope', 'CursoService', Cudu.Cursos.CursoController])
    .factory('CursoService', ['$http', 'Usuario', Cudu.Cursos.CursoServiceFactory]);
