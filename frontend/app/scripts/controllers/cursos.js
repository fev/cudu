/// <reference path="../../../typing.tsd.d.ts"/>
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
                this.service = service;
                this.cursos = service.listado_mock();
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
                return this.http.get("/api/lluerna/cursos");
            };
            CursoService.prototype.listado_mock = function () {
                return [{
                        id: 1, titulo: 'Contenidos Básicos',
                        estado: Estado.Normal,
                        plazas: 21, inscritos: 8,
                        descripcionFechas: 'Puente de octubre',
                        descripcionLugar: 'Moraira'
                    }];
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
