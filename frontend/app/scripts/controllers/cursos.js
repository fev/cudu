/// <reference path="../../../typings/tsd.d.ts"/>
var Cudu;
(function (Cudu) {
    var Cursos;
    (function (Cursos) {
        var Estado;
        (function (Estado) {
            Estado[Estado["Normal"] = 0] = "Normal";
            Estado[Estado["Inscrito"] = 1] = "Inscrito";
            Estado[Estado["ListaEspera"] = 2] = "ListaEspera";
        })(Estado || (Estado = {}));
        var CursoController = (function () {
            function CursoController($scope, service) {
                var _this = this;
                this.service = service;
                service.listado().success(function (pagina) {
                    var chunked = [[], [], []];
                    var lista = pagina.content;
                    for (var i = 0, j = 0; i < lista.length; i += 3, j++) {
                        if (typeof (lista[i]) === 'undefined') {
                            break;
                        }
                        chunked[0][j] = lista[i];
                        if (typeof (lista[i + 1]) === 'undefined') {
                            break;
                        }
                        chunked[1][j] = lista[i + 1];
                        if (typeof (lista[i + 2]) === 'undefined') {
                            break;
                        }
                        chunked[2][j] = lista[i + 2];
                    }
                    _this.cursos = chunked;
                });
            }
            CursoController.prototype.inscribir = function (id) {
                this.service.inscribir(id).success(function (c) {
                }).error(function (e) { });
            };
            CursoController.prototype.desinscribir = function (id) {
                this.service.desinscribir(id).success(function (c) {
                }).error(function (e) { });
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
                return this.http.post('/api/lluerna/curso/' + id + '/inscribir/' + this.usuarioId, {});
            };
            CursoService.prototype.desinscribir = function (id) {
                return this.http.post('/api/lluerna/curso/' + id + '/inscribir/' + this.usuarioId, {});
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
